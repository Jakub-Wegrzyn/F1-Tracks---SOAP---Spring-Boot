package f1_tracks.repo;

import f1_tracks.model.GrandPrix;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GrandPrixRepository extends CrudRepository<GrandPrix, Integer> {
    List<GrandPrix> findAll();
    List<GrandPrix> findByClockwise(boolean clockwise);

}
