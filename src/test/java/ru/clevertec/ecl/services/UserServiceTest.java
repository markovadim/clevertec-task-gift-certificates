package ru.clevertec.ecl.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dao.UserDao;
import ru.clevertec.ecl.entities.Order;
import ru.clevertec.ecl.exceptions.CertificateNotFoundException;
import ru.clevertec.ecl.exceptions.UserNotFoundException;
import ru.clevertec.ecl.util.MockUtil;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDao userDao;
    @InjectMocks
    private UserService userService;
    @Mock
    private CertificateService certificateService;

    @Test
    void checkFindAllShouldReturnNotEmptyList() {
        doReturn(new PageImpl<>(MockUtil.userList())).when(userDao).findAll(Pageable.unpaged());

        assertNotNull(userService.findAll(Pageable.unpaged()));
    }

    @Test
    void checkFindByIdShouldReturnRightUserName() {
        doReturn(Optional.of(MockUtil.userList().get(0))).when(userDao).findById(1L);

        String expectedName = "user_1";
        String actualName = userService.findById(1L).getName();

        assertEquals(expectedName, actualName);
    }

    @Test
    void checkFindUserOrdersShouldReturnOrderList() {
        doReturn(Optional.of(MockUtil.userList().get(0))).when(userDao).findById(1L);

        Set<Order> expectedOrders = new HashSet<>(MockUtil.orderList());
        Set<Order> actualOrders = userService.findUserOrders(1L, Pageable.unpaged());

        assertEquals(expectedOrders, actualOrders);
    }

    @Test
    void checkFindMostPopularTagFromMostHighCostOrder() {
        doThrow(UserNotFoundException.class).when(userDao).findById(1L);

        assertThrows(UserNotFoundException.class, () -> userService.findMostPopularTagFromMostHighCostOrder(1L));
    }
}