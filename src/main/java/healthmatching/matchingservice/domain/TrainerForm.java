package healthmatching.matchingservice.domain;

import healthmatching.matchingservice.dto.TrainerDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class TrainerForm {
    private Integer tYear;
    private String tGender;
    private String tCareer;
    private MultipartFile profile;

    @Builder
    public TrainerForm(Integer tYear, String tGender, String tCareer, MultipartFile profile) {
        this.tYear = tYear;
        this.tGender = tGender;
        this.tCareer = tCareer;
        this.profile = profile;
    }

    public TrainerDto createTrainerDto(Member member) {
        return TrainerDto.builder()
                .tYear(tYear)
                .tCareer(tCareer)
                .tGender(tGender)
                .member(member)
                .profile(profile)
                .build();
    }
}
