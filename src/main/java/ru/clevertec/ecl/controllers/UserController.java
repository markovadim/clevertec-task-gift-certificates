package ru.clevertec.ecl.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.mapping.OrderMapper;
import ru.clevertec.ecl.mapping.TagMapper;
import ru.clevertec.ecl.mapping.UserMapper;
import ru.clevertec.ecl.services.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final OrderMapper orderMapper;
    private final TagMapper tagMapper;


    @GetMapping
    public ResponseEntity<List<UserDto>> findAll(@PageableDefault(sort = "name") Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(userMapper.toDtoList(userService.findAll(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userMapper.toDto(userService.findById(id)));
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<Set<OrderDto>> findUserOrders(@PathVariable long id, @PageableDefault(sort = "number") Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUserOrders(id, pageable).stream().map(orderMapper::toDto).collect(Collectors.toSet()));
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> buyCertificate(@PathVariable long id,
                                      @RequestParam long giftId) {
        userService.buyCertificate(id, giftId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/top")
    public ResponseEntity<TagDto> findMostPopularTagFromMostHighCostOrder(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(tagMapper.toDto(userService.findMostPopularTagFromMostHighCostOrder(id)));
    }
}
