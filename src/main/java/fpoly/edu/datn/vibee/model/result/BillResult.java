package fpoly.edu.datn.vibee.model.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BillResult {
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
}
