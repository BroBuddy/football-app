package com.buddy.football.nation.controller;

import com.buddy.football.nation.dto.NationDTO;
import com.buddy.football.nation.dto.NationMapper;
import com.buddy.football.nation.entity.Nation;
import com.buddy.football.nation.repository.NationRepository;
import com.buddy.football.nation.service.NationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/nations")
public class NationController {

    private final NationService nationService;
    private final NationRepository nationRepository;
    private final NationMapper nationMapper;

    public NationController(NationService nationService, NationRepository nationRepository, NationMapper nationMapper) {
        this.nationService = nationService;
        this.nationRepository = nationRepository;
        this.nationMapper = nationMapper;
    }

    @GetMapping
    public List<NationDTO> getAllNations() {
        return nationRepository.findAll().stream()
                .map(nationMapper::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Nation> getNationById(@PathVariable UUID id) {
        return nationService.getNationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<NationDTO> getNationByCode(@PathVariable String code) {
        return nationRepository.findByCode(code.toUpperCase())
                .map(nationMapper::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
