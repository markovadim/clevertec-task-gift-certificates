package ru.clevertec.ecl.services;

import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dao.TagDao;
import ru.clevertec.ecl.entities.Tag;
import ru.clevertec.ecl.exceptions.TagAlreadyExist;
import ru.clevertec.ecl.exceptions.TagNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagDao tagDao;

    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    public Tag findById(long id) {
        return tagDao.findById(id).orElseThrow(() -> new TagNotFoundException(id));
    }

    public void add(Tag tag) {
        try {
            tagDao.add(tag);
        } catch (ConstraintViolationException e) {
            throw new TagAlreadyExist(tag.getName());
        }
    }

    public void update(long id, Tag tag) {
        findById(id);
        tagDao.update(id, tag);
    }

    public void deleteById(long id) {
        findById(id);
        tagDao.deleteById(id);
    }
}
