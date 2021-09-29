package com.backen.multicommerce.service.imp;

import com.backen.multicommerce.entity.*;
import com.backen.multicommerce.enums.EnumStatusGeneral;
import com.backen.multicommerce.presenter.*;
import com.backen.multicommerce.repository.*;
import com.backen.multicommerce.service.ProductService;
import com.backen.multicommerce.service.UploadFileService;
import com.backen.multicommerce.utils.Paginator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryProductRepository categoryProductRepository;

    @Autowired
    TypeProductRepository typeProductRepository;

    @Autowired
    TaxProductRepository taxProductRepository;

    @Autowired
    UploadFileService uploadFileService;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Paginator findProductFilter(Integer page, Integer size, String mainFilter) {
        Pageable paging = PageRequest.of(page, size, Sort.by("name"));

        Page<Product> listQuery = productRepository.findByFilters(mainFilter, paging);

        List<ProductPresenter> listPresenter = listQuery.getContent().stream().map((e) ->
                getProductPresenterFromProduct(e)
        ).collect(Collectors.toList());

        Paginator paginator = new Paginator(listQuery.getTotalPages(), listQuery.getTotalElements(), Set.of(listPresenter));
        return paginator;
    }

    @Override
    public ProductPresenter findById(UUID id) {
        ProductPresenter productPresenter = null;
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            productPresenter = getProductPresenterFromProduct(product);
        }
        return productPresenter;
    }

    @Override
    public ProductPresenter save(ProductPresenter productPresenter, MultipartFile file, String fileType)
            throws IOException {
        Product product = getProductFromProductPresenter(productPresenter);
        Product productDB;
        String fileOld = null;
        if(productPresenter.getId() != null){
            productDB =  productRepository.findById(productPresenter.getId()).orElse(new Product());
            fileOld = productDB.getImage();
        }
        if (file != null && !file.isEmpty()) {
            String nameFile = uploadFileService.copy(file, fileType);
            uploadFileService.delete(fileOld, fileType);
            product.setImage(nameFile);
        }
        Product productSave = productRepository.save(product);
        ProductPresenter productPresenterResult = getProductPresenterFromProduct(productSave);
        return productPresenterResult;
    }

    @Override
    public void deleteById(String id) throws Exception {
        Product product = productRepository.findById(UUID.fromString(id)).get();
        if (product == null)
            throw new Exception("El producto no existe");
        product.setStatus(EnumStatusGeneral.INA);
        product.setName(product.getName().concat("-INA"));
        productRepository.save(product);
    }

    @Override
    public Product getProductFromProductPresenter(ProductPresenter productPresenter) {
        TypeProduct typeProduct = new TypeProduct();
        modelMapper.map(productPresenter.getTypeProduct(), typeProduct);

        CategoryProduct categoryProduct = new CategoryProduct();
        modelMapper.map(productPresenter.getCategoryProduct(), categoryProduct);

        TaxProduct taxProduct = new TaxProduct();
        modelMapper.map(productPresenter.getTaxProduct(), taxProduct);

        return Product.builder()
                .id(productPresenter.getId())
                .name(productPresenter.getName())
                .code(productPresenter.getCode())
                .description(productPresenter.getDescription())
                .purchasePrice(productPresenter.getPurchasePrice())
                .salePrice(productPresenter.getSalePrice())
                .image(productPresenter.getImage())
                .status(productPresenter.getStatus())
                .typeProduct(typeProduct)
                .categoryProduct(categoryProduct)
                .taxProduct(taxProduct)
                .build();
    }

    @Override
    public ProductPresenter getProductPresenterFromProduct(Product product) {
        TypeProductPresenter typeProductPresenter = new TypeProductPresenter();
        modelMapper.map(product.getTypeProduct(), typeProductPresenter);

        CategoryProductPresenter categoryProductPresenter = new CategoryProductPresenter();
        modelMapper.map(product.getCategoryProduct(), categoryProductPresenter);

        TaxProductPresenter taxProductPresenter = new TaxProductPresenter();
        modelMapper.map(product.getTaxProduct(), taxProductPresenter);
        return ProductPresenter.builder()
                .id(product.getId())
                .name(product.getName())
                .code(product.getCode())
                .description(product.getDescription())
                .purchasePrice(product.getPurchasePrice())
                .salePrice(product.getSalePrice())
                .image(product.getImage())
                .status(product.getStatus())
                .typeProduct(typeProductPresenter)
                .categoryProduct(categoryProductPresenter)
                .taxProduct(taxProductPresenter)
                .build();
    }

    @Override
    public Boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }


}
