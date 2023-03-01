package fpoly.edu.datn.vibee.repository;

import fpoly.edu.datn.vibee.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer>,JpaSpecificationExecutor<Role>{
    @Query("select r.name from Role r where r.id in (select us.role from UserRole us where us.userId =: id)")
    List<String> findRoleByUserId(int id);

    @Query("SELECT r.name FROM Role r WHERE r.id = :idUserRole")
    String getNameRoleById(@Param("idUserRole") int idUserRole);
}
