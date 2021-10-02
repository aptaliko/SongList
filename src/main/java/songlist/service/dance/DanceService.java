package songlist.service.dance;

import org.springframework.stereotype.Service;
import songlist.model.features.dance.Dance;
import songlist.model.features.dance.dto.DanceDTO;
import songlist.model.features.dance.dto.NewDanceDTO;
import songlist.repository.dance.DanceRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DanceService {

    DanceRepository danceRepository;

    public DanceService(DanceRepository danceRepository) {
        this.danceRepository = danceRepository;
    }

    public List<DanceDTO> getAll() {
        return danceRepository.findAll().stream().map(s -> new DanceDTO(s.getId().toString(), s.getName())).collect(Collectors.toList());
    }

    public DanceDTO get(UUID id) {
        Dance dance = danceRepository.getOne(id);

        return new DanceDTO(dance.getId().toString(), dance.getName());
    }

    public Optional<UUID> create(NewDanceDTO newDanceDTO) {

        if (newDanceDTO == null) {
            return Optional.empty();
        }
        Dance dance = new Dance();
        dance.setName(newDanceDTO.getName());

        return Optional.of(danceRepository.save(dance).getId());
    }
}