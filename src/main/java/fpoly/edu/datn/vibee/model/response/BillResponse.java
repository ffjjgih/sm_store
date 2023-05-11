package fpoly.edu.datn.vibee.model.response;

import fpoly.edu.datn.vibee.model.result.BillResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BillResponse {
    private String billStatus;
    private int status;
    private String message;
    private List<BillResult> data;
}
