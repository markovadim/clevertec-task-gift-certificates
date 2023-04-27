package ru.clevertec.ecl.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {

    private String errorMessage;
    private int errorCode;
}
