package ru.clevertec.ecl.services;

import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.Pageable;
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

    public List<Tag> findAll(Pageable pageable) {
        return tagDao.findAll(pageable).getContent();
    }

    public Tag findById(long id) {
        return tagDao.findById(id).orElseThrow(() -> new TagNotFoundException(id));
    }

    public void add(Tag tag) {
        try {
            tagDao.save(tag);
        } catch (ConstraintViolationException e) {
            throw new TagAlreadyExist(tag.getName());
        }
    }

    public void update(long id, Tag updatedTag) {
        Tag tag = findById(id);
        tag.setName(updatedTag.getName());
        updatedTag.getCertificateList().forEach(tag.getCertificateList()::add);
        tagDao.save(tag);
    }

    public void deleteById(long id) {
        findById(id);
        tagDao.deleteById(id);
    }

    public List<Tag> findAllByName(String name, Pageable pageable) {
        return tagDao.findAllByNameContains(name, pageable).getContent();
    }
}
