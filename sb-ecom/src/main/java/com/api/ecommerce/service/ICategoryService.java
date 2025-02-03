package com.api.ecommerce.service;

import com.api.ecommerce.payload.CategoryDTO;
import com.api.ecommerce.payload.CategoryResponse;

public interface ICategoryService {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
