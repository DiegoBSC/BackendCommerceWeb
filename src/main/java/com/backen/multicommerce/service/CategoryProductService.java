package com.backen.multicommerce.service;

import com.backen.multicommerce.entity.CategoryProduct;
import com.backen.multicommerce.presenter.CategoryProductPresenter;

import java.util.List;
import java.util.UUID;

public interface CategoryProductService {
    List<CategoryProductPresenter> findAll();
    CategoryProductPresenter findById(UUID id);
    CategoryProductPresenter save(CategoryProductPresenter categoryProductPresenter);
    void deleteById(String id) throws Exception;
    CategoryProduct getCategoryProductFromCategoryProductPresenter(CategoryProductPresenter categoryProductPresenter);
    CategoryProductPresenter getCategoryProductPresenterFromCategoryProduct(CategoryProduct categoryProduct);
    Boolean existsByName(String nameCategoryProduct);
}
