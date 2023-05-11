package fpoly.edu.datn.vibee.repository;

import fpoly.edu.datn.vibee.entity.ProductVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVersionRepository extends JpaRepository<ProductVersion, Integer> {

    @Query("select pv from ProductVersion pv where pv.productId = :id")
    List<ProductVersion> getProductVersionByProductId(Integer id);
}
