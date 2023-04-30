package healthmatching.matchingservice.dto;

import healthmatching.matchingservice.domain.Member;
import healthmatching.matchingservice.domain.Trainer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDto {
    private Long mKey;
    @NotEmpty(message = "아이디를 입력해주세요")
    private String mId;
    @NotEmpty(message = "이름을 입력해주세요")
    private String mName;
    @NotEmpty(message = "전화번호를 입력해주세요")
    private String mPhonenumber;
    @NotNull(message = "나이를 입력해주세요")
    private Integer mAge;
    @NotEmpty(message = "패스워드를 입력해주세요")
    private String mPw;
    @NotEmpty(message = "역할을 입력해주세요")
    private String mRole;

    public Member toEntity() {
        Member build = Member.builder()
                .mKey(mKey)
                .mId(mId)
                .mName(mName)
                .mPhonenumber(mPhonenumber)
                .mAge(mAge)
                .mPw(mPw)
                .mRole(mRole)
                .build();
        return build;
    }

    @Builder
    public MemberDto(Long mKey, String mId, String mName, String mPhonenumber, Integer mAge, String mPw, String mRole) {
        this.mKey = mKey;
        this.mId = mId;
        this.mName = mName;
        this.mPhonenumber = mPhonenumber;
        this.mAge = mAge;
        this.mPw = mPw;
        this.mRole = mRole;
    }
}
