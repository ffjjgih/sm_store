package fpoly.edu.datn.vibee.repository;

import fpoly.edu.datn.vibee.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findAllByStatus(Pageable page,String status);

    @Query("SELECT p FROM Product p WHERE p.status = :status AND (:search IS NULL OR :search = '' OR p.name LIKE %:search%)")
    List<Product> findAllByStatusAndNameContaining(@Param("status") String status,@Param("search") String search);

    @Query("SELECT p FROM Product p WHERE p.id =(SELECT pv FROM ProductVersion pv WHERE pv.id = (SELECT wi FROM WarehouseInfo wi WHERE wi.id = (SELECT w FROM Warehouse w WHERE w.id = :warehouseId)))")
    Product findProductByWarehouseId(@Param("warehouseId") int warehouseId);
}
