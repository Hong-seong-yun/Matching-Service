package healthmatching.matchingservice.repository;

import healthmatching.matchingservice.domain.Matching;
import healthmatching.matchingservice.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsBymId(String mId);
    Optional<Member> findBymId(String uid);
}
