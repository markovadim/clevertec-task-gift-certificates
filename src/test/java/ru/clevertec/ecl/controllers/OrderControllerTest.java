package ru.clevertec.ecl.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.ecl.mapping.OrderMapper;
import ru.clevertec.ecl.services.OrderService;
import ru.clevertec.ecl.util.MockUtil;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;
    @MockBean
    private OrderMapper orderMapper;


    @Test
    void checkFindAllShouldReturnJsonListAndStatusOk() throws Exception {
        doReturn(MockUtil.orderList()).when(orderService).findAll(Pageable.unpaged());

        mockMvc.perform(get("/orders"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void checkFindByIdShouldReturnStatusOk() throws Exception {
        doReturn(MockUtil.orderList().get(0)).when(orderService).findById(1L);

        mockMvc.perform(get("/orders/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void checkDeleteByIdShouldReturnNoContentStatus() throws Exception {
        doNothing().when(orderService).deleteById(1L);

        mockMvc.perform(delete("/orders/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}