package com.youcode.cuisenio.category;


import com.youcode.cuisenio.features.recipe.dto.category.request.CategoryRequest;
import com.youcode.cuisenio.features.recipe.dto.category.response.CategoryResponse;
import com.youcode.cuisenio.features.recipe.entity.Category;
import com.youcode.cuisenio.features.recipe.exception.CategoryNotFoundException;
import com.youcode.cuisenio.features.recipe.mapper.CategoryMapper;
import com.youcode.cuisenio.features.recipe.repository.CategoryRepository;
import com.youcode.cuisenio.features.recipe.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;
    private CategoryRequest categoryRequest;
    private CategoryResponse categoryResponse;

    @BeforeEach
    void setUp() {
        category = new Category(1L, "Desserts", List.of());
        categoryRequest = new CategoryRequest("Desserts");
        categoryResponse = new CategoryResponse(1L, "Desserts");
    }

    @Test
    void shouldFindAllCategories() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Category> categoryPage = new PageImpl<>(List.of(category));

        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);
        when(categoryMapper.toResponse(category)).thenReturn(categoryResponse);

        Page<CategoryResponse> result = categoryService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Desserts", result.getContent().get(0).name());

        verify(categoryRepository, times(1)).findAll(pageable);
    }

    @Test
    void shouldFindCategoryById() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryMapper.toResponse(category)).thenReturn(categoryResponse);

        CategoryResponse result = categoryService.findById(1L);

        assertNotNull(result);
        assertEquals("Desserts", result.name());

        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(CategoryNotFoundException.class, () -> categoryService.findById(1L));

        assertEquals("Category Not Found avec id1", exception.getMessage());
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void shouldCreateCategory() {
        when(categoryMapper.toEntity(categoryRequest)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toResponse(category)).thenReturn(categoryResponse);

        CategoryResponse result = categoryService.create(categoryRequest);

        assertNotNull(result);
        assertEquals("Desserts", result.name());

        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void shouldUpdateCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toResponse(category)).thenReturn(categoryResponse);

        CategoryResponse result = categoryService.update(1L, categoryRequest);

        assertNotNull(result);
        assertEquals("Desserts", result.name());

        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(CategoryNotFoundException.class, () -> categoryService.update(1L, categoryRequest));

        assertEquals("Category Not Found avec id1", exception.getMessage());
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void shouldDeleteCategory() {
        when(categoryRepository.existsById(1L)).thenReturn(true);
        doNothing().when(categoryRepository).deleteById(1L);

        assertDoesNotThrow(() -> categoryService.delete(1L));

        verify(categoryRepository, times(1)).existsById(1L);
        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingCategory() {
        when(categoryRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(CategoryNotFoundException.class, () -> categoryService.delete(1L));

        assertEquals("Category Not Found avec id1", exception.getMessage());
        verify(categoryRepository, times(1)).existsById(1L);
    }
}
