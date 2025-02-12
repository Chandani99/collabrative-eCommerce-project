package com.api.ecommerce.controller;


import com.api.ecommerce.config.AppConstants;
import com.api.ecommerce.payload.CategoryDTO;
import com.api.ecommerce.payload.CategoryResponse;
import com.api.ecommerce.service.ICategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {



    @Autowired
    private final ICategoryService categoryService;
    public CategoryController(ICategoryService categoryService){

        this.categoryService = categoryService;
    }

    @Operation(
            summary = "Get all categories",
            description = "Fetch paginated categories with sorting options"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved categories"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Create a new category",
            description = "Adds a new category to the system and returns the created category details."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Category successfully created",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDTO.class))
    )
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @ApiResponse(responseCode = "500", description = "Internal server error")

    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete a category",
            description = "Deletes a category by its ID and returns the deleted category details."
    )
    @ApiResponse(responseCode = "200", description = "Category successfully deleted")
    @ApiResponse(responseCode = "404", description = "Category not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId){
        CategoryDTO deletedCategory = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(deletedCategory, HttpStatus.OK);
    }


    @ApiResponse(
            responseCode = "200",
            description = "Category successfully updated",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDTO.class))
    )
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @ApiResponse(responseCode = "404", description = "Category not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                                      @PathVariable Long categoryId){
        CategoryDTO savedCategoryDTO = categoryService.updateCategory(categoryDTO, categoryId);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);
    }
}

