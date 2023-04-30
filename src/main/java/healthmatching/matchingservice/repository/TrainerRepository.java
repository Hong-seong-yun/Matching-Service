package healthmatching.matchingservice.repository;

import healthmatching.matchingservice.domain.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    @Query("SELECT t FROM Trainer t ORDER BY t.tKey DESC")
    List<Trainer> findTop3Trainers();

}
