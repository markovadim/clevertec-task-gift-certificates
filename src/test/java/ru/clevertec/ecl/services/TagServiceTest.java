package ru.clevertec.ecl.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.ecl.dao.TagDao;
import ru.clevertec.ecl.entities.Tag;
import ru.clevertec.ecl.exceptions.TagAlreadyExist;
import ru.clevertec.ecl.exceptions.TagNotFoundException;
import ru.clevertec.ecl.util.MockUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {
    @Mock
    private TagDao tagDao;
    @InjectMocks
    private TagService tagService;

    @Test
    void checkFindAllShouldReturnRightListSize() {
        doReturn(MockUtil.getTags()).when(tagDao).findAll();

        int expectedSize = 2;
        int actualSize = tagService.findAll().size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    void checkFindByIdShouldThrowException() {
        doThrow(TagNotFoundException.class).when(tagDao).findById(1);

        assertThrows(TagNotFoundException.class, () -> tagService.findById(1));
    }

    @Test
    void checkAddShouldThrowException() {
        Tag tag = new Tag(111, "tag_1");

        doThrow(TagAlreadyExist.class).when(tagDao).add(tag);

        assertThrows(TagAlreadyExist.class, () -> tagService.add(tag));
    }

    @Test
    void checkUpdateShouldThrowException() {
        Tag tag = new Tag(32, "tag_tag");
        doThrow(TagNotFoundException.class).when(tagDao).findById(11);

        assertThrows(TagNotFoundException.class, () -> tagService.update(11, tag));
    }

    @Test
    void checkDeleteByIdShouldThrowException() {
        doThrow(TagNotFoundException.class).when(tagDao).findById(1);

        assertThrows(TagNotFoundException.class, () -> tagService.deleteById(1));
    }
}