package healthmatching.matchingservice.repository;

import healthmatching.matchingservice.domain.Matching;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchingRepository extends JpaRepository<Matching, Long> {
    //  외래키를 findby하고 싶을때(member의 기본키를 통해 matching을 뽑아낸다)
    List<Matching> findByMatchTrainer_tKey(Long id);
}
