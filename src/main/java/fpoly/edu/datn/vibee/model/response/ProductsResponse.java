package fpoly.edu.datn.vibee.model.response;

import fpoly.edu.datn.vibee.model.info.Filter;
import fpoly.edu.datn.vibee.model.result.ProductResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductsResponse {
    private List<ProductResult> products;
    private Filter filter;
    private int status;
    private String message;
}
