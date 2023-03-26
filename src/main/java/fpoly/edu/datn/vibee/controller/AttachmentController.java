package fpoly.edu.datn.vibee.controller;

import fpoly.edu.datn.vibee.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/vibee/api/v1/attachment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        return this.attachmentService.uploadFile(file);
    }
}
