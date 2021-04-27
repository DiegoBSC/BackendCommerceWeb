package com.backen.multicommerce.service;

import com.backen.multicommerce.entity.TypeProduct;
import com.backen.multicommerce.presenter.TypeProductPresenter;

import java.util.List;
import java.util.UUID;

public interface TypeProductService {
    List<TypeProductPresenter> findAll();
    TypeProductPresenter findById(UUID id);
    TypeProductPresenter save(TypeProductPresenter typeProductPresenter);
    void deleteById(String id) throws Exception;
    TypeProduct getTypeProductFromTypeProductPresenter(TypeProductPresenter typeProductPresenter);
    TypeProductPresenter getTypeProductPresenterFromTypeProduct(TypeProduct typeProduct);
    Boolean existsByName(String nameTypeProduct);
}
