package ru.clevertec.ecl.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.entities.Certificate;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CertificateDaoImpl implements CertificateDao {

    private static final String FIND_ALL = "SELECT * FROM certificates";
    private static final String FIND_BY_ID = "select * from certificates where id = ?";
    private static final String INSERT_INTO = "INSERT INTO certificates (name, description, price,duration, createdate, lastupdatedate) VALUES (?, ?, ?, ?, now(), now())";
    private static final String UPDATE = "UPDATE certificates SET name = ?, description = ?, price = ?,  lastupdatedate = now() WHERE id = ?";
    private static final String DELETE = "DELETE FROM certificates WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    @Override
    public List<Certificate> findAll(){
        return jdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(Certificate.class));
    }

    @Override
    public Optional<Certificate> findById(long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID, new BeanPropertyRowMapper<>(Certificate.class), id));
    }

    @Override
    public void add(Certificate giftCertificate) {
        jdbcTemplate.update(INSERT_INTO,
                new BeanPropertyRowMapper<>(Certificate.class),
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration()
        );
    }

    @Transactional
    @Override
    public void update(long id, Certificate giftCertificate) {
        jdbcTemplate.update(UPDATE,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration()
        );
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        jdbcTemplate.update(DELETE, id);
    }
}