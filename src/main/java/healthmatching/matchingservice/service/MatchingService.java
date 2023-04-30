package healthmatching.matchingservice.service;

import healthmatching.matchingservice.domain.Matching;
import healthmatching.matchingservice.domain.Member;
import healthmatching.matchingservice.dto.MatchingDto;
import healthmatching.matchingservice.repository.MatchingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchingService {
    private final MatchingRepository matchingRepository;

    public void MatchingAdd(MatchingDto dto, Member member) {
        String s = dto.getMatchDatetime();
//      ,와 enter(공백)을 같이 분리해준다.
        String[] date = s.split(",\\s*");
        for (String d : date) {
            Matching matching = Matching.builder()
                    .matchDatetime(d)
                    .matchMoney(60000)
                    .matchCustomer(member)
                    .matchTrainer(dto.getMatchTrainer())
                    .build();
            matchingRepository.save(matching);
        }
    }
//  match의 외래키인 trainer를 통해 matching을 조회
    public List<Matching> searchMatch(Long id){
        List<Matching> res = matchingRepository.findByMatchTrainer_tKey(id);
        return res;
    }
    public Matching findOne(Long id) {
        Matching matching = matchingRepository.findById(id).get();
        return matching;
    }
    public void deleteOne(Matching matching) {
        matchingRepository.delete(matching);
    }
}
