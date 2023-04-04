package ru.clevertec.ecl.mapping;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Duration;

@Converter
public class DurationConverter implements AttributeConverter<Duration, Long> {

    @Override
    public Long convertToDatabaseColumn(Duration attribute) {
        Long longValue =attribute.toDays();
        return longValue;
    }

    @Override
    public Duration convertToEntityAttribute(Long dbData) {
        Duration duration = Duration.ofDays(dbData);
        return duration;
    }
}
