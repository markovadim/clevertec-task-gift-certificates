package ru.clevertec.ecl.integration.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.entities.Tag;
import ru.clevertec.ecl.exceptions.TagNotFoundException;
import ru.clevertec.ecl.integration.BaseIntegrationTest;
import ru.clevertec.ecl.services.TagService;

import static org.junit.jupiter.api.Assertions.*;


class TagServiceTest extends BaseIntegrationTest {

    @Autowired
    private TagService tagService;

    @Test
    void checkFindAllShouldReturnSize4() {
        int expectedSize = 4;

        int actualSize = tagService.findAll(Pageable.unpaged()).size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    void checkFindByIdShouldReturnExpectedTagName() {
        String expectedName = "tag_1";

        String actualName = tagService.findById(1L).getName();

        assertEquals(expectedName, actualName);
    }

    @Test
    void checkAddShouldCrateTagInDatabase() {
        Tag tag = new Tag("integration_tag");

        assertDoesNotThrow(() -> tagService.add(tag));
    }

    @Test
    void checkUpdateShouldUpdateTagNameInDataBase() {
        TagDto tagDto = new TagDto(1L, "updated_tag");
        String expectedName = tagDto.getName();

        tagService.update(1L, tagDto);

        String actualName = tagService.findById(1L).getName();

        assertEquals(expectedName, actualName);
    }

    @Test
    void checkDeleteByIdShouldDeleteTagFromDatabase() {
        assertDoesNotThrow(() -> tagService.deleteById(2L));
    }

    @Test
    void checkDeleteByIdShouldThrowExceptionTagNotFound() {
        assertThrows(TagNotFoundException.class, () -> tagService.deleteById(0));
    }

    @Test
    void checkFindAllByNameShouldReturnTagList() {
        int expectedSize = 2;

        int actualSize = tagService.findAllByName("tag", Pageable.unpaged()).size();

        assertEquals(expectedSize, actualSize);
    }
}
