package com.br.compassuol.sp.challenge.msorders.model.entity;

import com.br.compassuol.sp.challenge.msorders.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderedProduct> products;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_address_id")
    private DeliveryAddress deliveryAddress;

    public Order setProducts(List<OrderedProduct> products) {
        this.products = products;
        this.products.forEach(product -> product.setOrder(this));
        return this;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", products=" + products.stream().map(OrderedProduct::getProductId).toList() +
                ", status=" + status +
                ", deliveryAddress=" + deliveryAddress +
                '}';
    }
}
