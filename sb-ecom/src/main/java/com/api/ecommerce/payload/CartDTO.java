package com.api.ecommerce.payload;


import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {

    private Long cartId;
    private Double totalPrice = 0.0;
    private List<ProductDTO> products = new ArrayList<>();



}
