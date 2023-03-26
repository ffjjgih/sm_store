package fpoly.edu.datn.vibee.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BrandRequest {
    private int id;
    private String name;
    private String address;
    private String email;
    private String website;
    private String description;
    private int fileId;
    private String status;
    private String birthOfDate;

}
