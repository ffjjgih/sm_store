package fpoly.edu.datn.vibee.service.implement;

import fpoly.edu.datn.vibee.entity.AttachmentFile;
import fpoly.edu.datn.vibee.entity.Brand;
import fpoly.edu.datn.vibee.model.info.Filter;
import fpoly.edu.datn.vibee.model.request.BrandRequest;
import fpoly.edu.datn.vibee.model.response.BrandsResponse;
import fpoly.edu.datn.vibee.model.response.UploadFileResponse;
import fpoly.edu.datn.vibee.model.result.BrandResult;
import fpoly.edu.datn.vibee.repository.AttachmentFileRepository;
import fpoly.edu.datn.vibee.repository.BrandRepository;
import fpoly.edu.datn.vibee.service.BrandService;
import fpoly.edu.datn.vibee.utilities.CommonUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class BrandServiceImplement implements BrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private AttachmentFileRepository attachmentFileRepository;
    @Override
    public BrandsResponse getAllBrand(Filter filter) {
        log.info("Get all brand-BrandService::Begin->Data: {}", filter);
        BrandsResponse brandsResponse = new BrandsResponse();
        brandsResponse.setMessage("Get all brand success");
        brandsResponse.setStatus(200);
        List<BrandResult> brandResults = new ArrayList<>();

        Sort sort = Sort.by(Sort.Direction.fromString(filter.getSort()), filter.getOrder());
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), sort);
        Page<Brand> brands = brandRepository.findAll(pageable);
        if (brands.isEmpty()) {
            brandsResponse.setMessage("Get all brand fail");
            brandsResponse.setStatus(400);
            return brandsResponse;
        }
        for (Brand brand : brands) {
            BrandResult brandResult = new BrandResult();
            if (brand.getFileId() != 0) {
                AttachmentFile fileAttachment = this.attachmentFileRepository.getById(brand.getFileId());
                UploadFileResponse uploadFileResponse = new UploadFileResponse();
                uploadFileResponse.setFileName(fileAttachment.getName());
                uploadFileResponse.setFileDownloadUri(fileAttachment.getUrl());
                uploadFileResponse.setFileType(fileAttachment.getType());
                uploadFileResponse.setData(CommonUtil.getEncodeFile(fileAttachment.getUrl()));
                uploadFileResponse.setSize(fileAttachment.getSize());
                brandResult.setImages(fileAttachment.getUrl());
                brandResult.setAttachment(uploadFileResponse);
            }
            brandResult.setId(brand.getId());
            brandResult.setBirthOfDate(brand.getBirthOfDate());
            brandResult.setName(brand.getName());
            brandResult.setAddress(brand.getAddress());
            brandResult.setEmail(brand.getEmail());
            brandResult.setWebsite(brand.getWebsite());
            brandResult.setDescription(brand.getDescription());
            brandResult.setCreatedDate(CommonUtil.convertDateToString(brand.getCreatedDate()));
            brandResult.setModifiedDate(CommonUtil.convertDateToString(brand.getModifiedDate()));
            brandResult.setModifiedBy(brand.getModifiedBy());
            brandResult.setCreatedBy(brand.getCreatedBy());
            brandResult.setStatus(brand.getStatus());
            brandResults.add(brandResult);
        }
        brandsResponse.setData(brandResults);
        brandsResponse.setFilter(filter);
        log.info("Get all brand-BrandService::End->Data: {}", brandsResponse);
        return brandsResponse;
    }

    @Override
    public BrandsResponse createBrand(BrandRequest request) {
        log.info("Create brand-BrandService::Begin->Data: {}", request);
        BrandsResponse brandsResponse = new BrandsResponse();
        String username="admin";
        Brand brand = new Brand();
        brand.setName(request.getName());
        brand.setAddress(request.getAddress());
        brand.setEmail(request.getEmail());
        brand.setWebsite(request.getWebsite());
        brand.setDescription(request.getDescription());
        brand.setCreatedDate(new Date());
        brand.setModifiedDate(new Date());
        brand.setBirthOfDate(request.getBirthOfDate());
        brand.setCreatedBy(username);
        brand.setModifiedBy(username);
        brand.setStatus(request.getStatus());
        brand.setFileId(request.getFileId());
        brand=brandRepository.save(brand);
        BrandResult brandResult = new BrandResult();
        if (brand.getFileId() != 0) {
            brandResult.setImages(this.attachmentFileRepository.findUrlById(brand.getFileId()));
        }
        brandResult.setId(brand.getId());
        brandResult.setName(brand.getName());
        brandResult.setAddress(brand.getAddress());
        brandResult.setEmail(brand.getEmail());
        brandResult.setWebsite(brand.getWebsite());
        brandResult.setDescription(brand.getDescription());
        brandResult.setCreatedDate(CommonUtil.convertDateToString(brand.getCreatedDate()));
        brandResult.setModifiedDate(CommonUtil.convertDateToString(brand.getModifiedDate()));
        brandResult.setModifiedBy(brand.getModifiedBy());
        brandResult.setCreatedBy(brand.getCreatedBy());
        brandResult.setStatus(brand.getStatus());
        List<BrandResult> brandResults = new ArrayList<>();
        brandResults.add(brandResult);
        brandsResponse.setData(brandResults);
        brandsResponse.setMessage("Create brand success");
        brandsResponse.setStatus(200);
        log.info("Create brand-BrandService::End->Data: {}", brandsResponse);
        return brandsResponse;
    }

    @Override
    public BrandsResponse updateBrand(BrandRequest request) {
        log.info("Update brand-BrandService::Begin->Data: {}", request);
        BrandsResponse brandsResponse = new BrandsResponse();
        String username="admin";
        Brand brand = brandRepository.findById(request.getId()).get();
        brand.setName(request.getName());
        brand.setAddress(request.getAddress());
        brand.setEmail(request.getEmail());
        brand.setWebsite(request.getWebsite());
        brand.setDescription(request.getDescription());
        brand.setModifiedBy(username);
        brand.setModifiedDate(new Date());
        brand.setStatus(request.getStatus());
        brand.setFileId(request.getFileId());
        brand.setBirthOfDate(request.getBirthOfDate());
        brand=brandRepository.save(brand);
        BrandResult brandResult = new BrandResult();
        if (brand.getFileId() != 0) {
            brandResult.setImages(this.attachmentFileRepository.findUrlById(brand.getFileId()));
        }
        brandResult.setId(brand.getId());
        brandResult.setName(brand.getName());
        brandResult.setAddress(brand.getAddress());
        brandResult.setEmail(brand.getEmail());
        brandResult.setWebsite(brand.getWebsite());
        brandResult.setDescription(brand.getDescription());
        brandResult.setCreatedDate(CommonUtil.convertDateToString(brand.getCreatedDate()));
        brandResult.setModifiedDate(CommonUtil.convertDateToString(brand.getModifiedDate()));
        brandResult.setModifiedBy(brand.getModifiedBy());
        brandResult.setCreatedBy(brand.getCreatedBy());
        brandResult.setStatus(brand.getStatus());
        List<BrandResult> brandResults = new ArrayList<>();
        brandResults.add(brandResult);
        brandsResponse.setData(brandResults);
        brandsResponse.setMessage("Update brand success");
        brandsResponse.setStatus(200);
        log.info("Update brand-BrandService::End->Data: {}", brandsResponse);
        return brandsResponse;
    }

    @Override
    public BrandsResponse deleteBrand(int id) {
        log.info("Delete brand-BrandService::Begin->Data: {}", id);
        BrandsResponse brandsResponse = new BrandsResponse();
        brandRepository.deleteById("ngừng hoạt động",id);
        brandsResponse.setMessage("Delete brand success");
        brandsResponse.setStatus(200);
        log.info("Delete brand-BrandService::End->Data: {}", brandsResponse);
        return brandsResponse;
    }
}
