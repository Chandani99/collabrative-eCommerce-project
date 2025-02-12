package com.api.ecommerce.payload;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long productId;
    private String productName;
    private String image;
    private String description;
    private Integer quantity;
    private double price;
    private double discount;
    private double specialPrice;


}
