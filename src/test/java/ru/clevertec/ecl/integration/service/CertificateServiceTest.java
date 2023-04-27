package ru.clevertec.ecl.integration.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.CertificateDto;
import ru.clevertec.ecl.entities.Certificate;
import ru.clevertec.ecl.exceptions.CertificateNotFoundException;
import ru.clevertec.ecl.integration.BaseIntegrationTest;
import ru.clevertec.ecl.services.CertificateService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CertificateServiceTest extends BaseIntegrationTest {

    @Autowired
    private CertificateService certificateService;

    @Test
    void checkFindAllShouldReturn5Certificates() {
        int expectedSize = 5;

        int actualSize = certificateService.findAll(Pageable.unpaged()).size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    void checkFindByIdShouldReturnExpectedName() {
        String expectedName = "certificate_1";

        String actualName = certificateService.findById(1L).getName();

        assertEquals(expectedName, actualName);
    }

    @Test
    void checkAddShouldCreateCertificate() {
        Certificate certificate = new Certificate("certificate_6", "none", 13.31, Duration.ZERO, LocalDateTime.now(), LocalDateTime.now());

        assertDoesNotThrow(() -> certificateService.add(certificate));
    }

    @Test
    void checkUpdateShouldThrowException() {
        assertThrows(CertificateNotFoundException.class, () -> certificateService.update(6L, CertificateDto.builder().name("test").build()));
    }

    @Test
    void checkUpdateShouldUpdateCertificateName() {
        String expectedName = "new_certificate_from_test";
        CertificateDto certificateDto = CertificateDto.builder().name(expectedName).build();

        certificateService.update(1L, certificateDto);
        String actualName = certificateService.findById(1L).getName();

        assertEquals(expectedName, actualName);
    }

    @Test
    void checkDeleteByIdShouldRemoveCertificate() {
        int expectedSize = 4;

        certificateService.deleteById(3L);
        int actualSize = certificateService.findAll(Pageable.unpaged()).size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    void checkFindByFilterShouldReturnAllCertificates() {
        List<Certificate> expectedList = certificateService.findAll(Pageable.unpaged());

        List<Certificate> actualList = certificateService.findByFilter("certificate", "data.sql", 11, 45, Pageable.unpaged());

        assertEquals(expectedList.size(), actualList.size());
    }

    @Test
    void checkFindTagsShouldReturnThreeTags() {
        int expectedAmount = 3;

        int actualAmount = certificateService.findTags(1L, Pageable.unpaged()).size();

        assertEquals(expectedAmount, actualAmount);
    }

    @Test
    void checkFindByTagsShouldReturnListWithOneCertificate() {
        int expectedSize = 1;

        int actualSize = certificateService.findByTags(Pageable.unpaged(), new String[]{"women"}).size();

        assertEquals(expectedSize, actualSize);
    }
}
