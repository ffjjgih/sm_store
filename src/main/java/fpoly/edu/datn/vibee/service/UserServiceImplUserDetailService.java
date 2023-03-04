//package fpoly.edu.datn.vibee.service;
//
//import fpoly.edu.datn.vibee.entity.UserInfo;
//import fpoly.edu.datn.vibee.repository.RoleRepo;
//import fpoly.edu.datn.vibee.repository.UserInfoRepository;
//import fpoly.edu.datn.vibee.service.implement.UserDetailImplUser;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class UserServiceImplUserDetailService implements UserDetailsService {
//	private static final Logger logger = LoggerFactory.getLogger(UserServiceImplUserDetailService.class);
//	@Autowired
//	private UserInfoRepository userRepo;
//	@Autowired
//	private RoleRepo roleRepo;
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		try {
//			UserInfo account = userRepo.findByUsername(username);
//			List<String> lstroles = roleRepo.findRoleByUserId(account.getId());
//			String password = account.getPassword();
//
//			List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
//
//			for (String grantedAuthority : lstroles) {
//				GrantedAuthority authority = new SimpleGrantedAuthority(grantedAuthority);
//				grantList.add(authority);
//			}
//			UserDetails userDetail = (UserDetails) new UserDetailImplUser(username, password, grantList);
//			logger.info(userDetail.toString());
//
//			return userDetail;
//		} catch (Exception e) {
//			throw new UsernameNotFoundException(username + " not found!");
//
//		}
//	}
//
//
//}
