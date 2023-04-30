package healthmatching.matchingservice.repository;

import healthmatching.matchingservice.domain.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
