package ru.clevertec.ecl.mapping;

import org.mapstruct.*;
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

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCertificateByDto(CertificateDto dto, @MappingTarget Certificate entity);

    default Duration toDuration(long days) {
        return Duration.ofDays(days);
    }

    default long toLong(Duration duration) {
        return duration.toDays();
    }
}
