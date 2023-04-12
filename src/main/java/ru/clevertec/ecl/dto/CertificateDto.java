package ru.clevertec.ecl.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class CertificateDto {

    private String name;
    private String description;
    private double price;
    private long duration;
    private String createDate;
    private String lastUpdateDate;
    private List<TagDto>tags;
}
