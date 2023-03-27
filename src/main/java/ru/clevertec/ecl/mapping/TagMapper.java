package ru.clevertec.ecl.mapping;

import org.mapstruct.Mapper;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.entities.Tag;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagDto toDto(Tag tag);

    Tag toEntity(TagDto tagDto);

    List<TagDto> toDtoList(List<Tag> entityList);
}
