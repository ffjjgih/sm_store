package fpoly.edu.datn.vibee.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransportCompanyRequest {
    private int id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String description;
    private String status;
    private String website;
    private int fileId;
}