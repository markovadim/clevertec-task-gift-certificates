package ru.clevertec.ecl.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.entities.Tag;
import ru.clevertec.ecl.entities.User;
import ru.clevertec.ecl.util.QueryData;

import java.util.Optional;


@Repository
public interface UserDao extends CrudRepository<User, Long> {

    /**
     * @param pageable - pagination of result
     * @return - page of users
     */
    Page<User> findAll(Pageable pageable);

    /**
     * User search by id.
     * Searching the most expensive order.
     * Search for the most popular tag in the found order.
     * If most popular tag not found, layer return first tag from db.
     *
     * @param userId - search parameter
     * @return optional of Tag.
     */
    @Query(value = QueryData.FIND_POPULAR_TAG_FROM_ORDER, nativeQuery = true)
    Optional<Tag> findMostPopularTagFromMostHighCostOrder(@Param("userId") long userId);
}
