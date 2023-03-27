package ru.clevertec.ecl.exceptions;

public class TagNotFoundException extends RuntimeException {

    public TagNotFoundException(long id) {
        super(String.format("Tag id = %d not found", id));
    }
}
