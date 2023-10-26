package ru.ufanet.coffeeshop.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "order_id")
    Long orderId;

    @Column(name = "customer_id")
    Long customerId;

    @Column(name = "employee_id")
    Long employeeId;

    @Column(name = "order_date")
    LocalDateTime orderDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Event> orderEvents;

    public Order() {
        orderEvents = new ArrayList<>();
    }
}



