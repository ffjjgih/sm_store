package fpoly.edu.datn.vibee.service.implement;

import fpoly.edu.datn.vibee.entity.*;
import fpoly.edu.datn.vibee.model.fc.response.FCShippersResponse;
import fpoly.edu.datn.vibee.model.request.TransactionRequest;
import fpoly.edu.datn.vibee.model.response.BillResponse;
import fpoly.edu.datn.vibee.model.response.DetailBillResponse;
import fpoly.edu.datn.vibee.model.response.TransactionResponse;
import fpoly.edu.datn.vibee.model.response.UploadFileResponse;
import fpoly.edu.datn.vibee.model.result.BillResult;
import fpoly.edu.datn.vibee.model.result.DetailBillResult;
import fpoly.edu.datn.vibee.model.result.DetailOrderResult;
import fpoly.edu.datn.vibee.model.result.TransactionResult;
import fpoly.edu.datn.vibee.repository.*;
import fpoly.edu.datn.vibee.service.BillService;
import fpoly.edu.datn.vibee.service.mockapi.MockApiService;
import fpoly.edu.datn.vibee.utilities.CommonUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class BillServiceImplement implements BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductVersionRepository productVersionRepository;

    @Autowired
    private WarehouseInfoRepository warehouseInfoRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private DetailBillRepository detailBillRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MockApiService mockApiService;

    @Autowired
    private ShipperRepository shipperRepository;

    @Autowired
    private DetailOrderRepository detailOrderRepository;

    @Autowired
    private TransportCompanyRepository transportCompanyRepository;

    @Autowired
    private AttachmentFileRepository attachmentFileRepository;

    /**
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<TransactionResponse> transaction(TransactionRequest request) {
        log.info("Transaction::start - request: {}", request);
        TransactionResponse response = new TransactionResponse();
        String username= "admin";
        if(!checkProductVersion(request)){
            response.setStatus(500);
            response.setMessage("Sản phẩm đã hết hàng");
            return ResponseEntity.noContent().build().ok(response);
        }
        if (request.getTypeSell().equals("offline")) {
            response.setId(createBill(request, username));
            this.createDetails(request, response.getId(),username);
            if (request.getTransactionMethod().equals("online")){
                Order order=this.createOrder(request,username,response.getId());
                this.createDetailOrder(order.getStatus(),order.getId());
            }
        }
        response.setStatus(200);
        response.setMessage("Thành công");
        log.info("Transaction::end - response: {}", response);
        return ResponseEntity.ok(response);
    }

    /**
     * @param search : mã đơn hàng
     * @param status : trạng thái đơn hàng
     * @return
     */
    @Override
    public ResponseEntity<BillResponse> getBills(String search, String status, int page, int size) {
        log.info("getBills::start - search: {}, status: {}", search, status);
        BillResponse response = new BillResponse();
        List<BillResult> billResults=new ArrayList<>();
        Pageable pageable=Pageable.ofSize(size).withPage(page);
        int billId=Integer.parseInt(search);
        List<Bill> bills=new ArrayList<>();
        if (billId==0){
            bills = billRepository.findAllByStatus(status,pageable);
        }else {
            bills = billRepository.findAllByStatusAndId(status,billId,pageable);
        }
        for (Bill bill : bills) {
            BillResult billResult=new BillResult();
            billResult.setBillId(bill.getId());
            Order order=orderRepository.findByBillId(bill.getId());
            if (order!=null){
                Shipper shipper=shipperRepository.getById(order.getShipperId());
                if (shipper!=null){
                    billResult.setShipperName(shipper.getName());
                    billResult.setShipperPhone(shipper.getPhone());
                }
                billResult.setOrderId(order.getId());
                billResult.setFullName(order.getReceivingName());
                billResult.setNumberPhone(order.getReceivingPhone());
                billResult.setAddress(order.getReceivingAddress());
            }else{
                billResult.setFullName("Khách hàng offline");
                billResult.setNumberPhone("Khách hàng offline");
                billResult.setAddress("Khách hàng offline");
            }
            billResult.setTransferMethod(bill.getTransactionMethod());
            billResult.setReceivingMethod(bill.getReceivingMethod());
            billResult.setCreatedDate(CommonUtil.convertDateToString(bill.getCreatedDate()));
            billResult.setCompletedDate(CommonUtil.convertDateToString(bill.getModifiedDate()));
            billResult.setPrice(bill.getPrice());
            billResults.add(billResult);
        }
        response.setData(billResults);
        response.setStatus(200);
        response.setBillStatus(status);
        response.setMessage("Thành công");
        return ResponseEntity.ok(response);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<DetailBillResponse> getBillDetail(int id) {
        log.info("getBillDetail::start - id: {}", id);
        DetailBillResponse response = new DetailBillResponse();
        List<DetailBillResult> detailBillResults=new ArrayList<>();
        List<DetailBill> detailBills=detailBillRepository.findAllByBillId(id);
        Bill bill=billRepository.getById(id);
        BigDecimal price=new BigDecimal(0);
        List<DetailOrderResult> detailOrderResults=new ArrayList<>();
        for (DetailBill detailBill : detailBills) {
            DetailBillResult detailBillResult=new DetailBillResult();
            detailBillResult.setQuantity(detailBill.getQuantity());
            detailBillResult.setPrice(detailBill.getPrice());
            detailBillResult.setSumPrice(detailBill.getPrice().multiply(new BigDecimal(detailBill.getQuantity())));
            Product product=productRepository.findProductByWarehouseId(detailBill.getWarehouseId());
            if (product!=null){
                detailBillResult.setName(product.getName());
                if (product.getImagePrimary() != 0) {
                    AttachmentFile fileAttachment = this.attachmentFileRepository.getById(product.getImagePrimary());
                    UploadFileResponse uploadFileResponse = new UploadFileResponse();
                    uploadFileResponse.setFileName(fileAttachment.getName());
                    uploadFileResponse.setFileDownloadUri(fileAttachment.getUrl());
                    uploadFileResponse.setFileType(fileAttachment.getType());
                    uploadFileResponse.setData(CommonUtil.getEncodeFile(fileAttachment.getUrl()));
                    uploadFileResponse.setSize(fileAttachment.getSize());
                    detailBillResult.setImage(uploadFileResponse);
                }
            }
            price=price.add(detailBillResult.getSumPrice());
            detailBillResults.add(detailBillResult);
        }

        Order order=orderRepository.findByBillId(id);
        if (order!=null){
            List<DetailOrder> detailOrders=detailOrderRepository.findAllByOrderId(order.getId());
            for (DetailOrder detailOrder : detailOrders) {
                DetailOrderResult detailBillResult=new DetailOrderResult();
                detailBillResult.setDetailId(detailOrder.getId());
                detailBillResult.setDate(CommonUtil.convertDateToString(detailOrder.getCreatedDate()));
                detailBillResult.setStatus(detailOrder.getStatus());
                detailOrderResults.add(detailBillResult);
            }
            TransportCompany transportCompany=transportCompanyRepository.getById(order.getTransportCompanyId());
            if (transportCompany!=null){
                response.setReceiveCompany(transportCompany.getName());
            }
            Shipper shipper=shipperRepository.getById(order.getShipperId());
            if (shipper!=null){
                response.setShipperName(shipper.getName());
                response.setShipperPhone(shipper.getPhone());
            }
            response.setDetailOrders(detailOrderResults);
            response.setOrderId(order.getId());
            response.setFullName(order.getReceivingName());
            response.setNumberPhone(order.getReceivingPhone());
            response.setAddress(order.getReceivingAddress());
            response.setReceiveAddress(order.getReceivingAddress());
        }else{
            response.setFullName("Khách hàng offline");
            response.setNumberPhone("Khách hàng offline");
            response.setAddress("Khách hàng offline");
            response.setReceiveAddress("Khách hàng offline");
        }
        response.setBillId(id);
        response.setCreatedDate(CommonUtil.convertDateToString(bill.getCreatedDate()));
        response.setCompletedDate(CommonUtil.convertDateToString(bill.getModifiedDate()));
        response.setPrice(price);
        response.setDetailBills(detailBillResults);
        response.setReceivingMethod(bill.getReceivingMethod());
        response.setTransferMethod(bill.getTransactionMethod());
        response.setSendAddress("FPT Polytechnic Hanoi, Phố Trịnh Văn Bô, Phương Canh, Nam Từ Liêm, Hà Nội");
        return ResponseEntity.ok(response);
    }

    private int createBill(TransactionRequest request, String createBy){
        Bill bill = new Bill();
        bill.setTransactionMethod(request.getTransferMethod());
        bill.setReceivingMethod(request.getTransactionMethod());
        bill.setPrice(request.getMoney());
        bill.setCreatedBy(createBy);
        bill.setModifiedBy(createBy);
        bill.setCreatedDate(new Date());
        bill.setModifiedDate(new Date());
        bill.setStatus(request.getTransactionStatus());
        if (request.getTransferMethod().equals("payment") && Boolean.FALSE.equals(request.getTransfer247())) {
            bill.setDescription(request.getDescription());
            bill.setFileId(request.getImg());
        }
        return billRepository.save(bill).getId();
    }

    private boolean checkProductVersion(TransactionRequest request){
        for (TransactionResult productVersion : request.getSellProducts()) {
            if (Boolean.FALSE.equals(warehouseInfoRepository.checkQuantity(productVersion.getProductVersionId(), productVersion.getQuantity()))){
                return false;
            }
        }
        return true;
    }

    private void createDetails(TransactionRequest request, int billId, String username){
        for (TransactionResult result : request.getSellProducts()) {
            DetailBill detailBill = new DetailBill();
            ProductVersion version = productVersionRepository.getById(result.getProductVersionId());
            Warehouse warehouse = warehouseRepository.getById(result.getWarehouseId());
            warehouse.setOutAmount(warehouse.getOutAmount() + result.getQuantity());
            warehouse.setModifiedBy(username);
            warehouse.setModifiedDate(new Date());
            warehouseRepository.save(warehouse);
            WarehouseInfo warehouseInfo = warehouseInfoRepository.getWarehouseInfoByProductVersionId(result.getProductVersionId());
            warehouseInfo.setSumOutPrice(warehouseInfo.getSumOutPrice().add(warehouse.getOutPrice().add(BigDecimal.valueOf(result.getQuantity()))));
            warehouseInfo.setModifiedBy(username);
            warehouseInfo.setModifiedDate(new Date());
            warehouseInfo.setSumOutAmount(warehouseInfo.getSumOutAmount() + result.getQuantity());
            warehouseInfo.setQuantity(warehouseInfo.getQuantity()-result.getQuantity());
            warehouseInfoRepository.save(warehouseInfo);
            detailBill.setBillId(billId);
            detailBill.setPrice(warehouse.getOutPrice());
            detailBill.setQuantity(result.getQuantity());
            detailBill.setCreatedBy(username);
            detailBill.setModifiedBy(username);
            detailBill.setCreatedDate(new Date());
            detailBill.setModifiedDate(new Date());
            detailBill.setWarehouseId(result.getWarehouseId());
            detailBillRepository.save(detailBill);
        }
    }
    private Order createOrder(TransactionRequest request, String createBy, int billId){
        Date time= new Date();
        Order order = new Order();
        order.setCreatedBy(createBy);
        order.setCreatedDate(time);
        order.setModifiedDate(time);
        order.setModifiedBy(createBy);
        order.setBillId(billId);
        order.setTransportCompanyId(request.getTransportCompanyId());
        order.setPrice(request.getMoney());
        order.setReceivingAddress(request.getReceivingAddress());
        order.setReceivingName(request.getReceivingName());
        order.setReceivingPhone(request.getReceivingPhone());
        order.setStatus("đang giao hàng");
        order.setShipperId(this.getShipper(request.getTransportCompanyId(),createBy));
        return this.orderRepository.save(order);
    }

    private void createDetailOrder(String status, int orderId){
        Date time= new Date();
        DetailOrder order = new DetailOrder();
        order.setCreatedDate(time);
        order.setOrderId(orderId);
        order.setStatus(status);
        this.detailOrderRepository.save(order);
    }

    private int getShipper(int transportCompanyId, String username){
        FCShippersResponse response= mockApiService.getShipper(transportCompanyId);
        if (response!=null){
            Shipper shipper=this.shipperRepository.findByShipperCode(response.getShipperCode());
            if (shipper!=null){
                shipper.setNumberOfDelivery(shipper.getNumberOfDelivery()+1);
                shipper.setModifiedBy(username);
                shipper.setModifiedDate(new Date());
                return shipperRepository.save(shipper).getId();
            }else{
                shipper=new Shipper();
                shipper.setName(response.getFullName());
                shipper.setEmail(response.getEmail());
                shipper.setPhone(response.getNumberPhone());
                shipper.setShipperCode(response.getShipperCode());
                shipper.setNumberOfDelivery(1);
                shipper.setTransportCompanyId(transportCompanyId);
                return shipperRepository.save(shipper).getId();
            }
        }
        return 0;
    }
}
