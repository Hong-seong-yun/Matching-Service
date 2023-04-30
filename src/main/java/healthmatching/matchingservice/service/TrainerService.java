package healthmatching.matchingservice.service;

import healthmatching.matchingservice.domain.Attachment;
import healthmatching.matchingservice.domain.FileStore;
import healthmatching.matchingservice.domain.Trainer;
import healthmatching.matchingservice.dto.TrainerDto;
import healthmatching.matchingservice.repository.AttachmentRepository;
import healthmatching.matchingservice.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerService {
    private final AttachmentService attachmentService;
    private final TrainerRepository trainerRepository;
    private final AttachmentRepository attachmentRepository;
    @Transactional
    public void TrainerAdd(TrainerDto dto) throws IOException {
        // attachment에는 파일이 들어가야한다.(오리지날 파일명, 저장될파일명 등)
        Attachment attachment = attachmentService.saveAttachment(dto);
//      toEntity를 통해 초기화시켜주기
        Trainer trainer = dto.toEntity();
//      toEntity에서 null을 넣어줬기 때문에 set을 통해 초기화시켜주자.
//      되겠지? 수동으로 만들어줌.
        trainer.setAttachment(attachment);
        attachment.setTrainer(trainer);
        trainerRepository.save(trainer);
        attachmentRepository.save(attachment);
    }

    public List<Trainer> TrainerSearch() {
        return trainerRepository.findTop3Trainers();
    }
    public Trainer findTrainer(Long id) {
        Trainer trainer = trainerRepository.findById(id).get();
        return trainer;
    }
    public List<Trainer> allTrainer() {
        return trainerRepository.findAll();
    }
}
