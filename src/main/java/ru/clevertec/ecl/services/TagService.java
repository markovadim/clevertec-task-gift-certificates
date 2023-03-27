package ru.clevertec.ecl.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
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
        try {
            return tagDao.findById(id).get();
        } catch (EmptyResultDataAccessException e) {
            throw new TagNotFoundException(id);
        }
    }

    public void add(Tag tag) {
        try {
            tagDao.add(tag);
        } catch (DuplicateKeyException e) {
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
