package com.api.ecommerce.controller;
import com.api.ecommerce.payload.ProductDTO;
import com.api.ecommerce.payload.ProductResponse;
import com.api.ecommerce.service.IProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private IProductService productService;

    @InjectMocks
    private ProductController productController;

    private ProductDTO productDTO;
    private ProductResponse productResponse;

    @BeforeEach
    void setUp() {
        productDTO = new ProductDTO();
        productDTO.setProductId(1L);
        productDTO.setProductName("Test Product");

        productResponse = new ProductResponse();
        productResponse.setContent(Collections.singletonList(productDTO));
        productResponse.setPageNumber(1);
        productResponse.setPageSize(10);
    }

    @Test
    void addProductSuccessfullyTest() {
        when(productService.addProduct(anyLong(), any(ProductDTO.class))).thenReturn(productDTO);

        ResponseEntity<ProductDTO> response = productController.addProduct(productDTO, 1L);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(productDTO, response.getBody());
        verify(productService, times(1)).addProduct(anyLong(), any(ProductDTO.class));
    }

    @Test
    void getAllProductsTest() {
        when(productService.getAllProducts(anyInt(), anyInt(), anyString(), anyString())).thenReturn(productResponse);

        ResponseEntity<ProductResponse> response = productController.getAllProducts(1, 10, "name", "asc");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productResponse, response.getBody());
        verify(productService, times(1)).getAllProducts(anyInt(), anyInt(), anyString(), anyString());
    }

    @Test
    void getProductsByCategoryTest() {
        when(productService.searchByCategory(anyLong(), anyInt(), anyInt(), anyString(), anyString())).thenReturn(productResponse);

        ResponseEntity<ProductResponse> response = productController.getProductsByCategory(1L, 1, 10, "name", "asc");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productResponse, response.getBody());
        verify(productService, times(1)).searchByCategory(anyLong(), anyInt(), anyInt(), anyString(), anyString());
    }

    @Test
    void getProductsByKeywordTest() {
        when(productService.searchProductByKeyword(anyString(), anyInt(), anyInt(), anyString(), anyString())).thenReturn(productResponse);

        ResponseEntity<ProductResponse> response = productController.getProductsByKeyword("test", 1, 10, "name", "asc");

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals(productResponse, response.getBody());
        verify(productService, times(1)).searchProductByKeyword(anyString(), anyInt(), anyInt(), anyString(), anyString());
    }

    @Test
    void updateProductTest() {
        when(productService.updateProduct(anyLong(), any(ProductDTO.class))).thenReturn(productDTO);

        ResponseEntity<ProductDTO> response = productController.updateProduct(productDTO, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productDTO, response.getBody());
        verify(productService, times(1)).updateProduct(anyLong(), any(ProductDTO.class));
    }

    @Test
    void deleteProductTest() {
        when(productService.deleteProduct(anyLong())).thenReturn(productDTO);

        ResponseEntity<ProductDTO> response = productController.deleteProduct(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productDTO, response.getBody());
        verify(productService, times(1)).deleteProduct(anyLong());
    }

    @Test
    void updateProductImageCheck() throws IOException {
        MultipartFile mockFile = mock(MultipartFile.class);
        when(productService.updateProductImage(anyLong(), any(MultipartFile.class))).thenReturn(productDTO);

        ResponseEntity<ProductDTO> response = productController.updateProductImage(1L, mockFile);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productDTO, response.getBody());
        verify(productService, times(1)).updateProductImage(anyLong(), any(MultipartFile.class));
    }
}
