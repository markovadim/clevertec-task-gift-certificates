package ru.clevertec.ecl.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dao.OrderDao;
import ru.clevertec.ecl.entities.Order;
import ru.clevertec.ecl.util.MockUtil;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderDao orderDao;
    @InjectMocks
    private OrderService orderService;

    @Test
    void checkFindAllShouldReturnNotEmptyList() {
        doReturn(new PageImpl<>(MockUtil.orderList())).when(orderDao).findAll(Pageable.unpaged());

        assertNotNull(orderService.findAll(Pageable.unpaged()));
    }

    @Test
    void checkFindByIdShouldReturnNotNullOrder() {
        doReturn(Optional.of(MockUtil.orderList().get(0))).when(orderDao).findById(1L);

        assertNotNull(orderService.findById(1L));
    }

    @Test
    void checkDeleteByIdShouldRemoveOrder() {
        Order order = MockUtil.orderList().get(0);
        doReturn(Optional.of(order)).when(orderDao).findById(1L);

        orderService.deleteById(1L);

        assertDoesNotThrow(() -> orderService.deleteById(1L));
    }
}