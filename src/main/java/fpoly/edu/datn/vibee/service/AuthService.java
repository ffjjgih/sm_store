package fpoly.edu.datn.vibee.service;
import fpoly.edu.datn.vibee.model.request.RegisterRequest;
import fpoly.edu.datn.vibee.model.response.LoginResponse;
import fpoly.edu.datn.vibee.model.response.RegisterResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> login(String authorization);

    String logout();

    ResponseEntity<Boolean> isUsernameExist(String username);

    ResponseEntity<RegisterResponse> register(RegisterRequest registerRequest);

}
