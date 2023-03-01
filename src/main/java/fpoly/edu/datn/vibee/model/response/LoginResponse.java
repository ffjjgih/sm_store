package fpoly.edu.datn.vibee.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String avatar;
    private List<String> role;
}
