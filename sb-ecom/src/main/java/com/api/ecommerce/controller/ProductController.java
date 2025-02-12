package com.api.ecommerce.controller;



import com.api.ecommerce.config.AppConstants;
import com.api.ecommerce.payload.ProductDTO;
import com.api.ecommerce.payload.ProductResponse;
import com.api.ecommerce.service.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {



    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }




    @Operation(
            summary = "Add a new product",
            description = "Adds a new product under a specific category."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Product successfully added",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))
    )
    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDTO,
                                                 @PathVariable Long categoryId){
        ProductDTO savedProductDTO = productService.addProduct(categoryId, productDTO);
        return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all products",
            description = "Retrieves a paginated list of all available products."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Products retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))
    )
    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder
    ){
        ProductResponse productResponse = productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @Operation(
            summary = "Get products by category",
            description = "Retrieves products belonging to a specific category."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Products retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))
    )
    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId,
                                                                 @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                 @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                                 @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
                                                                 @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder){
        ProductResponse productResponse = productService.searchByCategory(categoryId, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Search products by keyword",
            description = "Searches for products using a keyword."
    )
    @ApiResponse(responseCode = "302", description = "Products found")
    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword,
                                                                @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                                @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
                                                                @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder){
        ProductResponse productResponse = productService.searchProductByKeyword(keyword, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productResponse, HttpStatus.FOUND);
    }

    @Operation(
            summary = "Update product details",
            description = "Updates an existing product's details."
    )
    @ApiResponse(responseCode = "200", description = "Product updated successfully")
    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO,
                                                    @PathVariable Long productId){
        ProductDTO updatedProductDTO = productService.updateProduct(productId, productDTO);
        return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a product",
            description = "Deletes a product by its ID."
    )
    @ApiResponse(responseCode = "200", description = "Product deleted successfully")
    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
        ProductDTO deletedProduct = productService.deleteProduct(productId);
        return new ResponseEntity<>(deletedProduct, HttpStatus.OK);
    }

    @Operation(
            summary = "Update product image",
            description = "Updates an existing product's image."
    )
    @ApiResponse(responseCode = "200", description = "Product image updated successfully")
    @PutMapping("/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId,
                                                         @RequestParam("image") MultipartFile image) throws IOException {
        ProductDTO updatedProduct = productService.updateProductImage(productId, image);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

}
