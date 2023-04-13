package ru.clevertec.ecl.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dao.CertificateDao;
import ru.clevertec.ecl.entities.Certificate;
import ru.clevertec.ecl.exceptions.CertificateNotFoundException;
import ru.clevertec.ecl.mapping.CertificateMapper;
import ru.clevertec.ecl.util.MockUtil;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CertificateServiceTest {

    @Mock
    private CertificateDao certificateDao;
    @InjectMocks
    private CertificateService certificateService;
    @Mock
    private CertificateMapper certificateMapper;

    @Test
    void checkFindAllShouldReturnSizeOfThree() {
        doReturn(new PageImpl<>(MockUtil.certificateList())).when(certificateDao).findAll(Pageable.unpaged());

        int expectedSize = 1;
        int actualSize = certificateService.findAll(Pageable.unpaged()).size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    void checkFindByIdShouldThrowException() {
        doThrow(CertificateNotFoundException.class).when(certificateDao).findById(1L);

        assertThrows(CertificateNotFoundException.class, () -> certificateService.findById(1));
    }

    @Test
    void checkAddShouldReturnCertificate() {
        Certificate certificate = new Certificate("demo", "description", 12.23, Duration.ZERO, LocalDateTime.now(), LocalDateTime.now());
        doReturn(MockUtil.certificate()).when(certificateDao).save(certificate);

        String expectedDescription = "description";
        String actualDescription = MockUtil.certificate().getDescription();
        certificateService.add(certificate);

        assertEquals(expectedDescription, actualDescription);
    }

    @Test
    void checkUpdateShouldThrowException() {
        doThrow(CertificateNotFoundException.class).when(certificateDao).findById(2L);

        assertThrows(CertificateNotFoundException.class, () -> certificateService.update(2L, MockUtil.certificateDtoList().get(0)));
    }

    @Test
    void checkDeleteByIdShouldThrowException() {
        doThrow(CertificateNotFoundException.class).when(certificateDao).findById(1L);

        assertThrows(CertificateNotFoundException.class, () -> certificateService.deleteById(1));
    }

    @Test
    void checkDeleteByIdShouldCallMethod() {
        doReturn(Optional.of(MockUtil.certificate())).when(certificateDao).findById(1L);
        doNothing().when(certificateDao).deleteById(1L);

        certificateService.deleteById(1L);

        verify(certificateDao).deleteById(1L);
    }

    @Test
    void checkFindByFilter() {
        doReturn(new PageImpl<>(MockUtil.certificateList())).when(certificateDao).findAllByNameContainsOrDescriptionContainsOrPriceIsGreaterThanAndPriceIsLessThan("demo", "description", 1.0, 40.0, Pageable.unpaged());

        int expectedSize = 1;
        int actualSize = certificateService.findByFilter("demo", "description", 1.0, 40.0, Pageable.unpaged()).size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    void checkFindTagsShouldReturnTagList() {
        doReturn(Optional.of(MockUtil.certificateList().get(0))).when(certificateDao).findById(1L);

        String expectedName = "tag_1";
        String actualName = certificateService.findTags(1L, Pageable.unpaged()).get(0).getName();

        assertEquals(expectedName, actualName);
    }

    @Test
    void checkFindByTagsShouldReturnCertificateList() {
        doReturn(new PageImpl<>(MockUtil.certificateList())).when(certificateDao).certificatesBySomeTags(new String[]{"tag_1"}, Pageable.unpaged());

        String expectedName = "demo";
        String actualName = certificateService.findByTags(Pageable.unpaged(), new String[]{"tag_1"}).get(0).getName();

        assertEquals(expectedName, actualName);
    }
}