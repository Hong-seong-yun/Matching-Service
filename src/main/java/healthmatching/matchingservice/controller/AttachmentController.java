package healthmatching.matchingservice.controller;

import healthmatching.matchingservice.domain.FileStore;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
@RestController
@RequiredArgsConstructor
public class AttachmentController {

    private final FileStore fileStore;

    @GetMapping("/images/{filename}")
    public Resource processImg(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.createPath(filename));
    }
}
