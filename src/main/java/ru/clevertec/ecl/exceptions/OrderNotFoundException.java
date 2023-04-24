package ru.clevertec.ecl.exceptions;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(long id) {
        super(String.format("Order id = %d not found", id));
    }
}
