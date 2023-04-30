package healthmatching.matchingservice.domain;

import healthmatching.matchingservice.dto.TrainerDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileStore {
    //  파일경로
    @Value("${com.example.upload.path}") // application.properties의 변수
    private String fileDirPath;

    //  파일 저장 로직
    public Attachment storeFile(TrainerDto trainer) throws IOException {
        MultipartFile file = trainer.getProfile();
        if (file.isEmpty()) {
            return null;
        }
        String originalFilename = file.getOriginalFilename();
        String storeFilename = createStoreFilename(originalFilename);
//      createPath한 경로에 파일 저장
        file.transferTo(new File(createPath(storeFilename)));
//      Attachment DB에 저장하자.
//      외래키인 trainer가 들어간다.
        return Attachment.builder()
                .originFilename(originalFilename)
                .storeFilename(storeFilename)
                .build();

    }
    // 파일 경로 구성
    public String createPath(String storeFilename) {
        String viaPath = "images/";
        return fileDirPath+viaPath+storeFilename;
    }
    //  저장할 파일 이름 구성
    private String createStoreFilename(String originalFilename) {
//      파일의 이름이 겹치지 않게 UUID를 통해 설정
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
//        확장자를 추출하여 UUID 뒤에 붙여 출력
        String storeFilename = uuid + ext;

        return storeFilename;
    }
    // 확장자 추출
    private String extractExt(String originalFilename) {
        int idx = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(idx);
        return ext;
    }
}
