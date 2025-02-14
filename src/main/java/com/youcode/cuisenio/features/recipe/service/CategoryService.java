package com.youcode.cuisenio.features.recipe.service;

import com.youcode.cuisenio.features.recipe.dto.category.request.CategoryRequest;
import com.youcode.cuisenio.features.recipe.dto.category.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    public Page<CategoryResponse> findAll(Pageable pageable);

    public CategoryResponse findById(Long id);

    public CategoryResponse create(CategoryRequest request) ;

    public CategoryResponse update(Long id, CategoryRequest request) ;

    public void delete(Long id) ;
}
