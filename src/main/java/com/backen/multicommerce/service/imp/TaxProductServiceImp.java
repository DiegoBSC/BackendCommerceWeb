package com.backen.multicommerce.service.imp;

import com.backen.multicommerce.entity.TaxProduct;
import com.backen.multicommerce.enums.EnumStatusGeneral;
import com.backen.multicommerce.presenter.TaxProductPresenter;
import com.backen.multicommerce.repository.TaxProductRepository;
import com.backen.multicommerce.service.TaxProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaxProductServiceImp implements TaxProductService {

    @Autowired
    TaxProductRepository taxProductRepository;

    @Override
    public List<TaxProductPresenter> findAll() {
        List<TaxProduct> list = (List<TaxProduct>) taxProductRepository.findByStatus(EnumStatusGeneral.ACT);
        return list.stream().map((e) ->
                getTaxProductPresenterFromTaxProduct(e)
        ).collect(Collectors.toList());
    }

    @Override
    public TaxProductPresenter findById(UUID id) {
        TaxProductPresenter taxProductPresenter = null;
        TaxProduct taxProduct = taxProductRepository.findById(id).orElse(null);
        if (taxProduct != null) {
            taxProductPresenter = getTaxProductPresenterFromTaxProduct(taxProduct);
        }
        return taxProductPresenter;
    }

    @Override
    public TaxProductPresenter save(TaxProductPresenter taxProductPresenter) {
        TaxProduct taxProduct =
                getTaxProductFromTaxProductPresenter(taxProductPresenter);
        TaxProduct taxProductSave = taxProductRepository.save(taxProduct);
        TaxProductPresenter taxProductResult =
                getTaxProductPresenterFromTaxProduct(taxProductSave);
        return taxProductResult;
    }

    @Override
    public void deleteById(String id) throws Exception {
        TaxProduct taxProduct = taxProductRepository.findById(UUID.fromString(id)).get();
        if (taxProduct == null)
            throw new Exception("El impuesto no fue encontrado");
        taxProduct.setStatus(EnumStatusGeneral.INA);
        taxProduct.setName(taxProduct.getName().concat("-INA"));
        taxProductRepository.save(taxProduct);
    }

    @Override
    public TaxProduct getTaxProductFromTaxProductPresenter(
            TaxProductPresenter taxProductPresenter) {
        return TaxProduct.builder()
                .id(taxProductPresenter.getId())
                .name(taxProductPresenter.getName())
                .valueTax(taxProductPresenter.getValueTax())
                .status(taxProductPresenter.getStatus() != null ? taxProductPresenter.getStatus() : EnumStatusGeneral.ACT)
                .build();
    }

    @Override
    public TaxProductPresenter getTaxProductPresenterFromTaxProduct(
            TaxProduct taxProduct) {
        return TaxProductPresenter.builder()
                .id(taxProduct.getId())
                .name(taxProduct.getName())
                .valueTax(taxProduct.getValueTax())
                .status(taxProduct.getStatus())
                .build();
    }

    @Override
    public Boolean existsByName(String nameTaxProduct) {
        return taxProductRepository.existsByName(nameTaxProduct);
    }
}
