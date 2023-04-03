package fpoly.edu.datn.vibee.repository;

import fpoly.edu.datn.vibee.entity.ProductProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPropertiesRepository extends JpaRepository<ProductProperty, Integer> {

}
