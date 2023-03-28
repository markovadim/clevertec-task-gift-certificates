package ru.clevertec.ecl.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotCorrectedId(TagNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(e.getMessage(), getErrorCode(e)));
    }

    @ExceptionHandler(TagAlreadyExist.class)
    public ResponseEntity<ExceptionResponse> handleNotCorrectedId(TagAlreadyExist e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(e.getMessage(), e.getMessage().toCharArray().length));
    }

    @ExceptionHandler(CertificateNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotCorrectedId(CertificateNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(e.getMessage(), getErrorCode(e)));
    }

    private int getErrorCode(Exception e) {
        String errorId = e.getMessage().replaceAll("\\D", "");
        return Integer.parseInt(HttpStatus.NOT_FOUND.value() + errorId);
    }
}
