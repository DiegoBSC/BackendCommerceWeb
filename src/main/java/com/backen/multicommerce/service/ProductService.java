package com.backen.multicommerce.service;

import com.backen.multicommerce.entity.Company;
import com.backen.multicommerce.entity.Product;
import com.backen.multicommerce.presenter.ProductPresenter;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductPresenter> findAll();
    ProductPresenter findById(UUID id);
    ProductPresenter save(ProductPresenter productPresenter);
    void deleteById(String id) throws Exception;
    Product getProductFromProductPresenter(ProductPresenter productPresenter);
    ProductPresenter getProductPresenterFromProduct(Product product);
    Boolean existsByNameAndCompany(String name, String idCompany);
    List<ProductPresenter> findAllByCompany(String idCompany);
}
