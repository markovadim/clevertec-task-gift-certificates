package ru.clevertec.ecl.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.ecl.mapping.TagMapper;
import ru.clevertec.ecl.services.TagService;
import ru.clevertec.ecl.util.MockUtil;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TagController.class)
class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TagService tagService;
    @MockBean
    private TagMapper tagMapper;

    @Test
    void checkFindAllShouldReturnStatusOkAndJson() throws Exception {
        doReturn(MockUtil.tagList()).when(tagService).findAll(Pageable.unpaged());

        mockMvc.perform(get("/tags"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void checkFindByIdShouldReturnStatusOk() throws Exception {
        doReturn(MockUtil.tagList().get(0)).when(tagService).findById(1L);
        doReturn(MockUtil.tagDtoList().get(0)).when(tagMapper).toDto(MockUtil.tagList().get(0));

        mockMvc.perform(get("/tags/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void checkAddShouldReturnStatusOfCreated() throws Exception {
        doNothing().when(tagService).add(MockUtil.tagList().get(0));

        mockMvc.perform(post("/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(MockUtil.tagDtoList().get(0))))
                .andExpect(status().isCreated());
    }

    @Test
    void checkUpdateShouldReturnStatusOk() throws Exception {
        doNothing().when(tagService).update(1L, MockUtil.tagDtoList().get(0));

        mockMvc.perform(patch("/tags/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(MockUtil.tagDtoList().get(0))))
                .andExpect(status().isOk());
    }

    @Test
    void checkDeleteByIdShouldReturnStatusNoContent() throws Exception {
        doNothing().when(tagService).deleteById(1L);

        mockMvc.perform(delete("/tags/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void checkFindByNameShouldReturnTagList() throws Exception {
        doReturn(MockUtil.tagList()).when(tagService).findAllByName("tag_1", Pageable.unpaged());

        mockMvc.perform(get("/tags/filter")
                        .param("name", "tag_1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}