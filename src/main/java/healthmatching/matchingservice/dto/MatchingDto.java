package healthmatching.matchingservice.dto;

import healthmatching.matchingservice.domain.Matching;
import healthmatching.matchingservice.domain.Member;
import healthmatching.matchingservice.domain.Trainer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchingDto {
    private Long matchKey;
    @NotEmpty(message = "날짜와 시간을 입력해주세요")
    private String matchDatetime;
    private Integer matchMoney;
    private Trainer matchTrainer;
    private Member matchCustomer;

    @Builder
    public MatchingDto(Long matchKey, String matchDatetime, Integer matchMoney, Trainer matchTrainer, Member matchCustomer) {
        this.matchKey = matchKey;
        this.matchDatetime = matchDatetime;
        this.matchMoney = matchMoney;
        this.matchTrainer = matchTrainer;
        this.matchCustomer = matchCustomer;
    }

    public Matching toEntity() {
        Matching build = Matching.builder()
                .matchDatetime(matchDatetime)
                .matchMoney(matchMoney)
                .matchTrainer(matchTrainer)
                .matchCustomer(matchCustomer)
                .build();
        return build;
    }
}