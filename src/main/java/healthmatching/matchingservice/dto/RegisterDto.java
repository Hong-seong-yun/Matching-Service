package healthmatching.matchingservice.dto;

import healthmatching.matchingservice.domain.Member;
import healthmatching.matchingservice.domain.Trainer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterDto {
    private Long rKey;
    private String rDatetime; // PT 요청이 들어온 날짜
    private Trainer rTrainer; // 해당 일정의 트레이너
    private Member rCustomer; // 요청한 회원

    @Builder
    public RegisterDto(Long rKey, String rDatetime, Trainer rTrainer, Member rCustomer) {
        this.rKey = rKey;
        this.rDatetime = rDatetime;
        this.rTrainer = rTrainer;
        this.rCustomer = rCustomer;
    }
}
