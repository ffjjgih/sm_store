package fpoly.edu.datn.vibee.controller;

import fpoly.edu.datn.vibee.model.request.RegisterRequest;
import fpoly.edu.datn.vibee.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vibee/api/v1/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @Autowired
    private AuthService authService;
    @GetMapping("/login")
    public ResponseEntity<?> Login() {
        String token="Basic bGFtYmNwaDE2OTgwQGZwdC5lZHUudm46JDJ5JDA3JEpJdUZJSjJNbFNZcmh6UDZYZUplME9rcVRxSkR3QXVaSm5Ya2g0bXFTQ1BmY3ViODA5bGtD";
        return this.authService.login(token);
    }

    @GetMapping("/logout")
    public String Logout(){
        return "Logout";
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> Register(@RequestBody RegisterRequest registerRequest){
        return this.authService.register(registerRequest);
    }

    @GetMapping("/forgot-password")
    public String ForgotPassword(){
        return "ForgotPassword";
    }

    @GetMapping("/check-username")
    public ResponseEntity<Boolean> CheckUsername(@RequestParam("username") String username){
        return this.authService.isUsernameExist(username);
    }

}
