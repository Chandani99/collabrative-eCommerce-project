//package com.api.ecommerce.controller;
//
//public class CartControllerTest {
//}

package com.api.ecommerce.controller;

import com.api.ecommerce.model.Cart;
import com.api.ecommerce.model.Product;
import com.api.ecommerce.model.User;
import com.api.ecommerce.payload.CartDTO;
import com.api.ecommerce.payload.CartItemDTO;
import com.api.ecommerce.payload.ProductDTO;
import com.api.ecommerce.repository.CartRepository;
import com.api.ecommerce.service.ICartService;
import com.api.ecommerce.util.AuthUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private AuthUtil authUtil;

    @Mock
    private ICartService cartService;

    @InjectMocks
    private CartController cartController;

    private CartDTO cartDTO;
    private ProductDTO productDTO;
    private Cart cart;
    private CartItemDTO cartItemDTO;

    @BeforeEach
    void setUp() {
        productDTO = new ProductDTO();
        productDTO.setProductId(1L);
        productDTO.setProductName("Test Product");
        productDTO.setDescription("test product description");
        productDTO.setImage("image1");
        productDTO.setPrice(1000);
        productDTO.setDiscount(200);
        productDTO.setQuantity(10);
        productDTO.setSpecialPrice(700);



        cartDTO = new CartDTO();
        cartDTO.setCartId(1L);
        cartDTO.setProducts(Collections.singletonList(productDTO));
        cartDTO.setTotalPrice(2000.0);

        User user= new User();

        user.setEmail("test@example.com");
        cart = new Cart();
        cart.setCartId(1L);
        cart.setCartItems(null);
        cart.setUser(user);
        cart.setTotalPrice(2000.0);

    }

    @Test
    void addProductToCartCheck() {
        Long productId = 1L;
        Integer quantity = 2;

        when(cartService.addProductToCart(productId, quantity)).thenReturn(cartDTO);

        ResponseEntity<CartDTO> response = cartController.addProductToCart(productId, quantity);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cartDTO, response.getBody());
        verify(cartService, times(1)).addProductToCart(productId, quantity);
    }

    @Test
    void getCartsCheck() {
        List<CartDTO> cartDTOList = Arrays.asList(cartDTO);

        when(cartService.getAllCarts()).thenReturn(cartDTOList);

        ResponseEntity<List<CartDTO>> response = cartController.getCarts();

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals(cartDTOList, response.getBody());
        verify(cartService, times(1)).getAllCarts();
    }

    @Test
    void getCartByIdCheck() {
        String email = "test@example.com";
        when(authUtil.loggedInEmail()).thenReturn(email);
        when(cartRepository.findCartByEmail(email)).thenReturn(cart);
        when(cartService.getCart(email, cart.getCartId())).thenReturn(cartDTO);

        ResponseEntity<CartDTO> response = cartController.getCartById();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartDTO, response.getBody());
        verify(authUtil, times(1)).loggedInEmail();
        verify(cartRepository, times(1)).findCartByEmail(email);
        verify(cartService, times(1)).getCart(email, cart.getCartId());
    }

    @Test
    void updateCartProductCheck() {
        Long productId = 1L;
        String operation = "increment";

        when(cartService.updateProductQuantityInCart(productId, 1)).thenReturn(cartDTO);

        ResponseEntity<CartDTO> response = cartController.updateCartProduct(productId, operation);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartDTO, response.getBody());
        verify(cartService, times(1)).updateProductQuantityInCart(productId, 1);
    }

    @Test
    void testDeleteProductFromCart() {
        Long cartId = 1L;
        Long productId = 2L;
        String status = "Product removed successfully";

        when(cartService.deleteProductFromCart(cartId, productId)).thenReturn(status);

        ResponseEntity<String> response = cartController.deleteProductFromCart(cartId, productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(status, response.getBody());
        verify(cartService, times(1)).deleteProductFromCart(cartId, productId);
    }
}

