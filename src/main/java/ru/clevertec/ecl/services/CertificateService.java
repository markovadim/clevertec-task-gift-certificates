package ru.clevertec.ecl.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dao.CertificateDao;
import ru.clevertec.ecl.entities.Certificate;
import ru.clevertec.ecl.entities.Tag;
import ru.clevertec.ecl.exceptions.CertificateNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateService {

    private final CertificateDao certificateDao;
    private final TagService tagService;

    public List<Certificate> findAll() {
        return certificateDao.findAll();
    }

    public Certificate findById(long id) {
        return certificateDao.findById(id).orElseThrow(() -> new CertificateNotFoundException(id));
    }

    public void add(Certificate certificate) {
        certificateDao.add(certificate);
        certificate.getTags().stream().filter(t -> !tagService.findAll().contains(t)).forEach(tagService::add);
    }

    public void update(long id, Certificate certificate) {
        findById(id);
        certificateDao.update(id, certificate);
    }

    public void deleteById(long id) {
        findById(id);
        certificateDao.deleteById(id);
    }

    public List<Certificate> findByFilter(String name, String description, double minPrice, double maxPrice) {
        return certificateDao.findByFilter(name, description, minPrice, maxPrice);
    }

    public List<Tag> findTags(long id) {
        return findById(id).getTags();
    }
}
