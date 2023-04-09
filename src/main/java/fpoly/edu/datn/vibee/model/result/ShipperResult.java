package fpoly.edu.datn.vibee.model.result;

import fpoly.edu.datn.vibee.model.response.UploadFileResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShipperResult {
    private String fullname;
    private String phone;
    private String email;
    private String shipCode;
    private int countOrder;
    private String avatar;
}
