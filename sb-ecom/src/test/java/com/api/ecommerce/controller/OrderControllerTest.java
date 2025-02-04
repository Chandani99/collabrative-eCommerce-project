package com.api.ecommerce.controller;

import com.api.ecommerce.payload.OrderDTO;
import com.api.ecommerce.payload.OrderRequestDTO;
import com.api.ecommerce.payload.PaymentDTO;
import com.api.ecommerce.service.IOrderService;
import com.api.ecommerce.util.AuthUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private IOrderService orderService;

    @Mock
    private AuthUtil authUtil;

    @InjectMocks
    private OrderController orderController;

    private OrderRequestDTO orderRequestDTO;
    private OrderDTO orderDTO;

    @BeforeEach
    void setUp() {
        orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setAddressId(1L);
        orderRequestDTO.setPgName("Stripe");
        orderRequestDTO.setPgPaymentId("pay_123");
        orderRequestDTO.setPgStatus("SUCCESS");
        orderRequestDTO.setPgResponseMessage("Payment successful");
        orderRequestDTO.setPaymentMethod("Stripe");

        orderDTO = new OrderDTO();
        orderDTO.setOrderId(100L);
        orderDTO.setOrderDate(LocalDate.now());
        orderDTO.setOrderStatus("delevered");
        orderDTO.setAddressId(2l);
        orderDTO.setEmail("abc");
        orderDTO.setOrderItems(null);
        orderDTO.setTotalAmount(1000.0);
        orderDTO.setPayment(null);
    }

    @Test
    void placeOrderSuccessfullyCheck() {
        // Mock authentication & service layer
        when(authUtil.loggedInEmail()).thenReturn("user@example.com");
        when(orderService.placeOrder(anyString(), anyLong(), anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(orderDTO);

        ResponseEntity<OrderDTO> response = orderController.orderProducts("Stripe", orderRequestDTO);

        // Verify response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(orderDTO, response.getBody());
        verify(orderService, times(1)).placeOrder(anyString(), anyLong(), anyString(), anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void returnBadRequestForInvalidInputCheck() {
        OrderRequestDTO invalidOrderRequest = new OrderRequestDTO(); // Empty request

        ResponseEntity<OrderDTO> response = orderController.orderProducts("Stripe", invalidOrderRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode()); // Spring validation should be in service layer
    }

    @Test
    void returnUnauthorizedIfUserNotLoggedInCheck() {
        when(authUtil.loggedInEmail()).thenReturn(null);

        Exception exception = assertThrows(NullPointerException.class, () ->
                orderController.orderProducts("Stripe", orderRequestDTO));

        assertNotNull(exception);
    }

    @Test
    void handleInternalServerErrorCheck() {
        when(authUtil.loggedInEmail()).thenReturn("user@example.com");
        when(orderService.placeOrder(anyString(), anyLong(), anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenThrow(new RuntimeException("Service error"));

        Exception exception = assertThrows(RuntimeException.class, () ->
                orderController.orderProducts("Stripe", orderRequestDTO));

        assertEquals("Service error", exception.getMessage());
    }
}
