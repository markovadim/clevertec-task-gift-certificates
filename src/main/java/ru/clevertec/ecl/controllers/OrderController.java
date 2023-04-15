package ru.clevertec.ecl.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.mapping.OrderMapper;
import ru.clevertec.ecl.services.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping
    public ResponseEntity<List<OrderDto>> findAll(@PageableDefault(sort = "number") Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(orderMapper.toDtoList(orderService.findAll(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> findById(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(orderMapper.toDto(orderService.findById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        orderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
