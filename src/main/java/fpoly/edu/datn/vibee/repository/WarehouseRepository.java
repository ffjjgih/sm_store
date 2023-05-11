package fpoly.edu.datn.vibee.repository;

import fpoly.edu.datn.vibee.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {

    @Query("select w from Warehouse w where w.warehouseInfoId = :warehouseInfoId and w.status = :status")
    Warehouse getWarehouseByWarehouseInfoIdAndStatus(Integer warehouseInfoId, String status);
}
