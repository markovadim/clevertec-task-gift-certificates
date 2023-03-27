package ru.clevertec.ecl.dao;

import ru.clevertec.ecl.entities.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao {

    /**
     * @return tag list from database
     * @see Tag
     */
    List<Tag> findAll();

    /**
     * @param id - unique param of entity
     * @return Optional<Tag>. Throw exception if tag not found
     * @see ru.clevertec.ecl.exceptions.TagNotFoundException
     */
    Optional<Tag> findById(long id);

    /**
     * @param tag - request body from controller
     * @see ru.clevertec.ecl.controllers.TagController
     */
    void add(Tag tag);

    /**
     * @param id - unique param of entity. Throw exception if tag not found
     * @param tag - request body of new tag.
     * @see ru.clevertec.ecl.exceptions.TagNotFoundException
     */
    void update(long id, Tag tag);

    /**
     * @param id - unique param of entity. Throw exception if tag not found
     */
    void deleteById(long id);
}
