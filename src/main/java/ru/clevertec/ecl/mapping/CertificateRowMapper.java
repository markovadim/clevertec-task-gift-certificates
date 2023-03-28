package ru.clevertec.ecl.mapping;

import org.springframework.jdbc.core.RowMapper;
import ru.clevertec.ecl.entities.Certificate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;

public class CertificateRowMapper implements RowMapper<Certificate> {

    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        Certificate certificate = new Certificate();
        certificate.setId(rs.getLong("id"));
        certificate.setName(rs.getString("name"));
        certificate.setDescription(rs.getString("description"));
        certificate.setPrice(rs.getDouble("price"));
        certificate.setDuration(Duration.ofDays(rs.getLong("duration")));
        certificate.setCreateDate(LocalDateTime.parse(rs.getString("createdate").replace(" ", "T")));
        certificate.setLastUpdateDate(LocalDateTime.parse(rs.getString("lastupdatedate").replace(" ", "T")));
        return certificate;
    }
}