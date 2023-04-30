package healthmatching.matchingservice.dto;

import healthmatching.matchingservice.domain.Attachment;
import healthmatching.matchingservice.domain.Member;
import healthmatching.matchingservice.domain.Trainer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrainerDto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tKey;
    private Integer tYear;
    private String tGender;
    private String tCareer;
    private MultipartFile profile;
    private Member member;
//    사실 attachment와 profile이 의미하는건 똑같다.
    @Builder
    public TrainerDto(Integer tYear, String tCareer, String tGender, MultipartFile profile, Member member) {
        this.tYear = tYear;
        this.tCareer = tCareer;
        this.tGender = tGender;
        this.profile = profile;
        this.member = member;
    }

    public Trainer toEntity() {
        Trainer build = Trainer.builder()
                .tYear(tYear)
                .tCareer(tCareer)
                .tGender(tGender)
                .member(member)
                .attachment(null)
                .build();
        return build;
    }
}
