package com.backen.multicommerce.service;

import com.backen.multicommerce.entity.Product;
import com.backen.multicommerce.presenter.ProductPresenter;
import com.backen.multicommerce.utils.Paginator;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductPresenter findById(UUID id);

    ProductPresenter save(ProductPresenter productPresenter);

    void deleteById(String id) throws Exception;

    Product getProductFromProductPresenter(ProductPresenter productPresenter);

    ProductPresenter getProductPresenterFromProduct(Product product);

    Boolean existsByName(String name);

    Paginator findProductFilter(Integer page, Integer size, String mainFilter);
}
