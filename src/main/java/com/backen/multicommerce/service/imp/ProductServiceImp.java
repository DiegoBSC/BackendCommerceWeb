package com.backen.multicommerce.service.imp;

import com.backen.multicommerce.entity.*;
import com.backen.multicommerce.enums.EnumStatusGeneral;
import com.backen.multicommerce.presenter.CompanyPresenter;
import com.backen.multicommerce.presenter.ProductPresenter;
import com.backen.multicommerce.repository.*;
import com.backen.multicommerce.service.ProductService;
import com.backen.multicommerce.utils.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    CategoryProductRepository categoryProductRepository;

    @Autowired
    TypeProductRepository typeProductRepository;

    @Autowired
    TaxProductRepository taxProductRepository;


//    @Override
//    public List<ProductPresenter> findAll() {
//        List<Product> list = (List<Product>) productRepository.findAll();
//        return list.stream().map( (e)->
//                getProductPresenterFromProduct(e)
//        ).collect(Collectors.toList());
//    }


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
        if(product != null){
            productPresenter = getProductPresenterFromProduct(product);
        }
        return productPresenter;
    }

    @Override
    public ProductPresenter save(ProductPresenter productPresenter) {
        Company company  = companyRepository.findById(UUID.fromString(productPresenter.getCompanyId())).get();
        TypeProduct typeProduct = typeProductRepository.findById(UUID.fromString(productPresenter.getTypeProductId())).get();
        CategoryProduct categoryProduct = categoryProductRepository.findById(UUID.fromString(productPresenter.getCategoryProductId())).get();
        TaxProduct taxProduct = taxProductRepository.findById(UUID.fromString(productPresenter.getTaxProductId())).get();
        Product product = getProductFromProductPresenter(productPresenter);
        product.setStatus(EnumStatusGeneral.ACT);
        product.setCategoryProduct(categoryProduct);
        product.setTaxProduct(taxProduct);
        product.setTypeProduct(typeProduct);
        Product productSave = productRepository.save(product);
        ProductPresenter productPresenterResult = getProductPresenterFromProduct(productSave);
        return productPresenterResult;
    }

    @Override
    public void deleteById(String id) throws Exception {
        Product product =  productRepository.findById(UUID.fromString(id)).get();
        if(product == null)
            throw new Exception("El producto no existe");
        product.setStatus(EnumStatusGeneral.INA);
        product.setName(product.getName().concat("-INA"));
        productRepository.save(product);
    }

    @Override
    public Product getProductFromProductPresenter(ProductPresenter productPresenter) {
        TypeProduct typeProduct = typeProductRepository.findById(UUID.fromString(productPresenter.getTypeProductId())).get();
        CategoryProduct categoryProduct = categoryProductRepository.findById(UUID.fromString(productPresenter.getCategoryProductId())).get();
        TaxProduct taxProduct = taxProductRepository.findById(UUID.fromString(productPresenter.getTaxProductId())).get();
        Company company = companyRepository.findById(UUID.fromString(productPresenter.getCompanyId())).get();
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
        return ProductPresenter.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .code(product.getCode())
                        .description(product.getDescription())
                        .purchasePrice(product.getPurchasePrice())
                        .salePrice(product.getSalePrice())
                        .image(product.getImage())
                        .status(product.getStatus())
                        .typeProductId(product.getTypeProduct().getId().toString())
                        .categoryProductId(product.getCategoryProduct().getId().toString())
                        .taxProductId(product.getTaxProduct().getId().toString())
                        .build();
    }

    @Override
    public Boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }


}
