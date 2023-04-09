package fpoly.edu.datn.vibee.repository;

import fpoly.edu.datn.vibee.entity.DetailBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailBillRepository extends JpaRepository<DetailBill, Integer>, JpaSpecificationExecutor<DetailBill> {
}
