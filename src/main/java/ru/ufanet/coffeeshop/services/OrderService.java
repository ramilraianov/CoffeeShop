package ru.ufanet.coffeeshop.services;

import ru.ufanet.coffeeshop.models.Event;
import ru.ufanet.coffeeshop.models.Order;

import java.util.Optional;

public interface OrderService {

    void publishEvent(Event event);

    Optional<Order> findOrder(Long id);
}
