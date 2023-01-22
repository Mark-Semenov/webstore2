package ru.gb.mark.webstore.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private BigDecimal totalSum;

    @ManyToOne
    private User user;
}
