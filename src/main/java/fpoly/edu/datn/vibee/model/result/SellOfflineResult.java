package fpoly.edu.datn.vibee.model.result;

import fpoly.edu.datn.vibee.model.response.UploadFileResponse;
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
public class SellOfflineResult {
    private int productId;
    private String productName;
    private UploadFileResponse image;
    private int brandId;
    private int seriesId;
    private String brandName;
    private List<SellOfflineProductVersionResult> versionResults;
}
