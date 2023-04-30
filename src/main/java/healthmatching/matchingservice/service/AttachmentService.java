package healthmatching.matchingservice.service;

import healthmatching.matchingservice.domain.Attachment;
import healthmatching.matchingservice.domain.FileStore;
import healthmatching.matchingservice.domain.Trainer;
import healthmatching.matchingservice.dto.TrainerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AttachmentService {
    private final FileStore fileStore;

    public Attachment saveAttachment(TrainerDto trainer) throws IOException {
        return fileStore.storeFile(trainer);
    }
}
