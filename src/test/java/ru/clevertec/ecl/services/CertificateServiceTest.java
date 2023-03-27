package ru.clevertec.ecl.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.ecl.dao.CertificateDao;
import ru.clevertec.ecl.exceptions.CertificateNotFoundException;
import ru.clevertec.ecl.util.MockUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class CertificateServiceTest {

    @Mock
    private CertificateDao certificateDao;
    @InjectMocks
    private CertificateService certificateService;

    @Test
    void checkFindAllShouldReturnSizeOfThree() {
        doReturn(MockUtil.certificateList()).when(certificateDao).findAll();

        int expectedSize = 1;
        int actualSize = certificateService.findAll().size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    void checkFindByIdShouldThrowException() {
        doThrow(CertificateNotFoundException.class).when(certificateDao).findById(1);

        assertThrows(CertificateNotFoundException.class, () -> certificateService.findById(1));
    }

    @Test
    void checkUpdateShouldThrowException() {
        doThrow(CertificateNotFoundException.class).when(certificateDao).findById(2);

        assertThrows(CertificateNotFoundException.class, () -> certificateService.update(2, MockUtil.certificateList().get(0)));
    }

    @Test
    void checkDeleteByIdShouldThrowException() {
        doThrow(CertificateNotFoundException.class).when(certificateDao).findById(1);

        assertThrows(CertificateNotFoundException.class, () -> certificateService.deleteById(1));
    }

    @Test
    void checkFindByFilter() {
        doReturn(MockUtil.certificateList()).when(certificateDao).findAll();

        assertEquals(1, certificateService.findByFilter("demo", "description", 1.0, 40.0, "tag_1").size());
    }
}