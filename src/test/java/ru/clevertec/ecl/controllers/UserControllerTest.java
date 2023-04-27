package ru.clevertec.ecl.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.ecl.mapping.OrderMapper;
import ru.clevertec.ecl.mapping.TagMapper;
import ru.clevertec.ecl.mapping.UserMapper;
import ru.clevertec.ecl.services.UserService;
import ru.clevertec.ecl.util.MockUtil;

import java.util.HashSet;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private UserMapper userMapper;
    @MockBean
    private OrderMapper orderMapper;
    @MockBean
    private TagMapper tagMapper;

    @Test
    void checkFindAllShouldReturnListAndStatusOk() throws Exception {
        doReturn(MockUtil.userDtoList()).when(userMapper).toDtoList(MockUtil.userList());

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void checkFindByIdShouldReturnStatusOk() throws Exception {
        doReturn(MockUtil.userDtoList().get(0)).when(userMapper).toDto(MockUtil.userList().get(0));

        mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void checkFindUserOrdersShouldReturnOrderList() throws Exception {
        doReturn(new HashSet<>(MockUtil.orderList())).when(userService).findUserOrders(1L, Pageable.unpaged());

        mockMvc.perform(get("/users/{id}/orders", 1L))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void checkBuyCertificateShouldReturnStatusOk() throws Exception {
        doNothing().when(userService).buyCertificate(1L, 11L);

        mockMvc.perform(post("/users/{id}", 1L)
                        .param("giftId", "11"))
                .andExpect(status().isOk());
    }

    @Test
    void checkFindMostPopularTagFromMostHighCostOrderShouldReturnTagAndStatusOk() throws Exception {
        doReturn(MockUtil.tagDtoList().get(0)).when(tagMapper).toDto(MockUtil.tagList().get(0));

        mockMvc.perform(get("/users/{id}/top", 1L))
                .andExpect(status().isOk());
    }
}