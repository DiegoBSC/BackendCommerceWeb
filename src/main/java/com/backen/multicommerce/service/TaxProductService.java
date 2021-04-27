package com.backen.multicommerce.service;

import com.backen.multicommerce.entity.TaxProduct;
import com.backen.multicommerce.entity.TypeProduct;
import com.backen.multicommerce.presenter.TaxProductPresenter;
import com.backen.multicommerce.presenter.TypeProductPresenter;

import java.util.List;
import java.util.UUID;

public interface TaxProductService {
    List<TaxProductPresenter> findAll();
    TaxProductPresenter findById(UUID id);
    TaxProductPresenter save(TaxProductPresenter taxProductPresenter);
    void deleteById(String id) throws Exception;
    TaxProduct getTaxProductFromTaxProductPresenter(TaxProductPresenter taxProductPresenter);
    TaxProductPresenter getTaxProductPresenterFromTaxProduct(TaxProduct taxProduct);
    Boolean existsByName(String nameTaxProduct);
}
