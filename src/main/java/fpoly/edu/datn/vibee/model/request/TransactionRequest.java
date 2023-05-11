package fpoly.edu.datn.vibee.model.request;

import fpoly.edu.datn.vibee.model.result.TransactionResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    private int id;
    private String transactionMethod;
    private String transferMethod;
    private Boolean transfer247;
    private BigDecimal money;
    private String transactionStatus;
    private String typeSell;
    private int img;
    private String description;
    private String receivingAddress;
    private int transportCompanyId;
    private String receivingPhone;
    private String receivingName;
    private List<TransactionResult> sellProducts;
}
