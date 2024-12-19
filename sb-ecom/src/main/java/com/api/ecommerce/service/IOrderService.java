package com.api.ecommerce.service;

import com.api.ecommerce.payload.OrderDTO;
import jakarta.transaction.Transactional;

public interface IOrderService {
    @Transactional
    OrderDTO placeOrder(String emailId, Long addressId, String paymentMethod, String pgName, String pgPaymentId, String pgStatus, String pgResponseMessage);
}
