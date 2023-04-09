package fpoly.edu.datn.vibee.repository;

import fpoly.edu.datn.vibee.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
}
