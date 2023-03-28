package ru.clevertec.ecl.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.mapping.TagMapper;
import ru.clevertec.ecl.services.TagService;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final TagMapper tagMapper;

    @GetMapping
    public ResponseEntity<List<TagDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(tagMapper.toDtoList(tagService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> findById(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(tagMapper.toDto(tagService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<TagDto> add(@RequestBody TagDto tagDto) {
        tagService.add(tagMapper.toEntity(tagDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(tagDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TagDto> update(@PathVariable long id, @RequestBody TagDto updatedTagDto) {
        tagService.update(id, tagMapper.toEntity(updatedTagDto));
        return ResponseEntity.status(HttpStatus.OK).body(updatedTagDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        tagService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
