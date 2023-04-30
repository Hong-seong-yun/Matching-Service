package healthmatching.matchingservice.controller;

import healthmatching.matchingservice.domain.Member;
import healthmatching.matchingservice.domain.Register;
import healthmatching.matchingservice.domain.Trainer;
import healthmatching.matchingservice.service.MemberService;
import healthmatching.matchingservice.service.RegisterService;
import healthmatching.matchingservice.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RegisterController {
    private final MemberService memberService;
    private final RegisterService registerService;
//  로그인된 id값이 넘어온다(member)
//  뷰에서 이미 트레이너만이 클릭할 수 있기때문에 id로 member를 조회한후에 member.getTrainer()로 조회
    @GetMapping("/trainerregister/{id}")
    public String MyRegister(@PathVariable("id") Long id, Model model){
        Member one = memberService.findOne(id);
        Long tKey = one.getTrainer().getTKey();
        List<Register> registers = registerService.searchRegister(tKey);
        model.addAttribute("registers", registers);
        return "trainerregister.html";
    }
}
