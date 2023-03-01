package fpoly.edu.datn.vibee.service.implement;

import fpoly.edu.datn.vibee.common.JwtTokenProvider;
import fpoly.edu.datn.vibee.entity.UserInfo;
import fpoly.edu.datn.vibee.entity.UserRole;
import fpoly.edu.datn.vibee.model.request.RegisterRequest;
import fpoly.edu.datn.vibee.model.response.LoginResponse;
import fpoly.edu.datn.vibee.model.response.RegisterResponse;
import fpoly.edu.datn.vibee.repository.AttachmentFileRepository;
import fpoly.edu.datn.vibee.repository.RoleRepo;
import fpoly.edu.datn.vibee.repository.UserInfoRepository;
import fpoly.edu.datn.vibee.repository.UserRoleRepo;
import fpoly.edu.datn.vibee.service.AuthService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class AuthServiceImpl implements AuthService {
    private final String BASIC_TOKEN = "Basic ";
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private AttachmentFileRepository attachmentFileRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRoleRepo userRoleRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ResponseEntity<?> login(String authorization) {
        log.info("AuthService-login:: Begin - Data Info[ Authorization: {}]", authorization);
        LoginResponse loginResponse = new LoginResponse();
        String username = "";
        String password = "";
        String role="";
        boolean isExist = false;
        if (authorization.startsWith(BASIC_TOKEN)) {
            String account = new String(Base64.getDecoder().decode(authorization.substring(6)));
            username = account.split(":")[0];
            password = account.split(":")[1];
        }

        isExist = userInfoRepository.existsAccountByEmail(username);
        if (!isExist) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tài khoản không tồn tại");
        }
        UserInfo userInfo = userInfoRepository.login(username, password);
        if (userInfo == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sai mật khẩu");
        }
        if (userInfo.getStatus().equals("ACTIVE")) {
            try {
                Authentication authentication = authenticationManager.  authenticate(
                        new UsernamePasswordAuthenticationToken(
                                userInfo.getEmail(),
                                userInfo.getPassword()
                        )
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);

                String jwt = jwtTokenProvider.generateToken(userInfo.getUsername());
                UserDetailImplUser userDetails = (UserDetailImplUser) authentication
                        .getPrincipal();
                List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());
                loginResponse.setAccessToken(jwt);
                loginResponse.setPhone(userInfo.getPhone());
                loginResponse.setRole(roles);
                loginResponse.setEmail(userInfo.getEmail());
                loginResponse.setAddress(userInfo.getAddress());
                loginResponse.setFullName(userInfo.getFullName());
                String avatar = attachmentFileRepository.findUrlById(userInfo.getAvatar());
                loginResponse.setAvatar(avatar);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sai mật khẩu");
            }
        }

        return ResponseEntity.ok(loginResponse);
    }

    @Override
    public String logout() {
        return null;
    }

    @Override
    public ResponseEntity<Boolean> isUsernameExist(String username) {
        boolean isExist = false;
        isExist = userInfoRepository.existsByEmail(username);
        if (!isExist)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(isExist);

        return ResponseEntity.ok(isExist);
    }

    @Override
    public ResponseEntity<RegisterResponse> register(RegisterRequest registerRequest) {
        log.info("AuthService-register:: Begin - Data Info[ RegisterRequest: {}]", registerRequest);
        RegisterResponse registerResponse = new RegisterResponse();
        UserInfo userInfo = new UserInfo();
        userInfo.setFullName(registerRequest.getFullName());
        userInfo.setEmail(registerRequest.getEmail());
        userInfo.setPhone(registerRequest.getPhone());
        userInfo.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        userInfo.setAddress(registerRequest.getAddress());
        userInfo.setAvatar(registerRequest.getAvatar());
        userInfo.setStatus("ACTIVE");
        userInfoRepository.save(userInfo);
        UserRole userRole = new UserRole();
        userRole.setUserId(userInfo.getId());
        if ("BUYER".equalsIgnoreCase(registerRequest.getRole())){
            userRole.setRole(1);
        }else{
            userRole.setRole(2);
        }
        userRole=this.userRoleRepo.save(userRole);
        registerResponse.setFullName(userInfo.getFullName());
        registerResponse.setEmail(userInfo.getEmail());
        registerResponse.setPhone(userInfo.getPhone());
        registerResponse.setAddress(userInfo.getAddress());
        registerResponse.setRole(roleRepo.getNameRoleById(userRole.getRole()));
        if (registerRequest.getAvatar() != 0) {
            String avatar = attachmentFileRepository.findUrlById(userInfo.getAvatar());
            registerResponse.setAvatar(avatar);
        }
        log.info("AuthService-register:: End - Data Info[ RegisterResponse: {}]", registerResponse);
        return ResponseEntity.ok(registerResponse);
    }
}
