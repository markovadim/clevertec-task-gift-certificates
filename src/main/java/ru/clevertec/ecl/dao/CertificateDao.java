package ru.clevertec.ecl.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.entities.Certificate;

import java.util.List;

@Repository
public interface CertificateDao extends CrudRepository<Certificate, Long> {

    /**
     * @return certificate list from database
     * @see Certificate
     */
    List<Certificate> findAll();

    /**
     * @return find certificate list with parameters
     */
    List<Certificate> findAllByNameContainsOrDescriptionContainsOrPriceIsGreaterThanAndPriceIsLessThan(String name, String description, double minPrice, double maxPrice);
}
