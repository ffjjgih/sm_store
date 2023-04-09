package fpoly.edu.datn.vibee.repository;

import fpoly.edu.datn.vibee.entity.DetailOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailOrderRepository extends JpaRepository<DetailOrder,Integer> {
}
