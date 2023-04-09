package fpoly.edu.datn.vibee.repository;

import fpoly.edu.datn.vibee.entity.Category;
import fpoly.edu.datn.vibee.entity.TransportCompany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TransportCompanyRepository extends JpaRepository<TransportCompany, Integer> {
    @Transactional
    @Modifying
    @Query(value = "update TransportCompany t set t.status = :status where t.id = :id")
    void updateStatus(@Param("id") int id, @Param("status") String status);

    @Query("select t from TransportCompany t where (:search IS NULL OR :search = '' OR t.name LIKE %:search%)")
    Page<TransportCompany> findByName(Pageable page, String search);
}
