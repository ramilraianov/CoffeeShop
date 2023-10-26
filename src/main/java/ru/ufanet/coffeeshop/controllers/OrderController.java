package ru.ufanet.coffeeshop.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ufanet.coffeeshop.models.EventRequest;
import ru.ufanet.coffeeshop.models.Event;
import ru.ufanet.coffeeshop.services.OrderService;
import ru.ufanet.coffeeshop.util.EventConverter;


@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/events")
    public ResponseEntity<String> processEvent(@RequestBody EventRequest eventRequest) {
        Event event;
        try {
            event = EventConverter.convertEventRequestToEvent(eventRequest);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        try {
            orderService.publishEvent(event);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Order " + event.getEventType().name());
    }
}

