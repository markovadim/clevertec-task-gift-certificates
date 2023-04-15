package ru.clevertec.ecl.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(long id) {
        super(String.format("User id = %d not found", id));
    }
}
