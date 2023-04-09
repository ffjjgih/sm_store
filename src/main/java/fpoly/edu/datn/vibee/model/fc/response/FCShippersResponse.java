package fpoly.edu.datn.vibee.model.fc.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FCShippersResponse {
    private String shipperCode;
    private String fullName;
    private String avatar;
    private String numberPhone;
    private String transportCompanyId;
    private String email;
}
