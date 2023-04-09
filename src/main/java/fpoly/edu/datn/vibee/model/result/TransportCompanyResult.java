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
public class TransportCompanyResult {
    private int id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String description;
    private String status;
    private String createdDate;
    private String createdBy;
    private String updatedDate;
    private String updatedBy;
    private String website;
    private int countOrder;
    private UploadFileResponse attachment;
}
