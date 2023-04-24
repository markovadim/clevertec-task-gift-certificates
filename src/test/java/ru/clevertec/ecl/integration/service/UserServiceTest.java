package ru.clevertec.ecl.integration.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.entities.Tag;
import ru.clevertec.ecl.exceptions.UserNotFoundException;
import ru.clevertec.ecl.integration.BaseIntegrationTest;
import ru.clevertec.ecl.services.UserService;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest extends BaseIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    void checkFindAllShouldReturnNotNullObject() {
        assertNotNull(userService.findAll(Pageable.unpaged()));
    }

    @Test
    void checkFindByIdShouldReturnNotNullObject() {
        assertNotNull(userService.findById(1L));
    }

    @Test
    void checkFindByIdShouldThrowUserNotFoundException() {
        assertThrows(UserNotFoundException.class, () -> userService.findById(13L));
    }

    @Test
    void checkFindUserOrdersShouldReturnTwoOrders() {
        int expectedOrders = 2;

        int actualOrders = userService.findUserOrders(1L, Pageable.unpaged()).size();

        assertEquals(expectedOrders, actualOrders);
    }

    @Test
    void checkBuyCertificateShouldAddOrderInDatabase() {
        int expectedOrders = 3;
        userService.buyCertificate(1L, 1L);

        int actualOrders = userService.findUserOrders(1L, Pageable.unpaged()).size();

        assertEquals(expectedOrders, actualOrders);
    }

    @Test
    void checkFindMostPopularTagFromMostHighCostOrderShouldReturnAnyTag() {
        Tag tag = userService.findMostPopularTagFromMostHighCostOrder(1L);

        assertNotNull(tag);
    }
}
