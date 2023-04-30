package healthmatching.matchingservice.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Trainer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tKey;
    @Column(nullable = false)
    private String tGender;
    @Column(nullable = false)
    private Integer tYear;
    @Column(nullable = false)
    private String tCareer;
//  외래키(회원이름,나이)가 있는쪽이 연관관계 주인(mappedby를 사용X)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", unique = true)
    private Member member;
    @OneToOne(mappedBy = "trainer", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Attachment attachment;
    @OneToMany(mappedBy = "matchTrainer", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Matching> matching;

    @OneToMany(mappedBy = "rTrainer", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Register> registers;

    @Builder
    public Trainer(Integer tYear, String tCareer, String tGender, Member member, Attachment attachment) {
        this.tYear = tYear;
        this.tGender = tGender;
        this.tCareer = tCareer;
        this.member = member;
        this.attachment = attachment;
    }
    public void setAttachment(Attachment attachment){
        this.attachment = attachment;
    }
}
