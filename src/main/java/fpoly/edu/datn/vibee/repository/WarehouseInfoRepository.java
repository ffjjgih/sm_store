package fpoly.edu.datn.vibee.repository;

import fpoly.edu.datn.vibee.entity.WarehouseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseInfoRepository extends JpaRepository<WarehouseInfo, Integer> {
}
