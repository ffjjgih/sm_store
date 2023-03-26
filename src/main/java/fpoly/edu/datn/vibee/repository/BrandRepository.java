package fpoly.edu.datn.vibee.repository;

import fpoly.edu.datn.vibee.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    @Modifying
    @Transactional
    @Query("update Brand b set b.status = ?1 where b.id = ?2")
    void deleteById(String status,int id);
}
