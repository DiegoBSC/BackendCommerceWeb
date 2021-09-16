package com.backen.multicommerce.service.imp;

import com.backen.multicommerce.entity.TypeProduct;
import com.backen.multicommerce.enums.EnumStatusGeneral;
import com.backen.multicommerce.presenter.TypeProductPresenter;
import com.backen.multicommerce.repository.TypeProductRepository;
import com.backen.multicommerce.service.TypeProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TypeProductServiceImp implements TypeProductService {

    @Autowired
    TypeProductRepository typeProductRepository;

    @Override
    public List<TypeProductPresenter> findAll() {
        List<TypeProduct> list = (List<TypeProduct>) typeProductRepository.findByStatus(EnumStatusGeneral.ACT);
        return list.stream().map((e) ->
                getTypeProductPresenterFromTypeProduct(e)
        ).collect(Collectors.toList());
    }

    @Override
    public TypeProductPresenter findById(UUID id) {
        TypeProductPresenter typeProductPresenter = null;
        TypeProduct typeProduct = typeProductRepository.findById(id).orElse(null);
        if (typeProduct != null) {
            typeProductPresenter = getTypeProductPresenterFromTypeProduct(typeProduct);
        }
        return typeProductPresenter;
    }

    @Override
    public TypeProductPresenter save(TypeProductPresenter typeProductPresenter) {
        TypeProduct typeProduct =
                getTypeProductFromTypeProductPresenter(typeProductPresenter);
        TypeProduct typeProductSave = typeProductRepository.save(typeProduct);
        TypeProductPresenter typeProductResult =
                getTypeProductPresenterFromTypeProduct(typeProductSave);
        return typeProductResult;
    }

    @Override
    public void deleteById(String id) throws Exception {
        TypeProduct typeProduct = typeProductRepository.findById(UUID.fromString(id)).get();
        if (typeProduct == null)
            throw new Exception("El tipo de producto no fue encontrado");
        typeProduct.setStatus(EnumStatusGeneral.INA);
        typeProduct.setName(typeProduct.getName().concat("-INA"));
        typeProductRepository.save(typeProduct);
    }

    @Override
    public TypeProduct getTypeProductFromTypeProductPresenter(
            TypeProductPresenter typeProductPresenter) {
        return TypeProduct.builder()
                .id(typeProductPresenter.getId())
                .name(typeProductPresenter.getName())
                .status(typeProductPresenter.getStatus() != null ? typeProductPresenter.getStatus() : EnumStatusGeneral.ACT)
                .build();
    }

    @Override
    public TypeProductPresenter getTypeProductPresenterFromTypeProduct(
            TypeProduct typeProduct) {
        return TypeProductPresenter.builder()
                .id(typeProduct.getId())
                .name(typeProduct.getName())
                .status(typeProduct.getStatus())
                .build();
    }

    @Override
    public Boolean existsByName(String nameTypeProduct) {
        return typeProductRepository.existsByName(nameTypeProduct);
    }
}
