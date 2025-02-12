package com.api.ecommerce.controller;

import com.api.ecommerce.model.Cart;
import com.api.ecommerce.payload.CartDTO;
import com.api.ecommerce.repository.CartRepository;
import com.api.ecommerce.service.ICartService;
import com.api.ecommerce.util.AuthUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController {


    private final CartRepository cartRepository;



    private final AuthUtil authUtil;

    private final  ICartService cartService;

    public CartController(CartRepository cartRepository, AuthUtil authUtil, ICartService cartService) {
        this.cartRepository = cartRepository;
        this.authUtil = authUtil;
        this.cartService = cartService;
    }



    @Operation(summary = "Add a product to the cart", description = "Adds a specified quantity of a product to the user's shopping cart.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product added to cart successfully",
                    content = @Content(schema = @Schema(implementation = CartDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid product ID or quantity"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long productId,
                                                    @PathVariable Integer quantity){
        CartDTO cartDTO = cartService.addProductToCart(productId, quantity);
        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all carts", description = "Retrieves a list of all shopping carts.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Carts found",
                    content = @Content(schema = @Schema(implementation = CartDTO.class))),
            @ApiResponse(responseCode = "404", description = "No carts found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/carts")
    public ResponseEntity<List<CartDTO>> getCarts() {
        List<CartDTO> cartDTOs = cartService.getAllCarts();
        return new ResponseEntity<List<CartDTO>>(cartDTOs, HttpStatus.FOUND);
    }

    @Operation(summary = "Get user's cart", description = "Retrieves the authenticated user's cart details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved cart details",
                    content = @Content(schema = @Schema(implementation = CartDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Cart not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/carts/users/cart")
    public ResponseEntity<CartDTO> getCartById(){
        String emailId = authUtil.loggedInEmail();
        Cart cart = cartRepository.findCartByEmail(emailId);
        Long cartId = cart.getCartId();
        CartDTO cartDTO = cartService.getCart(emailId, cartId);
        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.OK);
    }

    @Operation(summary = "Update product quantity in cart",
            description = "Increases or decreases the quantity of a product in the user's shopping cart. Use 'delete' to remove the product.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product quantity updated successfully",
                    content = @Content(schema = @Schema(implementation = CartDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid product ID or operation"),
            @ApiResponse(responseCode = "404", description = "Product not found in cart"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/cart/products/{productId}/quantity/{operation}")
    public ResponseEntity<CartDTO> updateCartProduct(@PathVariable Long productId,
                                                     @PathVariable String operation) {

        CartDTO cartDTO = cartService.updateProductQuantityInCart(productId,
                operation.equalsIgnoreCase("delete") ? -1 : 1);

        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.OK);
    }

    @Operation(summary = "Delete product from cart",
            description = "Removes a specific product from a given shopping cart.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product successfully removed from cart",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid cart ID or product ID"),
            @ApiResponse(responseCode = "404", description = "Cart or product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/carts/{cartId}/product/{productId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable Long cartId,
                                                        @PathVariable Long productId) {
        String status = cartService.deleteProductFromCart(cartId, productId);

        return new ResponseEntity<String>(status, HttpStatus.OK);
    }



}
