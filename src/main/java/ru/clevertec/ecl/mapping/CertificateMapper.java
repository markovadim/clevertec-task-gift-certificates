package ru.clevertec.ecl.mapping;

import org.mapstruct.Mapper;
import ru.clevertec.ecl.dto.CertificateDto;
import ru.clevertec.ecl.entities.Certificate;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CertificateMapper {

    CertificateDto toDto(Certificate certificate);

    Certificate toEntity(CertificateDto certificateDto);

    List<CertificateDto> toDtoList(List<Certificate> entityList);
}
