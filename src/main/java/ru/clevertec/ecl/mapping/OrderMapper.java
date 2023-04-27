package ru.clevertec.ecl.mapping;

import org.mapstruct.Mapper;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.entities.Order;

import java.util.List;

@Mapper
public interface OrderMapper {

    OrderDto toDto(Order order);

    Order toEntity(OrderDto orderDto);

    List<OrderDto> toDtoList(List<Order> orders);
}
