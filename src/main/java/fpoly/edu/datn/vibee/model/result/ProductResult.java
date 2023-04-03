package fpoly.edu.datn.vibee.model.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResult {
    private int id;
    private String name;
    private String description;
    private String image;
    private String price;
    private String quantity;
    private String status;
    private String category;
    private String brand;
    private String createdDate;
    private String updatedDate;

    private int inventory;
}
