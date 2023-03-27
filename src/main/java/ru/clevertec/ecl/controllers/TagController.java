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
        return ResponseEntity.ok().body(tagMapper.toDtoList(tagService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> findById(@PathVariable long id) {
        return ResponseEntity.ok().body(tagMapper.toDto(tagService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<TagDto> add(@RequestBody TagDto tagDto) {
        tagService.add(tagMapper.toEntity(tagDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(tagDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody TagDto tagDto) {
        tagService.update(id, tagMapper.toEntity(tagDto));
        return ResponseEntity.ok().body(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        tagService.deleteById(id);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }
}
