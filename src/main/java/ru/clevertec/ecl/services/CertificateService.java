package ru.clevertec.ecl.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dao.CertificateDao;
import ru.clevertec.ecl.dto.CertificateDto;
import ru.clevertec.ecl.entities.Certificate;
import ru.clevertec.ecl.entities.Tag;
import ru.clevertec.ecl.exceptions.CertificateNotFoundException;
import ru.clevertec.ecl.mapping.CertificateMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateService {

    private final CertificateDao certificateDao;
    private final CertificateMapper certificateMapper;

    public List<Certificate> findAll(Pageable pageable) {
        return certificateDao.findAll(pageable).getContent();
    }

    public Certificate findById(long id) {
        return certificateDao.findById(id).orElseThrow(() -> new CertificateNotFoundException(id));
    }

    public void add(Certificate certificate) {
        certificateDao.save(certificate);
    }

    public void update(long id, CertificateDto certificateDto) {
        Certificate certificate = findById(id);
        certificateDto.setLastUpdateDate(LocalDateTime.now().toString());
        certificateMapper.updateCertificateByDto(certificateDto, certificate);
        certificateDao.save(certificate);
    }

    public void deleteById(long id) {
        findById(id);
        certificateDao.deleteById(id);
    }

    public List<Certificate> findByFilter(String name, String description, double minPrice, double maxPrice, Pageable pageable) {
        return certificateDao.findAllByNameContainsOrDescriptionContainsOrPriceIsGreaterThanAndPriceIsLessThan(name, description, minPrice, maxPrice, pageable).getContent();
    }

    public List<Tag> findTags(long id, Pageable pageable) {
        List<Tag> tags = findById(id).getTags();
        return new PageImpl<>(tags, pageable, tags.size()).getContent();
    }

    public List<Certificate> findByTags(Pageable pageable, String[] tag) {
        return certificateDao.certificatesBySomeTags(tag, pageable).getContent();
    }
}
