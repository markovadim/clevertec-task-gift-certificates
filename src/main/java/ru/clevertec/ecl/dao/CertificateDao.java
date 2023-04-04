package ru.clevertec.ecl.dao;

import ru.clevertec.ecl.entities.Certificate;

import java.util.List;
import java.util.Optional;

public interface CertificateDao {

    /**
     * @return certificate list from database
     * @see Certificate
     */
    List<Certificate> findAll();

    /**
     * @param id - unique param of entity
     * @return Optional<Certificate>. Throw exception if certificate not found
     * @see ru.clevertec.ecl.exceptions.CertificateNotFoundException
     */
    Optional<Certificate> findById(long id);

    /**
     * @param certificate - request body from controller
     * @see ru.clevertec.ecl.controllers.CertificateController
     */
    void add(Certificate certificate);

    /**
     * @param id          - unique param of entity. Throw exception if certificate not found
     * @param certificate - request body of new certificate
     * @see ClassNotFoundException
     */
    void update(long id, Certificate certificate);

    /**
     * @param id - unique param of entity. Throw exception if certificate not found
     */
    void deleteById(long id);

    /**
     * @return find certificate list with parameters
     */
    List<Certificate> findByFilter(String name, String description, double minPrice, double maxPrice);
}
