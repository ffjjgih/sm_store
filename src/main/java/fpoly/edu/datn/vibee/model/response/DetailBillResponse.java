package fpoly.edu.datn.vibee.model.response;

import fpoly.edu.datn.vibee.model.result.DetailBillResult;
import fpoly.edu.datn.vibee.model.result.DetailOrderResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DetailBillResponse {
    private int billId;
    private int orderId;
    private String fullName;
    private String address;
    private String numberPhone;
    private BigDecimal price;
    private String createdDate;
    private String shipperName;
    private String shipperPhone;
    private String completedDate;
    private String receivingMethod;
    private String transferMethod;
    private String sendAddress;
    private String receiveAddress;
    private String receiveCompany;
    private List<DetailOrderResult> detailOrders;
    private List<DetailBillResult> detailBills;
}
