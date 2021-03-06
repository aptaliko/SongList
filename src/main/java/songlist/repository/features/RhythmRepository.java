package songlist.repository.features;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import songlist.model.features.rhythm.Rhythm;

import java.util.UUID;

@Repository
public interface RhythmRepository extends JpaRepository<Rhythm, UUID> {
}
