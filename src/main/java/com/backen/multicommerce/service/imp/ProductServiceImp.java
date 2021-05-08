package com.backen.multicommerce.service.imp;

import com.backen.multicommerce.entity.*;
import com.backen.multicommerce.enums.EnumStatusGeneral;
import com.backen.multicommerce.presenter.ProductPresenter;
import com.backen.multicommerce.repository.*;
import com.backen.multicommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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


    @Override
    public List<ProductPresenter> findAll() {
        List<Product> list = (List<Product>) productRepository.findAll();
        return list.stream().map( (e)->
                getProductPresenterFromProduct(e)
        ).collect(Collectors.toList());
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
        product.setCompany(company);
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
                .company(company)
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
                        .companyId(product.getCompany().getId().toString())
                        .build();
    }

    @Override
    public Boolean existsByNameAndCompany(String name, String idCompany) {
        return productRepository.existsByNameAndCompany(name, Company.builder()
                .id(UUID.fromString(idCompany))
                .build());
    }

    @Override
    public List<ProductPresenter> findAllByCompany(String idCompany) {
                List<Product> list = (List<Product>) productRepository.findByCompany(new Company(UUID.fromString(idCompany)));
        return list.stream().map( (e)->
                getProductPresenterFromProduct(e)
        ).collect(Collectors.toList());
    }
}
