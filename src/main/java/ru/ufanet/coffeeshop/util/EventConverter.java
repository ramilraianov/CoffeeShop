package ru.ufanet.coffeeshop.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.ufanet.coffeeshop.models.EventRequest;
import ru.ufanet.coffeeshop.models.Event;
import ru.ufanet.coffeeshop.models.EventType;


@Component
public class EventConverter {
    public static Event convertEventRequestToEvent(EventRequest eventRequest) {
        Event event = new Event();
        ObjectMapper objectMapper = new ObjectMapper();
        event.setEventData(objectMapper.createObjectNode());

        if (eventRequest.getOrderId() == null) {
            throw new IllegalArgumentException("Идентификатор заказа (orderId) является обязательным полем.");
        }
        event.getEventData().put("orderId", eventRequest.getOrderId());

        if (eventRequest.getEmployeeId() == null) {
            throw new IllegalArgumentException("Идентификатор сотрудника (employeeId) является обязательным полем.");
        }
        event.getEventData().put("employeeId", eventRequest.getEmployeeId());

        if (eventRequest.getTime() == null) {
            throw new IllegalArgumentException("Время (time) является обязательным полем.");
        }
        event.setEventTimestamp(eventRequest.getTime());

        if (eventRequest.getTime() == null) {
            throw new IllegalArgumentException("Тип события (eventType) является обязательным полем.");
        }
        event.setEventType(EventType.valueOf(eventRequest.getEventType()));

        if (eventRequest.getPrice() != null) {
            event.getEventData().put("price", eventRequest.getPrice());
        } else if (event.getEventType() == EventType.RESERVED) {
            throw new IllegalArgumentException("Цена (price) является обязательным полем.");
        }

        if (eventRequest.getItems() != null) {
            event.getEventData().put("items", eventRequest.getItems().toString());
        } else if (event.getEventType() == EventType.RESERVED) {
            throw new IllegalArgumentException("Товары (items) является обязательным полем.");
        }

        if (eventRequest.getCustomerId() != null) {
            event.getEventData().put("customerId", eventRequest.getCustomerId());
        } else if (event.getEventType() == EventType.RESERVED) {
            throw new IllegalArgumentException("Покупатель (customerId) является обязательным полем.");
        }

        if (eventRequest.getReason() != null) {
            event.getEventData().put("reason", eventRequest.getReason());
        } else if (event.getEventType() == EventType.CANCELED) {
            throw new IllegalArgumentException("Причина отмены (reason) является обязательным полем.");
        }

        return event;
    }
}