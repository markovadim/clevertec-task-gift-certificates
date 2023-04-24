package ru.clevertec.ecl.integration.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.integration.BaseIntegrationTest;
import ru.clevertec.ecl.services.OrderService;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderServiceTest extends BaseIntegrationTest {

    @Autowired
    private OrderService orderService;


    @Test
    void checkFindAllShouldReturnSize() {
        int expectedSize = 2;

        int actualSize = orderService.findAll(Pageable.unpaged()).size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    void checkFindByIdShouldReturnOrderNumber321() {
        int expectedNumber = 321;

        long actualNumber = orderService.findById(1L).getNumber();

        assertEquals(expectedNumber, actualNumber);
    }

    @Test
    void checkDeleteByIdShouldRemoveOrderWithId1() {
        int expectedSize = 1;

        orderService.deleteById(1L);
        int actualSize = orderService.findAll(Pageable.unpaged()).size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    void checkAddShouldAddNewOrder() {
        int expectedSize = 3;

        orderService.add(2, 1);
        int actualSize = orderService.findAll(Pageable.unpaged()).size();

        assertEquals(expectedSize, actualSize);
    }
}
