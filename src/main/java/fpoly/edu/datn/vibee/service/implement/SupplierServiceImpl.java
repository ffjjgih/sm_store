package fpoly.edu.datn.vibee.service.implement;

import fpoly.edu.datn.vibee.entity.AttachmentFile;
import fpoly.edu.datn.vibee.entity.Supplier;
import fpoly.edu.datn.vibee.model.info.Filter;
import fpoly.edu.datn.vibee.model.request.SupplierRequest;
import fpoly.edu.datn.vibee.model.response.SuppliersResponse;
import fpoly.edu.datn.vibee.model.response.UploadFileResponse;
import fpoly.edu.datn.vibee.model.result.SupplierResult;
import fpoly.edu.datn.vibee.repository.AttachmentFileRepository;
import fpoly.edu.datn.vibee.repository.SupplierRepository;
import fpoly.edu.datn.vibee.service.SupplierService;
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
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private AttachmentFileRepository attachmentFileRepository;

    @Override
    public ResponseEntity<SuppliersResponse> getAllSupplier(Filter filter) {
        log.info("Get all supplier-SupplierService::Begin::Data: {}", CommonUtil.beanToString(filter));
        SuppliersResponse response = new SuppliersResponse();
        Sort sort = Sort.by(Sort.Direction.fromString(filter.getSort()), filter.getOrder());
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), sort);
        Page<Supplier> suppliers = this.supplierRepository.findByName(pageable, filter.getSearch());
        if (suppliers.isEmpty()) {
            response.setMessage("Không có dữ liệu");
            response.setStatus(500);
            return ResponseEntity.internalServerError().body(response);
        }
        List<SupplierResult> supplierResults = new ArrayList<>();
        for (Supplier supplier : suppliers) {
            SupplierResult supplierResult = new SupplierResult();
            if (supplier.getFileId() != 0){

                AttachmentFile fileAttachment = this.attachmentFileRepository.getById(supplier.getFileId());
                UploadFileResponse uploadFileResponse = new UploadFileResponse();
                uploadFileResponse.setFileName(fileAttachment.getName());
                uploadFileResponse.setFileDownloadUri(fileAttachment.getUrl());
                uploadFileResponse.setFileType(fileAttachment.getType());
                uploadFileResponse.setData(CommonUtil.getEncodeFile(fileAttachment.getUrl()));
                uploadFileResponse.setSize(fileAttachment.getSize());
                supplierResult.setAttachment(uploadFileResponse);
            }
            supplierResult.setId(supplier.getId());
            supplierResult.setName(supplier.getName());
            supplierResult.setAddress(supplier.getAddress());
            supplierResult.setPhone(supplier.getNumberPhone());
            supplierResult.setEmail(supplier.getEmail());
            supplierResult.setStatus(supplier.getStatus());
            supplierResult.setAddress(supplier.getAddress());
            supplierResult.setDescription(supplier.getDescription());
            supplierResult.setCreatedDate(CommonUtil.convertDateToString(supplier.getCreatedDate()));
            supplierResult.setModifiedDate(CommonUtil.convertDateToString(supplier.getModifiedDate()));
            supplierResult.setCreatedBy(supplier.getCreatedBy());
            supplierResult.setModifiedBy(supplier.getModifiedBy());
            supplierResults.add(supplierResult);
        }
        response.setData(supplierResults);
        response.setFilter(filter);
        response.setMessage("hiển thị danh sách nhà cung cấp thành công");
        response.setStatus(200);
        log.info("Get all supplier-SupplierService::End::Data: {}", CommonUtil.beanToString(response));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SuppliersResponse> createSupplier(SupplierRequest request) {
        log.info("Create supplier-SupplierService::Begin::Data: {}", CommonUtil.beanToString(request));
        String username="admin";
        SuppliersResponse response = new SuppliersResponse();
        Supplier supplier = new Supplier();
        supplier.setName(request.getName());
        supplier.setAddress(request.getAddress());
        supplier.setNumberPhone(request.getPhone());
        supplier.setEmail(request.getEmail());
        supplier.setDescription(request.getDescription());
        supplier.setCreatedBy(username);
        supplier.setModifiedBy(username);
        supplier.setCreatedDate(new Date());
        supplier.setModifiedDate(CommonUtil.getCurrentDate());
        supplier.setFileId(request.getFileId());
        supplier.setStatus(request.getStatus());
        supplier=this.supplierRepository.save(supplier);
        if (supplier == null) {
            response.setMessage("Thêm nhà cung cấp thất bại");
            response.setStatus(500);
            return ResponseEntity.internalServerError().body(response);
        }
        SupplierResult supplierResult = new SupplierResult();
        supplierResult.setId(supplier.getId());
        supplierResult.setName(supplier.getName());
        supplierResult.setAddress(supplier.getAddress());
        supplierResult.setPhone(supplier.getNumberPhone());
        supplierResult.setEmail(supplier.getEmail());
        supplierResult.setAddress(supplier.getAddress());
        supplierResult.setDescription(supplier.getDescription());
        supplierResult.setCreatedDate(CommonUtil.convertDateToString(supplier.getCreatedDate()));
        supplierResult.setModifiedDate(CommonUtil.convertDateToString(supplier.getModifiedDate()));
        supplierResult.setCreatedBy(supplier.getCreatedBy());
        supplierResult.setModifiedBy(supplier.getModifiedBy());
        response.setData(List.of(supplierResult));
        response.setMessage("Thêm nhà cung cấp thành công");
        response.setStatus(200);
        log.info("Create supplier-SupplierService::End::Data: {}", CommonUtil.beanToString(response));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SuppliersResponse> updateSupplier(SupplierRequest request) {
        log.info("Update supplier-SupplierService::Begin::Data: {}", CommonUtil.beanToString(request));
        String username="admin";
        SuppliersResponse response = new SuppliersResponse();
        Supplier supplier = this.supplierRepository.findById(request.getId()).orElse(null);
        if (supplier == null) {
            response.setMessage("Không tìm thấy nhà cung cấp");
            response.setStatus(500);
            return ResponseEntity.internalServerError().body(response);
        }
        supplier.setName(request.getName());
        supplier.setAddress(request.getAddress());
        supplier.setNumberPhone(request.getPhone());
        supplier.setEmail(request.getEmail());
        supplier.setDescription(request.getDescription());
        supplier.setModifiedBy(username);
        supplier.setModifiedDate(CommonUtil.getCurrentDate());
        supplier.setFileId(request.getFileId());
        supplier=this.supplierRepository.save(supplier);
        if (supplier == null) {
            response.setMessage("Cập nhật nhà cung cấp thất bại");
            response.setStatus(500);
            return ResponseEntity.internalServerError().body(response);
        }
        SupplierResult supplierResult = new SupplierResult();
        supplierResult.setId(supplier.getId());
        supplierResult.setName(supplier.getName());
        supplierResult.setAddress(supplier.getAddress());
        supplierResult.setPhone(supplier.getNumberPhone());
        supplierResult.setEmail(supplier.getEmail());
        supplierResult.setAddress(supplier.getAddress());
        supplierResult.setDescription(supplier.getDescription());
        supplierResult.setCreatedDate(CommonUtil.convertDateToString(supplier.getCreatedDate()));
        supplierResult.setModifiedDate(CommonUtil.convertDateToString(supplier.getModifiedDate()));
        supplierResult.setCreatedBy(supplier.getCreatedBy());
        supplierResult.setModifiedBy(supplier.getModifiedBy());
        response.setData(List.of(supplierResult));
        response.setMessage("Cập nhật nhà cung cấp thành công");
        response.setStatus(200);
        log.info("Update supplier-SupplierService::End::Data: {}", CommonUtil.beanToString(response));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SuppliersResponse> deleteSupplier(int id) {
        log.info("Delete supplier-SupplierService::Begin::Data: {}", id);
        SuppliersResponse response = new SuppliersResponse();
        Supplier supplier = this.supplierRepository.findById(id).orElse(null);
        if (supplier == null) {
            response.setMessage("Không tìm thấy nhà cung cấp");
            response.setStatus(500);
            return ResponseEntity.internalServerError().body(response);
        }
        this.supplierRepository.updateStatus("không hoạt động", id);
        response.setMessage("Xóa nhà cung cấp thành công");
        response.setStatus(200);
        log.info("Delete supplier-SupplierService::End::Data: {}", response);
        return ResponseEntity.ok(response);
    }
}
