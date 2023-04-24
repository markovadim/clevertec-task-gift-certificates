package ru.clevertec.ecl.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.entities.Tag;

@Repository
public interface TagDao extends CrudRepository<Tag, Long> {

    /**
     * @return tag list from database
     * @see Tag
     */
    Page<Tag> findAll(Pageable pageable);

    /**
     * @param name - keyword for search tags
     * @return tag list
     */
    Page<Tag> findAllByNameContains(String name, Pageable pageable);
}
