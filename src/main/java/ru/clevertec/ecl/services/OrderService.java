package ru.clevertec.ecl.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dao.OrderDao;
import ru.clevertec.ecl.entities.Certificate;
import ru.clevertec.ecl.entities.Order;
import ru.clevertec.ecl.exceptions.OrderNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderDao orderDao;
    private final CertificateService certificateService;

    public List<Order> findAll(Pageable pageable) {
        return orderDao.findAll(pageable).getContent();
    }

    public Order findById(long id) {
        return orderDao.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    public void deleteById(long id) {
        Order order = findById(id);
        orderDao.delete(order);
    }

    public void add(long userId, long certificateId) {
        Certificate certificate = certificateService.findById(certificateId);
        Order order = orderDao.addOrderToUser(userId, certificate.getPrice()).orElseThrow();
        orderDao.addCertificateToOrder(order.getId(), certificateId);
    }
}
