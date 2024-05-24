package com.example.databaseteam.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Entity
@Table(name = "user_dtls")
public class UserDtls {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String mobileNumber;

    private String email;

    private String address;

    private String city;

    private String state;

    private String pincode;

    private String password;

    private String profileImage;

    private String roles;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "user")//cascade : dong bo thao tac giua hai doi tuong
    private ShoppingCart shoppingCart;
//
////    @OneToMany
////    private List<Order> orders;
//
    public UserDtls(){
        this.shoppingCart = new ShoppingCart();
//        this.orders = new ArrayList<>();
    }

}
