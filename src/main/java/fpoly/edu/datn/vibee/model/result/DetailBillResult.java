package fpoly.edu.datn.vibee.model.result;

import fpoly.edu.datn.vibee.model.response.UploadFileResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DetailBillResult {
    private UploadFileResponse image;
    private String name;
    private BigDecimal price;
    private int quantity;
    private BigDecimal sumPrice;
}
