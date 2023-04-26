package ru.clevertec.ecl.exceptions;

public class CertificateNotFoundException extends RuntimeException {

    public CertificateNotFoundException(long id) {
        super(String.format("Certificate id = %d not found", id));
    }
}
