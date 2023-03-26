package fpoly.edu.datn.vibee.model.response;

import fpoly.edu.datn.vibee.model.info.Filter;
import fpoly.edu.datn.vibee.model.result.BrandResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BrandsResponse {
    private String message;
    private int status;
    private List<BrandResult> data;
    private Filter filter;
}
