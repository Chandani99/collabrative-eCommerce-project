package com.api.ecommerce.service;

import com.api.ecommerce.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    public Product addProduct(Product product);
    public List<Product> getProductByUserId(Long  userId);
    public Product getProductById(Long productId);
    public Product deleteProductById(Long productId);

}
