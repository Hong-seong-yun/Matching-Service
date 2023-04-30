package healthmatching.matchingservice.controller;

import healthmatching.matchingservice.domain.Matching;
import healthmatching.matchingservice.domain.Member;
import healthmatching.matchingservice.domain.Register;
import healthmatching.matchingservice.domain.Trainer;
import healthmatching.matchingservice.dto.MatchingDto;
import healthmatching.matchingservice.repository.MatchingRepository;
import healthmatching.matchingservice.service.MatchingService;
import healthmatching.matchingservice.service.MemberService;
import healthmatching.matchingservice.service.RegisterService;
import healthmatching.matchingservice.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MatchingController {
    private final TrainerService trainerService;
    private final MatchingService matchingService;
    private final RegisterService registerService;

//  id:트레이너 아이디
    @GetMapping("/matchingadd/{id}")
    public String Matching(@PathVariable("id") Long id ,Model model) {
        Matching matching = MatchingDto.builder().build().toEntity();
        Trainer trainer = trainerService.findTrainer(id);
        model.addAttribute("matchingDto", matching);
//      트레이너 정보도 같이 넘겨주기
        model.addAttribute("trainer", trainer);
//      트레이너아이디로 조회한 List<Register> model로 보내주기
        List<Register> registers = registerService.searchRegister(trainer.getTKey());
        model.addAttribute("registers", registers);
        return "request.html";
    }
    @PostMapping("/matchingadd")
    public String MatchingCreate(@ModelAttribute("matchingDto") MatchingDto matchingDto, Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();
//      외래키 둘다 초기화
        matchingService.MatchingAdd(matchingDto, member);
        return "redirect:/home";
    }
//    받아오는 id는 matching의 id
    @GetMapping("/matchingaccept/{id}")
    public String Accept(@PathVariable("id") Long id, Authentication authentication) throws IOException {
        Matching one = matchingService.findOne(id);
        Member member = (Member) authentication.getPrincipal();
        Long memberId = member.getMKey();
        //      등록테이블 생성
        registerService.RegisterAdd(one);
        //      매칭테이블에서 삭제후
        matchingService.deleteOne(one);
//      이런식으로도 가능하다
        return "redirect:/request/"+memberId;
    }
    @GetMapping("/matchingdecline/{id}")
    public String Decline(@PathVariable("id") Long id, Authentication authentication) {
        Matching one = matchingService.findOne(id);
        Member member = (Member) authentication.getPrincipal();
        Long memberId = member.getMKey();
//      요청테이블에서 그냥 삭제해주면 된다
        matchingService.deleteOne(one);
        return "redirect:/request/"+memberId;
    }
}