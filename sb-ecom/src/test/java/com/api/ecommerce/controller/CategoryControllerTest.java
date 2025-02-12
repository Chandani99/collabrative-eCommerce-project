package com.api.ecommerce.controller;
import com.api.ecommerce.payload.CategoryDTO;
import com.api.ecommerce.payload.CategoryResponse;
import com.api.ecommerce.service.ICategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private ICategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private CategoryDTO categoryDTO;
    private CategoryResponse categoryResponse;

    @BeforeEach
    void setUp() {
        categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(1L);
        categoryDTO.setCategoryName("Electronics");

        categoryResponse = new CategoryResponse();
        categoryResponse.setContent(Collections.singletonList(categoryDTO));
        categoryResponse.setPageNumber(1);
        categoryResponse.setPageSize(10);
        categoryResponse.setTotalElements(1L);
        categoryResponse.setTotalPages(1);
        categoryResponse.setLastPage(true);
    }

    @Test
    void getAllCategoriesCheck() {
        when(categoryService.getAllCategories(0, 10, "id", "asc")).thenReturn(categoryResponse);

        ResponseEntity<CategoryResponse> response = categoryController.getAllCategories(0, 10, "id", "asc");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categoryResponse, response.getBody());
        verify(categoryService, times(1)).getAllCategories(0, 10, "id", "asc");
    }

    @Test
    void createCategoryCheck() {
        when(categoryService.createCategory(categoryDTO)).thenReturn(categoryDTO);

        ResponseEntity<CategoryDTO> response = categoryController.createCategory(categoryDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(categoryDTO, response.getBody());
        verify(categoryService, times(1)).createCategory(categoryDTO);
    }

    @Test
    void deleteCategoryCheck() {
        when(categoryService.deleteCategory(1L)).thenReturn(categoryDTO);

        ResponseEntity<CategoryDTO> response = categoryController.deleteCategory(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categoryDTO, response.getBody());
        verify(categoryService, times(1)).deleteCategory(1L);
    }

    @Test
    void updateCategoryCheck() {
        when(categoryService.updateCategory(categoryDTO, 1L)).thenReturn(categoryDTO);

        ResponseEntity<CategoryDTO> response = categoryController.updateCategory(categoryDTO, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categoryDTO, response.getBody());
        verify(categoryService, times(1)).updateCategory(categoryDTO, 1L);
    }
}
