package fpoly.edu.datn.vibee.service.implement;

import fpoly.edu.datn.vibee.entity.AttachmentFile;
import fpoly.edu.datn.vibee.model.response.UploadFileResponse;
import fpoly.edu.datn.vibee.repository.AttachmentFileRepository;
import fpoly.edu.datn.vibee.service.AttachmentService;
import fpoly.edu.datn.vibee.utilities.CommonUtil;
import fpoly.edu.datn.vibee.utilities.Constant;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Service
@Log4j2
public class AttachmentServiceImpl implements AttachmentService {
    @Autowired
    private AttachmentFileRepository attachmentFileRepository;
    @Override
    public ResponseEntity<?> uploadFile(MultipartFile file) {
        log.info("AttachmentService-uploadFile-Start::Data:{}", file);
        UploadFileResponse uploadFileResponse = new UploadFileResponse();
        AttachmentFile attachmentFile = new AttachmentFile();
        boolean saveFile= CommonUtil.uploadFile(file);
        if (!saveFile) {
            log.error("AttachmentService-uploadFile-Error::Data:{}", saveFile);
            uploadFileResponse.setMessage("Upload file failed");
            return new ResponseEntity<>(uploadFileResponse, null, 400);
        }
        attachmentFile.setName(file.getOriginalFilename());
        attachmentFile.setType(file.getContentType());
        attachmentFile.setUrl(Constant.UPLOAD_DIR + file.getOriginalFilename());
        attachmentFile.setSize(file.getSize());
        attachmentFile.setCreatedDate(new Date());
        attachmentFile.setCreatedBy("admin");
        attachmentFile.setStatus("hoạt động");
        this.attachmentFileRepository.save(attachmentFile);
        uploadFileResponse.setMessage("Upload file success");
        uploadFileResponse.setFileType(attachmentFile.getType());
        uploadFileResponse.setFileName(attachmentFile.getName());
        uploadFileResponse.setUrl(attachmentFile.getUrl());
        uploadFileResponse.setSize(attachmentFile.getSize());
        uploadFileResponse.setId(attachmentFile.getId());
        uploadFileResponse.setData(CommonUtil.getEncodeFile(uploadFileResponse.getUrl()));
        log.info("AttachmentService-uploadFile-End::Data:{}", uploadFileResponse);
        return new ResponseEntity<>(uploadFileResponse, null, 200);
    }
}
