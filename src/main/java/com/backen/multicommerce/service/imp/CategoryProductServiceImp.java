package com.backen.multicommerce.service.imp;

import com.backen.multicommerce.entity.CategoryProduct;
import com.backen.multicommerce.enums.EnumStatusGeneral;
import com.backen.multicommerce.presenter.CategoryProductPresenter;
import com.backen.multicommerce.repository.CategoryProductRepository;
import com.backen.multicommerce.service.CategoryProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryProductServiceImp implements CategoryProductService {
    
    @Autowired
    CategoryProductRepository categoryProductRepository;
    
    @Override
    public List<CategoryProductPresenter> findAll() {
        List<CategoryProduct> list = (List<CategoryProduct>) categoryProductRepository.findByStatus(EnumStatusGeneral.ACT);
        return list.stream().map( (e)->
                getCategoryProductPresenterFromCategoryProduct(e)
        ).collect(Collectors.toList());
    }

    @Override
    public CategoryProductPresenter findById(UUID id) {
        CategoryProductPresenter categoryProductPresenter = null;
        CategoryProduct categoryProduct = categoryProductRepository.findById(id).orElse(null);
        if(categoryProduct != null){
            categoryProductPresenter = getCategoryProductPresenterFromCategoryProduct(categoryProduct);
        }
        return categoryProductPresenter;
    }

    @Override
    public CategoryProductPresenter save(CategoryProductPresenter categoryProductPresenter) {
        CategoryProduct categoryProduct =
                getCategoryProductFromCategoryProductPresenter(categoryProductPresenter);
        CategoryProduct categoryProductSave = categoryProductRepository.save(categoryProduct);
        CategoryProductPresenter categoryProductResult =
                getCategoryProductPresenterFromCategoryProduct(categoryProductSave);
        return categoryProductResult;
    }

    @Override
    public void deleteById(String id) throws Exception {
        CategoryProduct categoryProduct  =  categoryProductRepository.findById(UUID.fromString(id)).get();
        if(categoryProduct == null)
            throw new Exception("La categoria no fue encontrada");
        categoryProduct.setStatus(EnumStatusGeneral.INA);
        categoryProduct.setName(categoryProduct.getName().concat("-INA"));
        categoryProductRepository.save(categoryProduct);
    }

    @Override
    public CategoryProduct getCategoryProductFromCategoryProductPresenter(
            CategoryProductPresenter categoryProductPresenter) {
        return CategoryProduct.builder()
                .id(categoryProductPresenter.getId())
                .name(categoryProductPresenter.getName())
                .status(categoryProductPresenter.getStatus() != null ? categoryProductPresenter.getStatus() : EnumStatusGeneral.ACT)
                .build();
    }

    @Override
    public CategoryProductPresenter getCategoryProductPresenterFromCategoryProduct(
            CategoryProduct categoryProduct) {
        return CategoryProductPresenter.builder()
                .id(categoryProduct.getId())
                .name(categoryProduct.getName())
                .status(categoryProduct.getStatus())
                .build();
    }

    @Override
    public Boolean existsByName(String nameCategoryProduct) {
        return categoryProductRepository.existsByName(nameCategoryProduct);
    }
}
