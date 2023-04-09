package fpoly.edu.datn.vibee.model.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResult {
    private int productId;
    private int productVersionId;
    private int quantity;
    private int warehouseId;
}
