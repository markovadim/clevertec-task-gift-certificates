package ru.clevertec.ecl.exceptions;

public class TagAlreadyExist extends RuntimeException {

    public TagAlreadyExist(String name) {
        super(String.format("Tag < %s > already exist", name));
    }
}
