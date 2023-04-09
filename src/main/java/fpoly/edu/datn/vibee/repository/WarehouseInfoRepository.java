package fpoly.edu.datn.vibee.repository;

import fpoly.edu.datn.vibee.entity.WarehouseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseInfoRepository extends JpaRepository<WarehouseInfo, Integer> {
    @Query("select wi from WarehouseInfo wi where wi.productVersionId= :id and wi.quantity < :quantity")
    Boolean checkQuantity(@Param("id") Integer id, @Param("quantity") Integer quantity);

    @Query("select wi from WarehouseInfo wi where wi.productVersionId= :id")
    WarehouseInfo getWarehouseInfoByProductVersionId(@Param("id") Integer id);
}
