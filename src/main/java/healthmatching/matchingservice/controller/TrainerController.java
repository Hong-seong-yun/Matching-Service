package healthmatching.matchingservice.controller;

import healthmatching.matchingservice.domain.Matching;
import healthmatching.matchingservice.domain.Member;
import healthmatching.matchingservice.domain.Trainer;
import healthmatching.matchingservice.domain.TrainerForm;
import healthmatching.matchingservice.dto.TrainerDto;
import healthmatching.matchingservice.service.MatchingService;
import healthmatching.matchingservice.service.MemberService;
import healthmatching.matchingservice.service.TrainerService;
import lombok.RequiredArgsConstructor;
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
public class TrainerController {
    private final MemberService memberService;
    private final TrainerService trainerService;
    private final MatchingService matchingService;

    @GetMapping("/traineradd")
    public String add(){
        return "traineradd.html";
    }

    @PostMapping("/api/traineradd")
    public String addTrainer (@ModelAttribute TrainerForm trainer, Authentication authentication) throws IOException {
        Member member = (Member) authentication.getPrincipal();
//      트레이너여야만 등록할수있다.
        if (member.getMRole().equals("TRAINER")) {
            TrainerDto dto = trainer.createTrainerDto(member);
            trainerService.TrainerAdd(dto);
        }
        return "redirect:/home";
    }
//  트레이너 id를 받아와서 조회하자.
    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Long id, Model model, Authentication authentication){
        Trainer trainer = trainerService.findTrainer(id);
        if (authentication != null) {
            Member member = (Member) authentication.getPrincipal();
            model.addAttribute("role", member.getMRole());
        }
        model.addAttribute("trainer", trainer);
        return "details.html";
    }
    @GetMapping("/trainerlist")
    public String trainerList(Model model) {
        List<Trainer> trainers = trainerService.allTrainer();
        model.addAttribute("trainers", trainers);
        return "list.html";
    }
    @GetMapping("/request/{id}")
    public String trainerRequest(@PathVariable("id") Long id, Model model) {
        Member member = memberService.findOne(id);
        if(member.getMRole().equals("TRAINER")) {
            if (member.getTrainer().getTKey() != null) {
                Long trainerId = member.getTrainer().getTKey();
                List<Matching> matchings = matchingService.searchMatch(trainerId);
                model.addAttribute("matchings",matchings);
            }
        }
        return "requestconfirm.html";
    }
}
