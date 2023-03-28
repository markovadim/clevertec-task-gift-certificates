package ru.clevertec.ecl.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.ecl.dto.CertificateDto;
import ru.clevertec.ecl.entities.Certificate;

import java.time.Duration;
import java.util.List;

@Mapper
public interface CertificateMapper {

    @Mapping(target = "duration", expression = "java(toLong(certificate.getDuration()))")
    CertificateDto toDto(Certificate certificate);

    @Mapping(target = "duration", expression = "java(toDuration(certificateDto.getDuration()))")
    Certificate toEntity(CertificateDto certificateDto);

    List<CertificateDto> toDtoList(List<Certificate> entityList);

    default Duration toDuration(long days) {
        return Duration.ofDays(days);
    }

    default long toLong(Duration duration) {
        return duration.toDays();
    }
}
