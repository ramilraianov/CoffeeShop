package ru.ufanet.coffeeshop.services;

import jakarta.transaction.Transactional;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.ufanet.coffeeshop.exceptions.EventNotAllowed;
import ru.ufanet.coffeeshop.exceptions.OrderExist;
import ru.ufanet.coffeeshop.models.Event;
import ru.ufanet.coffeeshop.models.EventType;
import ru.ufanet.coffeeshop.models.Order;
import ru.ufanet.coffeeshop.repositories.EventRepository;
import ru.ufanet.coffeeshop.repositories.OrderRepository;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final EventRepository eventRepository;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(EventRepository eventRepository, OrderRepository orderRepository) {
        this.eventRepository = eventRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public void publishEvent(Event event) {
        checkEventAllowed(event);
        switch (event.getEventType()) {
            case RESERVED -> processReservedEvent(event);
            case PROCESSING, READY, ISSUED, CANCELED -> processEvent(event);
        }
        eventRepository.save(event);
    }

    public void checkEventAllowed(Event event) throws EventNotAllowed {
        if (event.getEventType() != EventType.RESERVED) {
            val orderId = event.getEventData().get("orderId").asLong();
            val order = orderRepository.findById(orderId).orElseThrow(() ->
                    new NoSuchElementException("Order with id " + orderId + " is missing"));

            val events = order.getOrderEvents();
            if (!events.isEmpty()) {
                val lastEvent = events.get(events.size() - 1);
                val lastEventType = lastEvent.getEventType();

                if (lastEventType == EventType.CANCELED || lastEventType == EventType.ISSUED) {
                    throw new EventNotAllowed();
                }
            }

            if (events.stream().anyMatch(orderEvent -> orderEvent.getEventType() == event.getEventType())) {
                throw new EventNotAllowed();
            }
        }
    }

    private void processEvent(Event event) {
        val orderId = event.getEventData().get("orderId").asLong();
        val order = findOrder(orderId);
        val foundOrder = order.orElseThrow(() ->
                new NoSuchElementException("Order with id " + orderId + " is missing"));
        event.setOrder(foundOrder);
        foundOrder.getOrderEvents().add(event);
    }

    @Transactional
    private void processReservedEvent(Event event) throws OrderExist {
        val orderIdNode = event.getEventData().get("orderId");

        if (orderIdNode == null || orderIdNode.isNull()) {
            throw new NullPointerException("orderId is missing");
        }

        long orderId = orderIdNode.asLong();

        if (orderRepository.findById(orderId).isPresent()) {
            throw new OrderExist("Order with id " + orderId + " already exists");
        }

        Order order = saveOrder(event);
        event.setOrder(order);
        order.getOrderEvents().add(event);
    }


    @Override
    @Transactional
    public Optional<Order> findOrder(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional
    private Order saveOrder(Event event) {
        val order = new Order();
        order.setOrderId(event.getEventData().get("orderId").asLong());
        order.setCustomerId(event.getEventData().get("customerId").asLong());
        order.setEmployeeId(event.getEventData().get("employeeId").asLong());
        order.setOrderDate(LocalDateTime.now());
        orderRepository.save(order);
        return order;
    }
}
