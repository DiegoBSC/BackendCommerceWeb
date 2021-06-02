package com.backen.multicommerce.service;

import com.backen.multicommerce.entity.Catalog;
import com.backen.multicommerce.presenter.CatalogPresenter;

import java.util.List;
import java.util.UUID;

public interface CatalogService {
    List<CatalogPresenter> findAll();
    CatalogPresenter findById(UUID id);
    CatalogPresenter save(CatalogPresenter catalogPresenter);
    void deleteById(String id) throws Exception;
    Catalog getCatalogFromCatalogPresenter(CatalogPresenter catalogPresenter);
    CatalogPresenter getCatalogPresenterFromCatalog(Catalog catalog);
    Boolean existsByNameCatalog(String nameCatalog);
    List<CatalogPresenter> findAllByCompanyId(String companyId);
    List<CatalogPresenter> getCatalogsByCompanyId(UUID userId);
}
