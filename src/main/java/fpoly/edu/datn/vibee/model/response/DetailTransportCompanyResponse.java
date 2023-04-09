package fpoly.edu.datn.vibee.model.response;

import fpoly.edu.datn.vibee.model.result.ShipperResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DetailTransportCompanyResponse {
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
    private int countShipper;
    private int countShipperActive;
    private UploadFileResponse attachment;
    private List<ShipperResult> shippers;
}
