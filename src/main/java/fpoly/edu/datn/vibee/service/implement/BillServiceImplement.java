package fpoly.edu.datn.vibee.service.implement;

import fpoly.edu.datn.vibee.entity.*;
import fpoly.edu.datn.vibee.model.fc.response.FCShippersResponse;
import fpoly.edu.datn.vibee.model.request.TransactionRequest;
import fpoly.edu.datn.vibee.model.response.TransactionResponse;
import fpoly.edu.datn.vibee.model.result.TransactionResult;
import fpoly.edu.datn.vibee.repository.*;
import fpoly.edu.datn.vibee.service.BillService;
import fpoly.edu.datn.vibee.service.mockapi.MockApiService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

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

    /**
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<TransactionResponse> transaction(TransactionRequest request) {
        log.info("Transaction::start - request: {}", request);
        TransactionResponse response = new TransactionResponse();
        String username= "admin";
        if(checkProductVersion(request)){
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
            ProductVersion version = productVersionRepository.findById(result.getProductVersionId()).get();
            Warehouse warehouse = warehouseRepository.findById(result.getWarehouseId()).get();
            warehouse.setOutAmount(warehouse.getOutAmount() + result.getQuantity());
            warehouse.setModifiedBy(username);
            warehouse.setModifiedDate(new Date());
            warehouseRepository.save(warehouse);
            WarehouseInfo warehouseInfo = warehouseInfoRepository.getWarehouseInfoByProductVersionId(result.getProductVersionId());
            warehouseInfo.setSumOutPrice(warehouseInfo.getSumOutPrice().add(warehouse.getOutPrice().add(BigDecimal.valueOf(result.getQuantity()))));
            warehouseInfo.setModifiedBy(username);
            warehouseInfo.setModifiedDate(new Date());
            warehouseInfo.setSumOutAmount(warehouseInfo.getSumOutAmount() + result.getQuantity());
            warehouseInfoRepository.save(warehouseInfo);
            detailBill.setBillId(billId);
            detailBill.setPrice(warehouse.getOutPrice());
            detailBill.setQuantity(result.getQuantity());
            detailBill.setCreatedBy(username);
            detailBill.setModifiedBy(username);
            detailBill.setCreatedDate(new Date());
            detailBill.setModifiedDate(new Date());
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
