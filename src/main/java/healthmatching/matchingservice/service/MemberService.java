package healthmatching.matchingservice.service;

import healthmatching.matchingservice.domain.Member;
import healthmatching.matchingservice.dto.MemberDto;
import healthmatching.matchingservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    public void JoinMember(MemberDto memberDto) {
        String role = (memberDto.getMRole().equals("회원")) ? "CUSTOMER" : "TRAINER";

        Member member = Member.builder()
                .mKey(memberDto.getMKey())
                .mId(memberDto.getMId())
                .mName(memberDto.getMName())
                .mPhonenumber(memberDto.getMPhonenumber())
                .mAge(memberDto.getMAge())
                .mPw(encoder.encode(memberDto.getMPw()))
                .mRole(role)
                .build();

        memberRepository.save(member);
    }

    @Transactional
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for(FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }
    public void deleteUser(Member member) {
        memberRepository.delete(member);
    }
    public Member findOne(Long id) {
        Member member = memberRepository.findById(id).get();
        return member;
    }
    @Transactional
    public void updateSign(MemberDto dto) {
        Member one = findOne(dto.getMKey());
        one.updateUser(dto.getMId(),dto.getMName(),dto.getMPhonenumber(),dto.getMAge(),encoder.encode(dto.getMPw()), dto.getMRole());
//        memberRepository.save(one);
    }
    @Transactional
    public boolean existsById(String mId){
        return memberRepository.existsBymId(mId);
    }
}
