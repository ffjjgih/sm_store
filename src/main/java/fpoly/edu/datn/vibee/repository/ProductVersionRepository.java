package fpoly.edu.datn.vibee.repository;

import fpoly.edu.datn.vibee.entity.ProductVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVersionRepository extends JpaRepository<ProductVersion, Integer> {
}
