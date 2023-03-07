package fpoly.edu.datn.vibee.controller;

import fpoly.edu.datn.vibee.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/vibee/api/v1/attachment")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        return this.attachmentService.uploadFile(file);
    }
}
