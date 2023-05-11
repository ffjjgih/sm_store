package fpoly.edu.datn.vibee.model.response;

import fpoly.edu.datn.vibee.model.result.SellOfflineResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SellOfflineResponse {
    private int status;
    private String message;
    private List<SellOfflineResult> data;
}
