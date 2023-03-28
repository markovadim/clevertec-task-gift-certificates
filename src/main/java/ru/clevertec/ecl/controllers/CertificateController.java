package ru.clevertec.ecl.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.dto.CertificateDto;
import ru.clevertec.ecl.mapping.CertificateMapper;
import ru.clevertec.ecl.services.CertificateService;

import java.util.List;

@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;
    private final CertificateMapper certificateMapper;

    @GetMapping
    public ResponseEntity<List<CertificateDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(certificateMapper.toDtoList(certificateService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificateDto> findById(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(certificateMapper.toDto(certificateService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<CertificateDto> add(@RequestBody CertificateDto certificateDto) {
        certificateService.add(certificateMapper.toEntity(certificateDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(certificateDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CertificateDto> update(@PathVariable long id, @RequestBody CertificateDto updatedCertificateDto) {
        certificateService.update(id, certificateMapper.toEntity(updatedCertificateDto));
        return ResponseEntity.status(HttpStatus.OK).body(updatedCertificateDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        certificateService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<List<CertificateDto>> findByFilter(@RequestParam(required = false) String name,
                                                             @RequestParam(required = false) String description,
                                                             @RequestParam(required = false) double minPrice,
                                                             @RequestParam(required = false) double maxPrice,
                                                             @RequestParam(required = false) String tag) {
        return ResponseEntity.status(HttpStatus.OK).body(certificateMapper.toDtoList(certificateService.findByFilter(name, description, minPrice, maxPrice, tag)));
    }
}
