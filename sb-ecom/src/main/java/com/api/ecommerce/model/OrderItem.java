package com.api.ecommerce.model;


import jakarta.persistence.*;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_items")
public class OrderItem {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Integer quantity;
    private double discount;
    private double orderedProductPrice;


    public OrderItem() {
    }


    public OrderItem(Long orderItemId, Product product, Order order, Integer quantity, double discount, double orderedProductPrice) {
        this.orderItemId = orderItemId;
        this.product = product;
        this.order = order;
        this.quantity = quantity;
        this.discount = discount;
        this.orderedProductPrice = orderedProductPrice;
    }

    public OrderItem(Product product, Order order, Integer quantity, double discount, double orderedProductPrice) {
        this.product = product;
        this.order = order;
        this.quantity = quantity;
        this.discount = discount;
        this.orderedProductPrice = orderedProductPrice;
    }


    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getOrderedProductPrice() {
        return orderedProductPrice;
    }

    public void setOrderedProductPrice(double orderedProductPrice) {
        this.orderedProductPrice = orderedProductPrice;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "orderItemId=" + orderItemId +
                ", product=" + product +
                ", order=" + order +
                ", quantity=" + quantity +
                ", discount=" + discount +
                ", orderedProductPrice=" + orderedProductPrice +
                '}';
    }
}
