package fpoly.edu.datn.vibee.model.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DetailOrderResult {
    private int orderId;
    private int detailId;
    private String date;
    private String description;
    private String status;
}
