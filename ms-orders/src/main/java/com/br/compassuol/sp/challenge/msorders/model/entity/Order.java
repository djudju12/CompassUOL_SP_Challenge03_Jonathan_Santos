package com.br.compassuol.sp.challenge.msorders.model.entity;

import com.br.compassuol.sp.challenge.msorders.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
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
    private List<Cart> products;

    @Enumerated(EnumType.STRING)
    // TODO - default value no sql
    private OrderStatus status;

    public Order setProducts(List<Cart> items) {
        this.products = items;
        this.products.forEach(item -> item.setOrder(this));
        return this;
    }
}