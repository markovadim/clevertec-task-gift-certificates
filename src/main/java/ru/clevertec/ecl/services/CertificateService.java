package ru.clevertec.ecl.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dao.CertificateDao;
import ru.clevertec.ecl.entities.Certificate;
import ru.clevertec.ecl.exceptions.CertificateNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CertificateService {

    private final CertificateDao certificateDao;
    private final TagService tagService;

    public List<Certificate> findAll() {
        return certificateDao.findAll();
    }

    public Certificate findById(long id) {
        try {
            return certificateDao.findById(id).get();
        } catch (EmptyResultDataAccessException e) {
            throw new CertificateNotFoundException(id);
        }
    }

    public void add(Certificate certificate) {
        certificateDao.add(certificate);
        tagService.findAll()
                .stream()
                .filter(t -> certificate.getDescription().contains(t.toString()))
                .forEach(tagService::add);
    }

    public void update(long id, Certificate certificate) {
        findById(id);
        certificateDao.update(id, certificate);
    }

    public void deleteById(long id) {
        findById(id);
        certificateDao.deleteById(id);
    }

    public List<Certificate> findByFilter(String name, String description, double minPrice, double maxPrice, String tag) {
        return findAll().stream()
                .filter(c -> c.getName().contains(name))
                .filter(c -> c.getDescription().contains(description))
                .filter(c -> c.getPrice() >= minPrice)
                .filter(c -> c.getPrice() <= maxPrice)
                .filter(c -> c.getTags().stream().anyMatch(t -> t.getName().contains(tag)))
                .collect(Collectors.toList());
    }
}
