package fpoly.edu.datn.vibee.service.implement;

import fpoly.edu.datn.vibee.entity.AttachmentFile;
import fpoly.edu.datn.vibee.entity.Shipper;
import fpoly.edu.datn.vibee.entity.TransportCompany;
import fpoly.edu.datn.vibee.model.info.Filter;
import fpoly.edu.datn.vibee.model.request.TransportCompanyRequest;
import fpoly.edu.datn.vibee.model.response.DetailTransportCompanyResponse;
import fpoly.edu.datn.vibee.model.response.TransportCompaniesResponse;
import fpoly.edu.datn.vibee.model.response.UploadFileResponse;
import fpoly.edu.datn.vibee.model.result.ShipperResult;
import fpoly.edu.datn.vibee.model.result.TransportCompanyResult;
import fpoly.edu.datn.vibee.repository.AttachmentFileRepository;
import fpoly.edu.datn.vibee.repository.ShipperRepository;
import fpoly.edu.datn.vibee.repository.TransportCompanyRepository;
import fpoly.edu.datn.vibee.service.TransportService;
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
public class TransportServiceImplement implements TransportService {

    @Autowired
    private TransportCompanyRepository transportCompanyRepository;

    @Autowired
    private AttachmentFileRepository attachmentFileRepository;

    @Autowired
    private ShipperRepository shipperRepository;
    /**
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<TransportCompaniesResponse> create(TransportCompanyRequest request) {
        String info= CommonUtil.beanToString(request);
        String username= "admin";
        log.info("create transport company with info: {}",info);
        TransportCompany transportCompany = new TransportCompany();
        transportCompany.setName(request.getName());
        transportCompany.setAddress(request.getAddress());
        transportCompany.setPhone(request.getPhone());
        transportCompany.setEmail(request.getEmail());
        transportCompany.setCreatedDate(new Date());
        transportCompany.setCreatedBy(username);
        transportCompany.setModifiedDate(new Date());
        transportCompany.setModifiedBy(username);
        transportCompany.setDescription(request.getDescription());
        transportCompany.setStatus(request.getStatus());
        transportCompany.setFileId(request.getFileId());
        transportCompany.setWebsite(request.getWebsite());
        transportCompany.setCountOrder(0);
        transportCompanyRepository.save(transportCompany);
        TransportCompaniesResponse response = new TransportCompaniesResponse();
        response.setStatus(200);
        response.setMessage("thêm mới thành công");
        log.info("create transport company success");
        return ResponseEntity.ok(response);
    }

    /**
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<TransportCompaniesResponse> update(TransportCompanyRequest request) {
        String info= CommonUtil.beanToString(request);
        String username= "admin";
        log.info("update transport company with info: {}",info);
        TransportCompany transportCompany = transportCompanyRepository.findById(request.getId()).get();
        if (transportCompany == null) {
            log.error("[VIBEE-TRANSPORT-UPDATE] -- transport company not found");
            return ResponseEntity.noContent().build();
        }
        transportCompany.setName(request.getName());
        transportCompany.setAddress(request.getAddress());
        transportCompany.setPhone(request.getPhone());
        transportCompany.setEmail(request.getEmail());
        transportCompany.setModifiedDate(new Date());
        transportCompany.setModifiedBy(username);
        transportCompany.setDescription(request.getDescription());
        transportCompany.setStatus(request.getStatus());
        transportCompany.setFileId(request.getFileId());
        transportCompany.setWebsite(request.getWebsite());
        transportCompanyRepository.save(transportCompany);
        TransportCompaniesResponse response = new TransportCompaniesResponse();
        response.setStatus(200);
        response.setMessage("cập nhật thành công");
        log.info("update transport company success");
        return ResponseEntity.ok(response);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<TransportCompaniesResponse> delete(int id) {
        log.info("delete transport company with id: {}",id);
        TransportCompany transportCompany = transportCompanyRepository.findById(id).get();
        if (transportCompany == null) {
            log.error("transport company not found");
            return ResponseEntity.noContent().build();
        }
        transportCompanyRepository.updateStatus(transportCompany.getId(),"ngừng hoạt động");
        TransportCompaniesResponse response=new TransportCompaniesResponse();
        response.setStatus(200);
        response.setMessage("xóa thành công");
        log.info("delete transport company success");
        return ResponseEntity.ok(response);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<DetailTransportCompanyResponse> detail(int id) {
        log.info("get detail transport company with id: {}",id);
        TransportCompany transportCompany = transportCompanyRepository.findById(id).get();
        if (transportCompany == null) {
            log.error("transport company not found");
            return ResponseEntity.noContent().build();
        }
        DetailTransportCompanyResponse response = new DetailTransportCompanyResponse();
        response.setId(transportCompany.getId());
        response.setName(transportCompany.getName());
        response.setAddress(transportCompany.getAddress());
        response.setPhone(transportCompany.getPhone());
        response.setEmail(transportCompany.getEmail());
        response.setCreatedDate(CommonUtil.convertDateToString(transportCompany.getCreatedDate()));
        response.setCreatedBy(transportCompany.getCreatedBy());
        response.setUpdatedDate(CommonUtil.convertDateToString(transportCompany.getModifiedDate()));
        response.setUpdatedBy(transportCompany.getModifiedBy());
        response.setDescription(transportCompany.getDescription());
        response.setStatus(transportCompany.getStatus());
        if (transportCompany.getFileId() > 0){
            AttachmentFile fileAttachment = this.attachmentFileRepository.getById(transportCompany.getFileId());
            UploadFileResponse uploadFileResponse = new UploadFileResponse();
            uploadFileResponse.setFileName(fileAttachment.getName());
            uploadFileResponse.setFileDownloadUri(fileAttachment.getUrl());
            uploadFileResponse.setFileType(fileAttachment.getType());
            uploadFileResponse.setData(CommonUtil.getEncodeFile(fileAttachment.getUrl()));
            uploadFileResponse.setSize(fileAttachment.getSize());
            response.setAttachment(uploadFileResponse);
        }
        List<Shipper> shippers= this.shipperRepository.findByTransportCompanyId(response.getId());
        response.setCountShipper(shippers.size());
        List<ShipperResult> shipperResults= new ArrayList<>();
        for (Shipper s:shippers){
            if (s.getStatus().equals("đang hoạt động")){
                response.setCountShipperActive(response.getCountShipperActive()+1);
            }
            ShipperResult shipperResult=new ShipperResult();
            shipperResult.setShipCode(s.getShipperCode());
            shipperResult.setFullname(s.getName());
            shipperResult.setCountOrder(s.getNumberOfDelivery());
            shipperResult.setPhone(s.getPhone());
            shipperResult.setEmail(s.getEmail());
            shipperResult.setAvatar(s.getAvatar());
            shipperResults.add(shipperResult);
        }
        response.setShippers(shipperResults);
        response.setWebsite(transportCompany.getWebsite());
        response.setCountOrder(transportCompany.getCountOrder());
        String info= CommonUtil.beanToString(response);
        log.info("get detail transport company success with info: {}",info);
        return ResponseEntity.ok(response);
    }

    /**
     * @param filter
     * @return
     */
    @Override
    public ResponseEntity<TransportCompaniesResponse> getAll(Filter filter) {
        String info= CommonUtil.beanToString(filter);
        log.info("get detail transport company with id: {}",info);
        Sort sort = Sort.by(Sort.Direction.fromString(filter.getSort()), filter.getOrder());
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), sort);
        Page<TransportCompany> transportCompanies = transportCompanyRepository.findByName(pageable,filter.getSearch());
        if (transportCompanies.isEmpty()) {
            log.error("transport company not found");
            return ResponseEntity.noContent().build();
        }
        TransportCompaniesResponse response = new TransportCompaniesResponse();
        List<TransportCompanyResult> transportCompanyResults= new ArrayList<>();
        for (TransportCompany transportCompany:transportCompanies){
            TransportCompanyResult transportCompanyResult=new TransportCompanyResult();
            transportCompanyResult.setId(transportCompany.getId());
            transportCompanyResult.setName(transportCompany.getName());
            transportCompanyResult.setAddress(transportCompany.getAddress());
            transportCompanyResult.setPhone(transportCompany.getPhone());
            transportCompanyResult.setEmail(transportCompany.getEmail());
            transportCompanyResult.setCreatedDate(CommonUtil.convertDateToString(transportCompany.getCreatedDate()));
            transportCompanyResult.setCreatedBy(transportCompany.getCreatedBy());
            transportCompanyResult.setUpdatedDate(CommonUtil.convertDateToString(transportCompany.getModifiedDate()));
            transportCompanyResult.setUpdatedBy(transportCompany.getModifiedBy());
            transportCompanyResult.setDescription(transportCompany.getDescription());
            transportCompanyResult.setStatus(transportCompany.getStatus());
            if (transportCompany.getFileId() > 0){
                AttachmentFile fileAttachment = this.attachmentFileRepository.getById(transportCompany.getFileId());
                UploadFileResponse uploadFileResponse = new UploadFileResponse();
                uploadFileResponse.setFileName(fileAttachment.getName());
                uploadFileResponse.setFileDownloadUri(fileAttachment.getUrl());
                uploadFileResponse.setFileType(fileAttachment.getType());
                uploadFileResponse.setData(CommonUtil.getEncodeFile(fileAttachment.getUrl()));
                uploadFileResponse.setSize(fileAttachment.getSize());
                transportCompanyResult.setAttachment(uploadFileResponse);
            }
            transportCompanyResult.setWebsite(transportCompany.getWebsite());
            transportCompanyResult.setCountOrder(transportCompany.getCountOrder());
            transportCompanyResults.add(transportCompanyResult);
        }
        response.setData(transportCompanyResults);
        response.setFilter(filter);
        response.setStatus(200);
        response.setMessage("success");
        info= CommonUtil.beanToString(response);
        log.info("get detail transport company success with info: {}",info);
        return ResponseEntity.ok(response);
    }
}
