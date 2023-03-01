package fpoly.edu.datn.vibee.service.implement;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserDetailImplUser extends User {
    private String fullname;
    private String cccd;
    private String address;
    private String email;
    private String phone;
    public UserDetailImplUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }


    public UserDetailImplUser(String username, String password, Collection<? extends GrantedAuthority> authorities, String fullname, String cccd,
                              String address, String email, String phone) {
        super(username, password, authorities);
        this.fullname = fullname;
        this.cccd = cccd;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public String getCccd() {
        return cccd;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

}
