package fpoly.edu.datn.vibee.model.response;

import fpoly.edu.datn.vibee.model.info.Filter;
import fpoly.edu.datn.vibee.model.result.SupplierResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SuppliersResponse {
    private String message;
    private List<SupplierResult> data;
    private int status;
    private Filter filter;
}
