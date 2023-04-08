package ru.clevertec.ecl.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.entities.Tag;

import java.util.List;

@Repository
public interface TagDao extends CrudRepository<Tag, Long> {

    /**
     * @return tag list from database
     * @see Tag
     */
    List<Tag> findAll();

    /**
     * @param name - keyword for search tags
     * @return tag list
     */
    List<Tag> findAllByNameContains(String name);
}
