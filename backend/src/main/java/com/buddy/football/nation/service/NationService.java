package com.buddy.football.nation.service;

import com.buddy.football.nation.dto.NationDTO;
import com.buddy.football.nation.dto.NationMapper;
import com.buddy.football.nation.entity.Nation;
import com.buddy.football.nation.repository.NationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NationService {

    private final NationRepository nationRepository;
    private final NationMapper nationMapper;

    public NationService(NationRepository nationRepository, NationMapper nationMapper) {
        this.nationRepository = nationRepository;
        this.nationMapper = nationMapper;
    }

    public List<Nation> getAllNations() {
        return nationRepository.findAll();
    }

    public Optional<Nation> getNationById(UUID id) {
        return nationRepository.findById(id);
    }

    public Optional<NationDTO> getNationByCode(String code) {
        return nationRepository.findByCode(code)
                .map(nationMapper::fromEntity);
    }
}
