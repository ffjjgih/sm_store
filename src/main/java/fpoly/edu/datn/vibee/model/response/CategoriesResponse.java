package fpoly.edu.datn.vibee.model.response;

import fpoly.edu.datn.vibee.model.info.Filter;
import fpoly.edu.datn.vibee.model.result.CategoryResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesResponse {
    private String message;
    private int status;
    private List<CategoryResult> data;
    private Filter filter;
}
