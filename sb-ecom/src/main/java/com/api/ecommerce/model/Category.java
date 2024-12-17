package com.api.ecommerce.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity(name = "categories")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @NotBlank
    @Size(min = 5, message = "Category name must contain atleast 5 characters")
    private String categoryName;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;

    public Category(String categoryName, List<Product> products) {
        this.categoryName = categoryName;
        this.products = products;
    }

    public Category(Long categoryId, String categoryName, List<Product> products) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.products = products;
    }

    public Category() {
    }


    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public @NotBlank @Size(min = 5, message = "Category name must contain atleast 5 characters") String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(@NotBlank @Size(min = 5, message = "Category name must contain atleast 5 characters") String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", products=" + products +
                '}';
    }


}
