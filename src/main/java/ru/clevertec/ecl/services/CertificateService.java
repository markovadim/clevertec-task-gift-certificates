package ru.clevertec.ecl.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dao.CertificateDao;
import ru.clevertec.ecl.entities.Certificate;
import ru.clevertec.ecl.entities.Tag;
import ru.clevertec.ecl.exceptions.CertificateNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateService {

    private final CertificateDao certificateDao;

    public List<Certificate> findAll() {
        return certificateDao.findAll();
    }

    public Certificate findById(long id) {
        return certificateDao.findById(id).orElseThrow(() -> new CertificateNotFoundException(id));
    }

    public void add(Certificate certificate) {
        certificateDao.save(certificate);
    }

    public void update(long id, Certificate updatedCertificate) {
        Certificate certificate = findById(id);
        certificate.setName(updatedCertificate.getName());
        certificate.setDescription(updatedCertificate.getDescription());
        certificate.setPrice(updatedCertificate.getPrice());
        certificate.setDuration(updatedCertificate.getDuration());
        certificate.setLastUpdateDate(LocalDateTime.now());
        certificateDao.save(certificate);
    }

    public void deleteById(long id) {
        findById(id);
        certificateDao.deleteById(id);
    }

    public List<Certificate> findByFilter(String name, String description, double minPrice, double maxPrice) {
        return certificateDao.findAllByNameContainsOrDescriptionContainsOrPriceIsGreaterThanAndPriceIsLessThan(name, description, minPrice, maxPrice);
    }

    public List<Tag> findTags(long id) {
        return findById(id).getTags();
    }
}
