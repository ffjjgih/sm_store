package fpoly.edu.datn.vibee.model.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private int id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String avatar;
    private List<String> role;
    private String status;
}
