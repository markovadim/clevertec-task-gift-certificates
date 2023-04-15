package ru.clevertec.ecl.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.ecl.dto.CertificateDto;
import ru.clevertec.ecl.exceptions.CertificateNotFoundException;
import ru.clevertec.ecl.mapping.CertificateMapper;
import ru.clevertec.ecl.mapping.TagMapper;
import ru.clevertec.ecl.services.CertificateService;
import ru.clevertec.ecl.util.MockUtil;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CertificateController.class)
class CertificateControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CertificateService certificateService;
    @MockBean
    private CertificateMapper certificateMapper;
    @MockBean
    private TagMapper tagMapper;

    @Test
    void checkFindAllShouldReturnJsonAndStatusOk() throws Exception {
        doReturn(MockUtil.certificateList()).when(certificateService).findAll(Pageable.unpaged());
        doReturn(MockUtil.certificateDtoList()).when(certificateMapper).toDtoList(MockUtil.certificateList());

        mockMvc.perform(get("/certificates"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void checkFindByIdShouldReturnOneCertificate() throws Exception {
        doReturn(MockUtil.certificate()).when(certificateService).findById(1L);
        doReturn(MockUtil.certificateDto()).when(certificateMapper).toDto(MockUtil.certificate());

        long id = 1L;

        mockMvc.perform(get("/certificates/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void checkFindByIdShouldReturnNotFoundStatus() throws Exception {
        doThrow(new CertificateNotFoundException(1L)).when(certificateService).findById(1L);

        long id = 1L;

        mockMvc.perform(get("/certificates/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void checkAddShouldReturnStatusCreated() throws Exception {
        CertificateDto certificateDto = CertificateDto
                .builder()
                .name("demo")
                .description("description")
                .price(12.23)
                .build();

        doNothing().when(certificateService).add(MockUtil.certificate());
        doReturn(MockUtil.certificate()).when(certificateMapper).toEntity(certificateDto);

        mockMvc.perform(post("/certificates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(certificateDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void checkUpdateShouldReturnStatusOk() throws Exception {
        doNothing().when(certificateService).update(1L, MockUtil.certificateDto());

        mockMvc.perform(patch("/certificates/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(MockUtil.certificateDto())))
                .andExpect(status().isOk());
    }

    @Test
    void checkDeleteByIdShouldReturnNoContent() throws Exception {
        doNothing().when(certificateService).deleteById(1L);

        mockMvc.perform(delete("/certificates/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void checkFindByFilterShouldReturnCertificateList() throws Exception {
        doReturn(MockUtil.certificateList()).when(certificateService).findByFilter("Name", "Description", 1.0, 12.0, Pageable.unpaged());
        doReturn(MockUtil.certificateDtoList()).when(certificateMapper).toDtoList(MockUtil.certificateList());

        mockMvc.perform(get("/certificates/filter")
                        .param("name", "name")
                        .param("description", "description")
                        .param("minPrice", "1.0")
                        .param("maxPrice", "12.0"))
                .andExpect(status().isOk());
    }

    @Test
    void checkFindTagsShouldReturnStatusOkAndJsonType() throws Exception {
        doReturn(MockUtil.tagDtoList()).when(tagMapper).toDtoList(MockUtil.tagList());

        mockMvc.perform(get("/certificates/{id}/tags", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void checkFindByTagsShouldReturnList() throws Exception {
        doReturn(MockUtil.certificateDtoList()).when(certificateMapper).toDtoList(MockUtil.certificateList());

        mockMvc.perform(get("/certificates/search")
                        .param("tag", "tag_1")
                        .param("tag", "tag_2"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}