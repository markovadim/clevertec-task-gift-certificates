package ru.clevertec.ecl.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dao.UserDao;
import ru.clevertec.ecl.entities.Order;
import ru.clevertec.ecl.entities.Tag;
import ru.clevertec.ecl.entities.User;
import ru.clevertec.ecl.exceptions.UserNotFoundException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final OrderService orderService;

    public List<User> findAll(Pageable pageable) {
        return userDao.findAll(pageable).getContent();
    }

    public User findById(long id) {
        return userDao.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public Set<Order> findUserOrders(long id, Pageable pageable) {
        Set<Order> orders = findById(id).getOrders();
        return new HashSet<>(new PageImpl<>(orders.stream().toList(), pageable, orders.size()).getContent());
    }

    public void buyCertificate(long userId, long certificateId) {
        orderService.add(userId, certificateId);
    }

    public Tag findMostPopularTagFromMostHighCostOrder(long userId) {
        User user = findById(userId);
        return userDao.findMostPopularTagFromMostHighCostOrder(userId).orElse(findAnyTag(user).get());
    }

    private Optional<Tag> findAnyTag(User user) {
        return user.getOrders()
                .stream()
                .max(Comparator.comparing(Order::getOrderPrice)).get()
                .getCertificates()
                .stream()
                .findFirst().get()
                .getTags()
                .stream()
                .findFirst();
    }
}
