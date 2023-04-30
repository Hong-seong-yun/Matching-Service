package healthmatching.matchingservice.repository;

import healthmatching.matchingservice.domain.Register;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegisterRepository extends JpaRepository<Register, Long>  {
//  외래키 트레이너id를 통해 다수의 Register를 조회하자.
//  명명규칙이 findBy첫번째글자는 대문자인데 왜 조회가 안되지?(아무튼 소문자로 바꾸니 됐다)
    List<Register> findByrTrainer_tKey(Long id);
}
