package ru.ufanet.coffeeshop.exceptions;

public class OrderExist extends RuntimeException  {
    public OrderExist(String message) {
        super(message);
    }
}
