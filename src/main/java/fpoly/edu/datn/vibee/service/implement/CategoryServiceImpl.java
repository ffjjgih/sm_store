package fpoly.edu.datn.vibee.service.implement;

import fpoly.edu.datn.vibee.entity.AttachmentFile;
import fpoly.edu.datn.vibee.entity.Category;
import fpoly.edu.datn.vibee.model.info.Filter;
import fpoly.edu.datn.vibee.model.request.CategoryRequest;
import fpoly.edu.datn.vibee.model.response.CategoriesResponse;
import fpoly.edu.datn.vibee.model.response.UploadFileResponse;
import fpoly.edu.datn.vibee.model.result.CategoryResult;
import fpoly.edu.datn.vibee.repository.AttachmentFileRepository;
import fpoly.edu.datn.vibee.repository.CategoryRepository;
import fpoly.edu.datn.vibee.service.CategoryService;
import fpoly.edu.datn.vibee.utilities.CommonUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AttachmentFileRepository attachmentFileRepository;
    @Override
    public ResponseEntity<CategoriesResponse> getAllCategory(Filter filter) {
        log.info("Get all category-CategoryService::Begin::Data: {}", CommonUtil.beanToString(filter));
        CategoriesResponse response = new CategoriesResponse();
        Sort sort = Sort.by(Sort.Direction.fromString(filter.getSort()), filter.getOrder());
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), sort);
        Page<Category> categories = this.categoryRepository.findByName(pageable, filter.getSearch());
        if (categories.isEmpty()) {
            response.setMessage("Get all category fail");
            response.setStatus(500);
            return ResponseEntity.internalServerError().body(response);
        }
        List<CategoryResult> categoryResults = new ArrayList<>();
        for (Category category : categories) {
            CategoryResult categoryResult = new CategoryResult();
            categoryResult.setId(category.getId());
            categoryResult.setName(category.getName());
            categoryResult.setCreatedDate(CommonUtil.convertDateToString(category.getCreatedDate()));
            categoryResult.setModifiedDate(CommonUtil.convertDateToString(category.getModifiedDate()));
            categoryResult.setCreatedBy(category.getCreatedBy());
            categoryResult.setModifiedBy(category.getModifiedBy());
            if (category.getFileId() > 0){
                AttachmentFile fileAttachment = this.attachmentFileRepository.getById(category.getFileId());
                UploadFileResponse uploadFileResponse = new UploadFileResponse();
                uploadFileResponse.setFileName(fileAttachment.getName());
                uploadFileResponse.setFileDownloadUri(fileAttachment.getUrl());
                uploadFileResponse.setFileType(fileAttachment.getType());
                uploadFileResponse.setData(CommonUtil.getEncodeFile(fileAttachment.getUrl()));
                uploadFileResponse.setSize(fileAttachment.getSize());
                categoryResult.setFileId(category.getFileId());
                categoryResult.setAttachment(uploadFileResponse);
            }
            categoryResult.setDescription(category.getDescription());
            categoryResult.setStatus(category.getStatus());
            categoryResults.add(categoryResult);
        }
        response.setData(categoryResults);
        response.setFilter(filter);
        response.setMessage("Get all category success");
        response.setStatus(200);
        log.info("Get all category-CategoryService::End::Data: {}", CommonUtil.beanToString(response));
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<CategoriesResponse> createCategory(CategoryRequest request) {
        log.info("Create category-CategoryService::Begin::Data: {}", request);
        String username = "admin";
        CategoriesResponse response = new CategoriesResponse();
        Category category = new Category();
        category.setName(request.getName());
        category.setCreatedBy(username);
        category.setModifiedBy(username);
        category.setCreatedDate(new Date());
        category.setModifiedDate(new Date());
        category.setDescription(request.getDescription());
        category.setStatus(request.getStatus());
        category.setFileId(request.getAttachmentId());
        Category category1 = this.categoryRepository.save(category);
        if (category1 == null) {
            response.setMessage("Create category fail");
            response.setStatus(500);
            return ResponseEntity.internalServerError().body(response);
        }
        CategoryResult categoryResult = new CategoryResult();
        categoryResult.setId(category1.getId());
        categoryResult.setName(category1.getName());
        categoryResult.setCreatedDate(CommonUtil.convertDateToString(category1.getCreatedDate()));
        categoryResult.setModifiedDate(CommonUtil.convertDateToString(category1.getModifiedDate()));
        categoryResult.setCreatedBy(category1.getCreatedBy());
        categoryResult.setModifiedBy(category1.getModifiedBy());
        if (category1.getFileId() != 0){
            categoryResult.setFileId(category1.getFileId());
            categoryResult.setImage(this.attachmentFileRepository.findUrlById(category1.getFileId()));
        }
        List<CategoryResult> categoryResults = new ArrayList<>();
        categoryResults.add(categoryResult);
        response.setData(categoryResults);
        response.setMessage("Create category success");
        response.setStatus(200);
        log.info("Create category-CategoryService::End::Data: {}", response);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CategoriesResponse> updateCategory(CategoryRequest request) {
        log.info("Update category-CategoryService::Begin::Data: {}", request);
        String username = "admin";
        CategoriesResponse response = new CategoriesResponse();
        Category category = this.categoryRepository.findById(request.getId()).orElse(null);
        if (category == null) {
            response.setMessage("Danh mục không tồn tại");
            response.setStatus(500);
            return ResponseEntity.internalServerError().body(response);
        }
        category.setName(request.getName());
        category.setModifiedBy(username);
        category.setModifiedDate(new Date());
        category.setFileId(request.getAttachmentId());
        category.setDescription(request.getDescription());
        category.setStatus(request.getStatus());
        Category category1 = this.categoryRepository.save(category);
        if (category1 == null) {
            response.setMessage("Câp nhật danh mục thất bại");
            response.setStatus(500);
            return ResponseEntity.internalServerError().body(response);
        }
        CategoryResult categoryResult = new CategoryResult();
        categoryResult.setId(category1.getId());
        categoryResult.setName(category1.getName());
        categoryResult.setCreatedDate(CommonUtil.convertDateToString(category1.getCreatedDate()));
        categoryResult.setModifiedDate(CommonUtil.convertDateToString(category1.getModifiedDate()));
        categoryResult.setCreatedBy(category1.getCreatedBy());
        categoryResult.setModifiedBy(category1.getModifiedBy());
        if (category1.getFileId() > 0){
            AttachmentFile fileAttachment = this.attachmentFileRepository.getById(category1.getFileId());
            UploadFileResponse uploadFileResponse = new UploadFileResponse();
            uploadFileResponse.setFileName(fileAttachment.getName());
            uploadFileResponse.setFileDownloadUri(fileAttachment.getUrl());
            uploadFileResponse.setFileType(fileAttachment.getType());
            uploadFileResponse.setData(CommonUtil.getEncodeFile(fileAttachment.getUrl()));
            uploadFileResponse.setSize(fileAttachment.getSize());
            categoryResult.setFileId(category1.getFileId());
            categoryResult.setAttachment(uploadFileResponse);
        }
        List<CategoryResult> categoryResults = new ArrayList<>();
        categoryResults.add(categoryResult);
        response.setData(categoryResults);
        response.setMessage("Cập nhật danh mục thành công");
        response.setStatus(200);
        log.info("Update category-CategoryService::End::Data: {}", response);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CategoriesResponse> deleteCategory(int id) {
        log.info("Delete category-CategoryService::Begin::Data: {}", id);
        CategoriesResponse response = new CategoriesResponse();
        Category category = this.categoryRepository.findById(id).orElse(null);
        if (category == null) {
            response.setMessage("Danh mục không tồn tại");
            response.setStatus(500);
            return ResponseEntity.internalServerError().body(response);
        }
        this.categoryRepository.updateStatus("không hoạt động", id);
        response.setMessage("Xóa danh mục thành công");
        response.setStatus(200);
        log.info("Delete category-CategoryService::End::Data: {}", response);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CategoriesResponse> getCategoryById(int id) {
        log.info("Get category by id-CategoryService::Begin::Data: {}", id);
        CategoriesResponse response = new CategoriesResponse();
        Category category = this.categoryRepository.findById(id).orElse(null);
        if (category == null) {
            response.setMessage("Danh mục không tồn tại");
            response.setStatus(500);
            return ResponseEntity.internalServerError().body(response);
        }
        CategoryResult categoryResult = new CategoryResult();
        categoryResult.setId(category.getId());
        categoryResult.setName(category.getName());
        categoryResult.setCreatedDate(CommonUtil.convertDateToString(category.getCreatedDate()));
        categoryResult.setModifiedDate(CommonUtil.convertDateToString(category.getModifiedDate()));
        categoryResult.setCreatedBy(category.getCreatedBy());
        categoryResult.setModifiedBy(category.getModifiedBy());
        if (category.getFileId() > 0){
            AttachmentFile fileAttachment = this.attachmentFileRepository.getById(category.getFileId());
            UploadFileResponse uploadFileResponse = new UploadFileResponse();
            uploadFileResponse.setFileName(fileAttachment.getName());
            uploadFileResponse.setFileDownloadUri(fileAttachment.getUrl());
            uploadFileResponse.setFileType(fileAttachment.getType());
            uploadFileResponse.setData(CommonUtil.getEncodeFile(fileAttachment.getUrl()));
            uploadFileResponse.setSize(fileAttachment.getSize());
            categoryResult.setFileId(category.getFileId());
            categoryResult.setAttachment(uploadFileResponse);
        }
        categoryResult.setStatus(category.getStatus());
        categoryResult.setDescription(category.getDescription());
        List<CategoryResult> categoryResults = new ArrayList<>();
        categoryResults.add(categoryResult);
        response.setData(categoryResults);
        response.setMessage("Lấy danh mục thành công");
        response.setStatus(200);
        log.info("Get category by id-CategoryService::End::Data: {}", response);
        return ResponseEntity.ok(response);
    }
}
