package fpoly.edu.datn.vibee.repository;

import fpoly.edu.datn.vibee.entity.Bill;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
    List<Bill> findAllByStatus(String status, Pageable pageable);
    List<Bill> findAllByStatusAndId(String status, int id, Pageable pageable);
}
