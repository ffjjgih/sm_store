package fpoly.edu.datn.vibee.repository;

import fpoly.edu.datn.vibee.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    @Query("SELECT u FROM UserInfo u WHERE u.email = ?1 AND u.password = ?2")
    UserInfo login(String username, String password);

    @Query("SELECT u FROM UserInfo u WHERE u.email = ?1")
    UserInfo findByUsername(String username);

    Boolean existsByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserInfo u WHERE u.email = :email and u.isActive = 0")
    boolean existsAccountByEmail(@Param("email") String username);
}
