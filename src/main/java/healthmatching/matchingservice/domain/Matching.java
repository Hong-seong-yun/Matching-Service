package healthmatching.matchingservice.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Matching {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchKey;
    @Column(nullable = false)
    private String matchDatetime; // PT 요청이 들어온 날짜
    @Column(nullable = false)
    private Integer matchMoney; // 결제 금액
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_trainer")
    private Trainer matchTrainer; // 해당 일정의 트레이너
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_customer")
    private Member matchCustomer; // 요청한 회원

    @Builder
    public Matching(Long matchKey, String matchDatetime, Integer matchMoney, Trainer matchTrainer, Member matchCustomer) {
        this.matchKey = matchKey;
        this.matchDatetime = matchDatetime;
        this.matchMoney = matchMoney;
        this.matchTrainer = matchTrainer;
        this.matchCustomer = matchCustomer;
    }
}
