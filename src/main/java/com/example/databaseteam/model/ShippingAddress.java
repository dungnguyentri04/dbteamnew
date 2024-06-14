package com.example.databaseteam.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.service.spi.InjectService;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shippingAddress")
public class ShippingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String address;

    private String country;

    private String district;

    private String province;

    private String zipcode;

    private String name;

    private String email;

    private String phone;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;
}
