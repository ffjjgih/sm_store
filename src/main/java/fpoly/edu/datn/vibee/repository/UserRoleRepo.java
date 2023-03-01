
package fpoly.edu.datn.vibee.repository;

import fpoly.edu.datn.vibee.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepo extends JpaSpecificationExecutor<UserRole>,JpaRepository<UserRole, Integer>{

}
