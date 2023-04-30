package healthmatching.matchingservice.controller;

import healthmatching.matchingservice.domain.Attachment;
import healthmatching.matchingservice.domain.Member;
import healthmatching.matchingservice.domain.Trainer;
import healthmatching.matchingservice.dto.MemberDto;
import healthmatching.matchingservice.exception.BadRequestException;
import healthmatching.matchingservice.service.MemberService;
import healthmatching.matchingservice.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final TrainerService trainerService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        List<Trainer> trainers = trainerService.TrainerSearch();
        if (trainers.size() >= 6) {
            trainers = trainers.subList(0, 6);
        }
        model.addAttribute("trainers", trainers);
        if (authentication != null) {
            Member member = (Member) authentication.getPrincipal();  //userDetail 객체를 가져옴
//          트레이너와 프로필이 있는지 둘 다 체크를 해줘야한다.
            if (member.getTrainer() != null && member.getTrainer().getAttachment() != null) {
                Attachment attachment = member.getTrainer().getAttachment();
                model.addAttribute("profile", attachment);
            }
            model.addAttribute("memberId", member.getMKey());
            Member one = memberService.findOne(member.getMKey());
            model.addAttribute("member", one);
        }
        return "home.html";
    }

    @GetMapping("/login")
    public String login(){
        return "login.html";
    }

    @GetMapping
    public String root() {
        return "redirect:/home";
    }

//  customer회원가입
    @GetMapping("/signup")
    public String signupView(Model model) {
//      이 초기화한 파일을 반드시 넘겨주어야 한다.
        model.addAttribute("memberDto", MemberDto.builder().build().toEntity());
        return "signup.html";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberDto memberDto, Errors errors, Model model) {
        if (errors.hasErrors()) {
            // 회원가입 실패 시 입력 데이터 값 유지
            model.addAttribute("memberDto", memberDto);

            // 유효성 검사를 통과하지 못 한 필드와 메시지 핸들링
            Map<String, String> validatorResult = memberService.validateHandling(errors);
            for(String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            return "signup.html";
        }

        memberService.JoinMember(memberDto);
        return "redirect:/login";
    }
    // 회원정보 수정(수정해야함)
    @GetMapping("/signup/{id}")
    public String memberUpdate(@PathVariable("id") Long id, Model model) {
        Member member = memberService.findOne(id);
        model.addAttribute("memberDto", member);
        return "updatesign.html";
    }
    @PostMapping("/updatesign")
    public String signUpdate(@Valid MemberDto dto) {
        memberService.updateSign(dto);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getMId(), dto.getMPw()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/home";
    }

//  탈퇴
    @GetMapping("/withdraw")
    public String deleteMember(Authentication authentication) {
//      기존에 로그인되어있던 회원을 테이블에서 delete후에 로그아웃함수진행
        Member member = (Member) authentication.getPrincipal();
        memberService.deleteUser(member);
//      이 부분을 꼭 따로 추가해 줘야 스프링 시큐리티 탈퇴 시 로그아웃 처리가 됨
        SecurityContextHolder.clearContext();
        return "redirect:/home";
    }
    @GetMapping("/user-ids/{mId}/exists")
    public ResponseEntity<String> checkIdDuplication(@RequestParam(value = "mId") String id) throws BadRequestException {
        if (memberService.existsById(id)) {
            throw new BadRequestException("이미 사용중인 아이디 입니다.");
        } else {
            return ResponseEntity.ok("사용 가능한 아이디 입니다.");
        }
    }
}
