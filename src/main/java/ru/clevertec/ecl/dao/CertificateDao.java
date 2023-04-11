package ru.clevertec.ecl.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.entities.Certificate;
import ru.clevertec.ecl.util.QueryData;

@Repository
public interface CertificateDao extends CrudRepository<Certificate, Long> {

    /**
     * @return certificate list from database
     * @see Certificate
     */
    Page<Certificate> findAll(Pageable pageable);

    /**
     * @return find certificate list with parameters
     */
    Page<Certificate> findAllByNameContainsOrDescriptionContainsOrPriceIsGreaterThanAndPriceIsLessThan(String name, String description, double minPrice, double maxPrice, Pageable pageable);

    /**
     * @param tag      - tag array (request params from controller
     * @param pageable - pagination argument
     * @return page of certificates
     * @see ru.clevertec.ecl.controllers.CertificateController
     */
    @Query(value = QueryData.FIND_BY_TAGS, nativeQuery = true)
    Page<Certificate> certificatesBySomeTags(@Param("tag") String[] tag, Pageable pageable);
}
