package ru.clevertec.ecl.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.entities.Order;
import ru.clevertec.ecl.util.QueryData;

import java.util.Optional;

@Repository
public interface OrderDao extends CrudRepository<Order, Long> {

    Page<Order> findAll(Pageable pageable);

    /**
     * The order is associated with the certificate.
     *
     * @param orderId       - search parameter
     * @param certificateId = search parameter
     */
    @Modifying
    @Query(value = QueryData.ORDER_CERTIFICATE, nativeQuery = true)
    @Transactional
    void addCertificateToOrder(@Param("orderId") long orderId, @Param("certificateId") long certificateId);

    /**
     * Order has only one certificate.
     * Create order for user by id and certificate price.
     *
     * @param userId     - search parameter
     * @param orderPrice - = certificate price
     * @return optional of Order
     */
    @Query(value = QueryData.CREATE_ORDER, nativeQuery = true)
    Optional<Order> addOrderToUser(@Param("userId") long userId, @Param("orderPrice") double orderPrice);
}
