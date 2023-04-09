package fpoly.edu.datn.vibee.model.response;

import fpoly.edu.datn.vibee.model.info.Filter;
import fpoly.edu.datn.vibee.model.result.TransportCompanyResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransportCompaniesResponse {
    private int status;
    private String message;
    private List<TransportCompanyResult> data;
    private Filter filter;
}
