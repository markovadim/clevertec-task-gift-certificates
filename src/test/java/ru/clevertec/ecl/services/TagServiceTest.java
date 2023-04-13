package ru.clevertec.ecl.services;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dao.TagDao;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.entities.Tag;
import ru.clevertec.ecl.exceptions.TagAlreadyExist;
import ru.clevertec.ecl.exceptions.TagNotFoundException;
import ru.clevertec.ecl.util.MockUtil;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {
    @Mock
    private TagDao tagDao;
    @InjectMocks
    private TagService tagService;

    @Test
    void checkFindAllShouldReturnRightListSize() {
        doReturn(new PageImpl<>(MockUtil.tagList())).when(tagDao).findAll(Pageable.unpaged());

        int expectedSize = 2;
        int actualSize = tagService.findAll(Pageable.unpaged()).size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    void checkFindByIdShouldThrowException() {
        doThrow(TagNotFoundException.class).when(tagDao).findById(1L);

        assertThrows(TagNotFoundException.class, () -> tagService.findById(1L));
    }

    @Test
    void checkFindByIdShouldReturnRightTagName() {
        doReturn(Optional.of(MockUtil.tagList().get(0))).when(tagDao).findById(1L);

        String expectedName = "tag_1";
        String actualName = tagService.findById(1L).getName();

        assertEquals(expectedName, actualName);
    }

    @Test
    void checkAddShouldThrowException() {
        Tag tag = new Tag("tag_1");

        doThrow(TagAlreadyExist.class).when(tagDao).save(tag);

        assertThrows(TagAlreadyExist.class, () -> tagService.add(tag));
    }

    @Test
    void checkAddTagShouldThrowConstraintViolationException() {
        Tag tag = MockUtil.tagList().get(0);
        doThrow(ConstraintViolationException.class).when(tagDao).save(tag);

        assertThrows(TagAlreadyExist.class, () -> tagService.add(tag));
    }

    @Test
    void checkAddShouldCreateTag() {
        Tag tag = MockUtil.tagList().get(0);
        doReturn(tag).when(tagDao).save(tag);

        tagService.add(tag);

        verify(tagDao).save(tag);
    }

    @Test
    void checkUpdateShouldThrowException() {
        TagDto tag = new TagDto(11L, "tag_tag");
        doThrow(TagNotFoundException.class).when(tagDao).findById(11L);

        assertThrows(TagNotFoundException.class, () -> tagService.update(11, tag));
    }

    @Test
    void checkUpdateShouldChangeTagName() {
        TagDto updatedTagDto = new TagDto(11L, "tag_tag");
        Tag tagFromDb = new Tag("tag_1");
        doReturn(Optional.of(tagFromDb)).when(tagDao).findById(1L);
        tagService.update(1L, updatedTagDto);

        String expectedName = "tag_tag";
        String actualName = tagFromDb.getName();

        assertEquals(expectedName, actualName);
    }

    @Test
    void checkDeleteByIdShouldThrowException() {
        doThrow(TagNotFoundException.class).when(tagDao).findById(1L);

        assertThrows(TagNotFoundException.class, () -> tagService.deleteById(1));
    }

    @Test
    void checkDeleteByIdShouldCallMethod() {
        doReturn(Optional.of(MockUtil.tagList().get(0))).when(tagDao).findById(1L);
        doNothing().when(tagDao).deleteById(1L);

        tagService.deleteById(1L);

        verify(tagDao).deleteById(1L);
    }

    @Test
    void checkFindAllByNameShouldReturnTagList() {
        doReturn(new PageImpl<>(MockUtil.tagList())).when(tagDao).findAllByNameContains("tag", Pageable.unpaged());

        int expectedListSize = 2;
        int actualListSize = tagService.findAllByName("tag", Pageable.unpaged()).size();

        assertEquals(expectedListSize, actualListSize);
    }
}