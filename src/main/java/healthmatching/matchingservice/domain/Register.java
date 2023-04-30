package healthmatching.matchingservice.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Register {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rKey;
    @Column(nullable = false)
    private String rDatetime; // PT 요청이 들어온 날짜
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "r_trainer")
    private Trainer rTrainer; // 해당 일정의 트레이너
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "r_customer")
    private Member rCustomer; // 요청한 회원

    @Builder
    public Register(Long rKey, String rDatetime, Trainer rTrainer, Member rCustomer) {
        this.rKey = rKey;
        this.rDatetime = rDatetime;
        this.rTrainer = rTrainer;
        this.rCustomer = rCustomer;
    }
}
