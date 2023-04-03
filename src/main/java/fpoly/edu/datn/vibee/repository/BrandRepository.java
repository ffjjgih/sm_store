package fpoly.edu.datn.vibee.repository;

import fpoly.edu.datn.vibee.entity.Brand;
import fpoly.edu.datn.vibee.model.impl.BrandCatalogResult;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    @Modifying
    @Transactional
    @Query("update Brand b set b.status = ?1 where b.id = ?2")
    void deleteById(String status,int id);

    @Query("SELECT b.id as brandId, b.name as brandName FROM Brand b WHERE b.status = :status")
    List<BrandCatalogResult> findByStatus(String status);
}
