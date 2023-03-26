package fpoly.edu.datn.vibee.repository;

import fpoly.edu.datn.vibee.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    @Query("select s from Supplier s where (:search IS NULL OR :search = '' OR s.name LIKE %:search%)")
    Page<Supplier> findByName(Pageable page, String search);

    @Modifying
    @Transactional
    @Query("update Supplier s set s.status = ?1 where s.id = ?2")
    void updateStatus(String status, int id);
}
