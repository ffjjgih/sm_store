package fpoly.edu.datn.vibee.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductVersionRequest {
    private int id;
    private String color;
    private String ram;
    private String rom;
    private int quantity;
    private BigDecimal inPrice;
    private BigDecimal outPrice;
    private int image;
}
