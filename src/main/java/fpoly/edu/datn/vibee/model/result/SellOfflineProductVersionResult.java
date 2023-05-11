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
public class SellOfflineProductVersionResult {
    private int productVersionId;
    private BigDecimal price;
    private int warehouseId;
    private int warehouseInfoId;
    private int quantity;
    private String ram;
    private String rom;
    private String color;
    private int supplierId;
}
