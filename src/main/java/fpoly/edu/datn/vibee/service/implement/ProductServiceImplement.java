package fpoly.edu.datn.vibee.service.implement;

import fpoly.edu.datn.vibee.entity.*;
import fpoly.edu.datn.vibee.model.info.Filter;
import fpoly.edu.datn.vibee.model.request.ProductPropertyRequest;
import fpoly.edu.datn.vibee.model.request.ProductRequest;
import fpoly.edu.datn.vibee.model.request.ProductVersionRequest;
import fpoly.edu.datn.vibee.model.response.ProductsResponse;
import fpoly.edu.datn.vibee.model.response.SellOfflineResponse;
import fpoly.edu.datn.vibee.model.response.UploadFileResponse;
import fpoly.edu.datn.vibee.model.result.ProductResult;
import fpoly.edu.datn.vibee.model.result.SellOfflineProductVersionResult;
import fpoly.edu.datn.vibee.model.result.SellOfflineResult;
import fpoly.edu.datn.vibee.repository.*;
import fpoly.edu.datn.vibee.service.ProductService;
import fpoly.edu.datn.vibee.utilities.CommonUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class ProductServiceImplement implements ProductService {
    /**
     * @param productRequest
     * @return
     */

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductPropertiesRepository productPropertyRepository;

    @Autowired
    private ProductVersionRepository productVersionRepository;

    @Autowired
    private WarehouseInfoRepository warehouseInfoRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private SeriesRepository productSeriesRepository;

    @Autowired
    private AttachmentFileRepository attachmentFileRepository;

    @Autowired
    private BrandRepository brandRepository;


    @Override
    public ResponseEntity<ProductsResponse> createProduct(ProductRequest productRequest) {
        log.info("Create product with data: {}", CommonUtil.beanToString(productRequest));
        String username="admin";
        String rams="";
        String roms="";
        int checkRam=0;
        int checkRom=0;
        ProductsResponse productsResponse = new ProductsResponse();
        //thêm mới cấu hình sản phẩm
        ProductPropertyRequest productPropertyRequest = productRequest.getDetail();
        ProductProperty productProperty = new ProductProperty();
        productProperty.setOperatingSystem(productPropertyRequest.getOs());
        productProperty.setScreenSize(productPropertyRequest.getScreenSize());
        productProperty.setScreenResolution(productPropertyRequest.getScreenResolution());
        productProperty.setScreenTechnology(productPropertyRequest.getScreenTechnology());
        productProperty.setSweepFrequency(productPropertyRequest.getSweepFrequency());
        productProperty.setCameraFront(productPropertyRequest.getCameraFront());
        productProperty.setCameraRear(productPropertyRequest.getCameraRear());
        productProperty.setPinCapacity(productPropertyRequest.getPinCapacity());
        productProperty.setChargingSpeed(productPropertyRequest.getChargingSpeed());
        productProperty.setChargingType(productPropertyRequest.getChargingType());
        productProperty.setSim(productPropertyRequest.getSim());
        productProperty.setJack(productPropertyRequest.isJack());
        productProperty.setWeight(productPropertyRequest.getWeight());
        productProperty.setFrameMaterial(productPropertyRequest.getFrameMaterial());
        productProperty.setBackMaterial(productPropertyRequest.getBackMaterial());
        productProperty.setCpu(productPropertyRequest.getCpu());
        ProductProperty finalProductProperty = productPropertyRepository.save(productProperty);


        //thêm mới sản phẩm
        Product product = new Product();
        product.setCreatedBy(username);
        product.setUpdatedBy(username);
        product.setCreatedDate(new Date());
        product.setUpdatedDate(new Date());
        product.setName(productRequest.getName());
        product.setImagePrimary(productRequest.getImg());
        product.setStatus(productRequest.getStatus());
        product.setDescription(productRequest.getDescription());
        product.setProductImages(CommonUtil.beanToString(productRequest.getImages()));
        product.setProductSeriesId(productRequest.getSeriesId());
        product.setProductPropertyId(finalProductProperty.getId());
        product.setProductIntroId(productRequest.getMedia());
        Product finalProduct = productRepository.save(product);

        for (ProductVersionRequest versionRequest : productRequest.getVersions()) {
            //thêm mới phiên bản sản phẩm
            ProductVersion productVersion = new ProductVersion();
            productVersion.setColor(versionRequest.getColor());
            productVersion.setCpu(finalProductProperty.getCpu());
            productVersion.setRam(versionRequest.getRam());
            productVersion.setRom(versionRequest.getRom());
            productVersion.setScreenSize(finalProductProperty.getScreenSize());
            productVersion.setPin(finalProductProperty.getPinCapacity());
            productVersion.setImage(versionRequest.getImage());
            productVersion.setProductId(finalProduct.getId());
            if (rams!=null && rams.length()>0){
                for (String ram:rams.split(", ")){
                    if (ram.equals(versionRequest.getRam())){
                        checkRam=1;
                        break;
                    }
                }
                if (checkRam==0){
                    rams=rams+versionRequest.getRam()+", ";
                }
            }
            if (roms!=null && roms.length()>0){
                for (String rom:roms.split(", ")){
                    if (rom.equals(versionRequest.getRom())){
                        checkRom=1;
                        break;
                    }
                }
                if (checkRom==0){
                    roms=roms+versionRequest.getRom()+", ";
                }
            }
            ProductVersion finalProductVersion=productVersionRepository.save(productVersion);

            //tạo kho hàng
            WarehouseInfo warehouseInfo = new WarehouseInfo();
            warehouseInfo.setProductVersionId(finalProductVersion.getId());
            warehouseInfo.setNumberOfIn(1);
            warehouseInfo.setQuantity(versionRequest.getQuantity());
            warehouseInfo.setCreatedBy(username);
            warehouseInfo.setModifiedBy(username);
            warehouseInfo.setCreatedDate(new Date());
            warehouseInfo.setModifiedDate(new Date());
            warehouseInfo.setSumInAmount(versionRequest.getQuantity());
            warehouseInfo.setSumOutPrice(BigDecimal.ZERO);
            warehouseInfo.setSumInPrice(versionRequest.getInPrice().multiply(BigDecimal.valueOf(versionRequest.getQuantity())));
            warehouseInfo.setSumOutAmount(0);

            WarehouseInfo finalWarehouseInfo= warehouseInfoRepository.save(warehouseInfo);

            //nhập kho
            Warehouse warehouse = new Warehouse();
            warehouse.setWarehouseInfoId(finalWarehouseInfo.getId());
            warehouse.setInAmount(versionRequest.getQuantity());
            warehouse.setInPrice(versionRequest.getInPrice());
            warehouse.setCreatedDate(new Date());
            warehouse.setCreatedBy(username);
            warehouse.setModifiedDate(new Date());
            warehouse.setModifiedBy(username);
            warehouse.setOutPrice(versionRequest.getOutPrice());
            warehouse.setOutAmount(0);
            warehouse.setStatus("hoạt động");
            warehouse.setEntryOfNumber(1);
            if (productRequest.getSupplierId()==0){
                warehouse.setIsSupplier(false);
                warehouse.setSupplierId(productRequest.getBrandId());
            }else {
                warehouse.setIsSupplier(true);
                warehouse.setSupplierId(productRequest.getSupplierId());
            }
            warehouseRepository.save(warehouse);
        }
        finalProductProperty.setRam(rams);
        finalProductProperty.setRom(roms);
        productPropertyRepository.save(finalProductProperty);
        ProductResult productResult = new ProductResult();
        productResult.setId(finalProduct.getId());
        productResult.setName(finalProduct.getName());
        productResult.setDescription(finalProduct.getDescription());
        productResult.setStatus(finalProduct.getStatus());
        productsResponse.setProducts(new ArrayList<>(List.of(productResult)));
        productsResponse.setStatus(200);
        productsResponse.setMessage("Thêm mới sản phẩm thành công");
        log.info("Create product successfully");
        return ResponseEntity.ok(productsResponse);
    }

    /**
     * @param filter
     * @return
     */
    @Override
    public ResponseEntity<ProductsResponse> getAllProduct(Filter filter) {
        log.info("Get all product with data: {}", CommonUtil.beanToString(filter));
        ProductsResponse productsResponse = new ProductsResponse();
        List<ProductResult> productResults = new ArrayList<>();
        Sort sort = Sort.by(Sort.Direction.fromString(filter.getSort()), filter.getOrder());
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), sort);
        Page<Product> products = productRepository.findAll(pageable);
        if (products.isEmpty()){
            productsResponse.setStatus(404);
            productsResponse.setMessage("Không tìm thấy sản phẩm nào");
            log.info("Not found product");
            return ResponseEntity.noContent().build();
        }
        for (Product product : products) {
            ProductResult productResult = new ProductResult();
            productResult.setId(product.getId());
            productResult.setName(product.getName());
            productResult.setDescription(product.getDescription());
            productResult.setStatus(product.getStatus());
            productResults.add(productResult);
        }
        productsResponse.setProducts(productResults);
        productsResponse.setStatus(200);
        productsResponse.setMessage("Lấy danh sách sản phẩm thành công");
        log.info("Get all product successfully");
        return ResponseEntity.ok(productsResponse);
    }

    /**
     * @param filter
     * @return
     */
    @Override
    public ResponseEntity<ProductsResponse> getProductsSell(Filter filter) {
        log.info("Get all product sell with data: {}", CommonUtil.beanToString(filter));
        ProductsResponse productsResponse = new ProductsResponse();
        List<ProductResult> productResults = new ArrayList<>();
        Sort sort = Sort.by(Sort.Direction.fromString(filter.getSort()), filter.getOrder());
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), sort);
        Page<Product> products = productRepository.findAllByStatus(pageable,"hoạt động");
        if (products.isEmpty()){
            productsResponse.setStatus(404);
            productsResponse.setMessage("Không tìm thấy sản phẩm nào");
            log.info("Not found product");
            return ResponseEntity.noContent().build();
        }
        for (Product product : products) {
            ProductResult productResult = new ProductResult();
            productResult.setId(product.getId());
            productResult.setName(product.getName());
            productResult.setDescription(product.getDescription());
            productResult.setStatus(product.getStatus());
            productResults.add(productResult);
        }
        productsResponse.setProducts(productResults);
        productsResponse.setStatus(200);
        productsResponse.setMessage("Lấy danh sách sản phẩm thành công");
        log.info("Get all product successfully");
        return ResponseEntity.ok(productsResponse);
    }

    /**
     * @param search
     * @return
     */
    @Override
    public ResponseEntity<SellOfflineResponse> getProductsSellOffline(String search) {
        log.info("Get all product sell offline with data: {}", search);
        SellOfflineResponse sellOfflineResponse = new SellOfflineResponse();
        List<SellOfflineResult> productResults = new ArrayList<>();
        List<Product> products = productRepository.findAllByStatusAndNameContaining("hoạt động",search);
        if (products.isEmpty()){
            sellOfflineResponse.setStatus(404);
            sellOfflineResponse.setMessage("Không tìm thấy sản phẩm nào");
            log.info("Not found product");
            return ResponseEntity.noContent().build();
        }
        for (Product product : products) {
            SellOfflineResult productResult = new SellOfflineResult();
            ProductSeries productSeries = productSeriesRepository.findById(product.getProductSeriesId()).orElse(null);
            productResult.setProductId(product.getId());
            productResult.setBrandId(productSeries.getBrandId());
            Brand brand = brandRepository.findById(productSeries.getBrandId()).orElse(null);
            productResult.setBrandName(brand.getName());
            productResult.setProductName(product.getName());
            if (product.getImagePrimary() != 0) {
                AttachmentFile fileAttachment = this.attachmentFileRepository.getById(product.getImagePrimary());
                UploadFileResponse uploadFileResponse = new UploadFileResponse();
                uploadFileResponse.setFileName(fileAttachment.getName());
                uploadFileResponse.setFileDownloadUri(fileAttachment.getUrl());
                uploadFileResponse.setFileType(fileAttachment.getType());
                uploadFileResponse.setData(CommonUtil.getEncodeFile(fileAttachment.getUrl()));
                uploadFileResponse.setSize(fileAttachment.getSize());
                productResult.setImage(uploadFileResponse);
            }
            productResult.setSeriesId(product.getProductSeriesId());
            List<ProductVersion> productVersions = productVersionRepository.getProductVersionByProductId(product.getId());
            List<SellOfflineProductVersionResult> sellOfflineProductVersionResults = new ArrayList<>();
            for (ProductVersion productVersion : productVersions) {
                SellOfflineProductVersionResult sellOfflineProductVersionResult = new SellOfflineProductVersionResult();
                sellOfflineProductVersionResult.setProductVersionId(productVersion.getId());
                sellOfflineProductVersionResult.setColor(productVersion.getColor());
                sellOfflineProductVersionResult.setRam(productVersion.getRam());
                sellOfflineProductVersionResult.setRom(productVersion.getRom());
                WarehouseInfo warehouseInfo = warehouseInfoRepository.getWarehouseInfoByProductVersionId(productVersion.getId());
                Warehouse warehouse = warehouseRepository.getWarehouseByWarehouseInfoIdAndStatus(warehouseInfo.getId(), "hoạt động");
                sellOfflineProductVersionResult.setPrice(warehouse.getOutPrice());
                sellOfflineProductVersionResult.setSupplierId(warehouse.getSupplierId());
                sellOfflineProductVersionResult.setWarehouseId(warehouse.getId());
                sellOfflineProductVersionResult.setWarehouseInfoId(warehouseInfo.getId());
                sellOfflineProductVersionResult.setQuantity(warehouseInfo.getQuantity());
                sellOfflineProductVersionResults.add(sellOfflineProductVersionResult);
            }
            productResult.setVersionResults(sellOfflineProductVersionResults);
            productResults.add(productResult);
        }
        sellOfflineResponse.setData(productResults);
        sellOfflineResponse.setStatus(200);
        sellOfflineResponse.setMessage("Lấy danh sách sản phẩm thành công");
        log.info("Get all product successfully");
        return ResponseEntity.ok(sellOfflineResponse);
    }
}
