package songlist.repository.features;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import songlist.model.features.dance.Dance;

import java.util.UUID;

@Repository
public interface DanceRepository extends JpaRepository<Dance, UUID> {
}
