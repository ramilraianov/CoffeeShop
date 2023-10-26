package ru.ufanet.coffeeshop.exceptions;

public class EventNotAllowed extends RuntimeException {
    @Override
    public String getMessage() {
        return "Event not allowed";
    }
}
