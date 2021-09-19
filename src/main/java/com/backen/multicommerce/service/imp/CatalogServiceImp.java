package com.backen.multicommerce.service.imp;

import com.backen.multicommerce.entity.Catalog;
import com.backen.multicommerce.entity.Company;
import com.backen.multicommerce.enums.EnumStatusGeneral;
import com.backen.multicommerce.presenter.CatalogPresenter;
import com.backen.multicommerce.repository.CatalogRepository;
import com.backen.multicommerce.repository.CompanyRepository;
import com.backen.multicommerce.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CatalogServiceImp implements CatalogService {

    @Autowired
    CatalogRepository catalogRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Override
    public List<CatalogPresenter> findAll(List<String> companiesIds) {
        List<UUID> idsCompanies =  companiesIds.stream()
                .map(UUID::fromString) // map in later stages
                .collect(Collectors.toList());
        List<Catalog> list = (List<Catalog>) catalogRepository.findCatalogByUser(idsCompanies);
        return list.stream().map((e) ->
                getCatalogPresenterFromCatalog(e)
        ).collect(Collectors.toList());
    }

    @Override
    public CatalogPresenter findById(UUID id) {
        CatalogPresenter catalogPresenter = null;
        Catalog catalog = catalogRepository.findById(id).orElse(null);
        if (catalog != null) {
            catalogPresenter = getCatalogPresenterFromCatalog(catalog);
        }
        return catalogPresenter;
    }

    @Override
    public CatalogPresenter save(CatalogPresenter catalogPresenter) {
        Company company = companyRepository.findById(UUID.fromString(catalogPresenter.getCompanyId())).get();
        Catalog catalog = getCatalogFromCatalogPresenter(catalogPresenter);
        catalog.setCompany(company);
        catalog.setStatus(EnumStatusGeneral.ACT);
        if (catalogPresenter.getId() == null) {
            catalog.setCreatedDate(new Date());
        }
        catalog.setUpdateDate(new Date());
        Catalog catalogSave = catalogRepository.save(catalog);
        CatalogPresenter catalogPresenterResult = getCatalogPresenterFromCatalog(catalogSave);
        return catalogPresenterResult;
    }

    @Override
    public void deleteById(String id) throws Exception {
        Catalog catalog = catalogRepository.findById(UUID.fromString(id)).get();
        if (catalog == null)
            throw new Exception("El catálogo no fue encontrado");
        catalog.setUpdateDate(new Date());
        catalog.setStatus(EnumStatusGeneral.INA);
        catalog.setName(catalog.getName().concat("-INA"));
        catalogRepository.save(catalog);
    }

    public Catalog getCatalogFromCatalogPresenter(CatalogPresenter catalogPresenter) {
        return Catalog.builder()
                .id(catalogPresenter.getId())
                .createdDate(catalogPresenter.getCreatedDate() == null ? new Date() : catalogPresenter.getCreatedDate())
                .name(catalogPresenter.getName())
                .status(catalogPresenter.getStatus() != null ? catalogPresenter.getStatus() : EnumStatusGeneral.ACT)
                .products(catalogPresenter.getProducts())
                .build();
    }

    public CatalogPresenter getCatalogPresenterFromCatalog(Catalog catalog) {
        return CatalogPresenter.builder()
                .id(catalog.getId())
                .createdDate(catalog.getCreatedDate())
                .name(catalog.getName())
                .status(catalog.getStatus())
                .companyId(catalog.getCompany().getId().toString())
                .products(catalog.getProducts())
                .build();
    }

    @Override
    public Boolean existsByNameCatalog(String nameCatalog) {
        return catalogRepository.existsByName(nameCatalog);
    }

    @Override
    public List<CatalogPresenter> findByCompanyAndStatus(String companyId) {
        List<Catalog> list = (List<Catalog>) catalogRepository.findByCompanyAndStatus(
                new Company(UUID.fromString(companyId)), EnumStatusGeneral.ACT);
        list.forEach(catalog -> {
            catalog.getProducts().forEach(product -> {
                product.setCompany(null);
            });
        });
        return list.stream().map((e) ->
                getCatalogPresenterFromCatalog(e)
        ).collect(Collectors.toList());
    }

}
