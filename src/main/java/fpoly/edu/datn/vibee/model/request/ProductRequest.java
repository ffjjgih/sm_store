package fpoly.edu.datn.vibee.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String status;
    private int categoryId;
    private int brandId;
    private int seriesId;
    private int supplierId;
    private String description;
    private ProductPropertyRequest detail;
    private int img;
    private String barcode;
    private String name;
    private int media;
    private List<ProductVersionRequest> versions;
    private List<Integer> images;
}
