package ru.clevertec.ecl.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CertificateDto {

    private String name;
    private String description;
    private double price;
    private Duration duration;
    private String createDate;
    private String lastUpdateDate;
}
