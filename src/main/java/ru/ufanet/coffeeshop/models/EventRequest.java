package ru.ufanet.coffeeshop.models;

import lombok.Data;
import ru.ufanet.coffeeshop.models.Item;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventRequest {
    private Long orderId;
    private String eventType;
    private Long customerId;
    private Long employeeId;
    private LocalDateTime expectedTime;
    private List<Item> items;
    private String price;
    private String reason;
    private LocalDateTime time;
}
