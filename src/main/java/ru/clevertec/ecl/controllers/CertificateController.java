package ru.clevertec.ecl.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.dto.CertificateDto;
import ru.clevertec.ecl.mapping.CertificateMapper;
import ru.clevertec.ecl.services.CertificateService;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class CertificateController {

    private final CertificateService certificateService;
    private final CertificateMapper certificateMapper;

    @Autowired
    public CertificateController(CertificateService certificateService, CertificateMapper certificateMapper) {
        this.certificateService = certificateService;
        this.certificateMapper = certificateMapper;
    }

    @GetMapping
    public ResponseEntity<List<CertificateDto>> findAll() {
        return ResponseEntity.ok().body(certificateMapper.toDtoList(certificateService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificateDto> findById(@PathVariable long id) {
        return ResponseEntity.ok().body(certificateMapper.toDto(certificateService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody CertificateDto certificateDto) {
        certificateService.add(certificateMapper.toEntity(certificateDto));
        return ResponseEntity.ok().body(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody CertificateDto certificateDto) {
        certificateService.update(id, certificateMapper.toEntity(certificateDto));
        return ResponseEntity.ok().body(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        certificateService.deleteById(id);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }

//    @GetMapping("/filter")
//    public ResponseEntity<List<CertificateDto>> findByTag(@RequestParam String tag) {
//        return ResponseEntity.ok().body(certificateMapper.toDtoList(certificateService.findByTag(tag)));
//    }

    @GetMapping("/filter")
    public ResponseEntity<List<CertificateDto>> findByFilter(@RequestParam(required = false) String name,
                                                             @RequestParam(required = false) String description,
                                                             @RequestParam(required = false) double minPrice,
                                                             @RequestParam(required = false) double maxPrice,
                                                             @RequestParam (required = false) String tag) {
        return ResponseEntity.ok().body(certificateMapper.toDtoList(certificateService.findByFilter(name, description, minPrice, maxPrice,tag)));
    }
}
