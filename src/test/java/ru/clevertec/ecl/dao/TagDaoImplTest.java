package ru.clevertec.ecl.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.clevertec.ecl.configuration.AppTestConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppTestConfiguration.class})
@ActiveProfiles("dev")
class TagDaoImplTest {

    @Autowired
    private TagDaoImpl tagDao;

    @Test
    void findAll() {
        assertEquals(4, tagDao.findAll().size());
    }
}