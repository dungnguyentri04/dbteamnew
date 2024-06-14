package com.example.databaseteam.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date orderDate;

    private Double totalPrice;

    private int quantity;

    private String paymentMethod;

    private boolean isAccept;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserDtls user;

    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "order")
    private List<OrderDetail> orderDetailList;

    @OneToOne(cascade = CascadeType.ALL)//cascade : dong bo thao tac giua hai doi tuong
    @JoinColumn(name = "shipping_address_id", referencedColumnName = "id")
    private ShippingAddress shippingAddress;

    public Order(){
        this.shippingAddress = new ShippingAddress();
//        this.orders = new ArrayList<>();
    }

}
