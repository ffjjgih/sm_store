package fpoly.edu.datn.vibee.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {
    ResponseEntity<?> uploadFile(MultipartFile file);
}
