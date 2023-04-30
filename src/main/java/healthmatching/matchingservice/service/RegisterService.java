package healthmatching.matchingservice.service;

import healthmatching.matchingservice.domain.Matching;
import healthmatching.matchingservice.domain.Register;
import healthmatching.matchingservice.repository.RegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegisterService {
    private final RegisterRepository registerRepository;
    public void RegisterAdd(Matching matching) throws IOException {
        Register register = Register.builder()
                .rDatetime(matching.getMatchDatetime())
                .rTrainer(matching.getMatchTrainer())
                .rCustomer(matching.getMatchCustomer())
                .build();
        registerRepository.save(register);
    }

    public List<Register> searchRegister(Long id){
        List<Register> res = registerRepository.findByrTrainer_tKey(id);
        return res;
    }
}
