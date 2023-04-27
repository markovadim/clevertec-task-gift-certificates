package ru.clevertec.ecl.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dao.UserDao;
import ru.clevertec.ecl.entities.Order;
import ru.clevertec.ecl.entities.Tag;
import ru.clevertec.ecl.entities.User;
import ru.clevertec.ecl.exceptions.UserNotFoundException;
import ru.clevertec.ecl.util.MockUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDao userDao;
    @InjectMocks
    private UserService userService;
    @Mock
    private OrderService orderService;

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
    void checkBuyCertificate() {
        doNothing().when(orderService).add(1L, 11L);

        userService.buyCertificate(1L, 11L);

        verify(orderService).add(1L, 11L);
    }

    @Test
    void checkFindMostPopularTagFromMostHighCostOrder() {
        doThrow(UserNotFoundException.class).when(userDao).findById(1L);

        assertThrows(UserNotFoundException.class, () -> userService.findMostPopularTagFromMostHighCostOrder(1L));
    }

    @Test
    void checkFindMostPopularTagShouldReturnTag() {
        doReturn(Optional.of(MockUtil.userList().get(0))).when(userDao).findById(1L);
        doReturn(Optional.of(MockUtil.tagList().get(0))).when(userDao).findMostPopularTagFromMostHighCostOrder(1L);

        Tag tag = userService.findMostPopularTagFromMostHighCostOrder(1L);

        assertNotNull(tag);
    }

    @Test
    void checkFindAnyTagShouldReturnFirstTagFromOrderList() throws InvocationTargetException, IllegalAccessException {
        Method method = ReflectionUtils.getRequiredMethod(UserService.class, "findAnyTag", User.class);
        method.setAccessible(true);

        Object tag = method.invoke(userService, MockUtil.userList().get(0));

        assertNotNull(tag);
    }
}