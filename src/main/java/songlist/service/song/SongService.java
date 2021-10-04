package songlist.service.song;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import songlist.exceptions.ValidationException;
import songlist.mappers.SongMapper;
import songlist.model.song.Song;
import songlist.model.song.dto.NewSongDTO;
import songlist.model.song.dto.SongDTO;
import songlist.model.song.dto.SongSearchCriteria;
import songlist.filters.SongWithDance;
import songlist.filters.SongWithMode;
import songlist.filters.SongWithRhythm;
import songlist.repository.song.SongRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SongService {

    SongRepository songRepository;
    SongBuilderService songBuilderService;

    public SongService(SongRepository songRepository, SongBuilderService songBuilderService) {
        this.songRepository = songRepository;
        this.songBuilderService = songBuilderService;
    }

    public List<SongDTO> getAll() {
        return songRepository.findAll().stream().map(SongMapper::toSongDTO).collect(Collectors.toList());
    }

    public List<SongDTO> getSongsOfCriteria(SongSearchCriteria criteria) {
        Specification<Song> activeSongs = Specification
                .where(new SongWithRhythm(criteria.getRhythmId()))
                .and(new SongWithDance(criteria.getDanceId()))
                .and(new SongWithMode(criteria.getModeId()));

        return songRepository.findAll(activeSongs).stream().map(SongMapper::toSongDTO).collect(Collectors.toList());
    }

    public SongDTO getDTO(String id) {
        Song song = songRepository.getOne(UUID.fromString(id));

        return SongMapper.toSongDTO(song);
    }

    public String create(NewSongDTO newSongDTO) throws ValidationException {
        Song song = songBuilderService.validateAndBuild(newSongDTO);

        return songRepository.save(song).getId().toString();
    }
}

