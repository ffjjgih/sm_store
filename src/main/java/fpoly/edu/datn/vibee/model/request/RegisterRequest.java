package fpoly.edu.datn.vibee.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String fullName;
    private String cccd;
    private String address;
    private String email;
    private String phone;
    private String role;
    private int avatar;
}
