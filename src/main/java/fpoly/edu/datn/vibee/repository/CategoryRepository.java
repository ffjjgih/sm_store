package fpoly.edu.datn.vibee.repository;

import fpoly.edu.datn.vibee.entity.Category;
import fpoly.edu.datn.vibee.model.impl.CategoryCatalogResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Modifying
    @Transactional
    @Query("update Category c set c.status = ?1 where c.id = ?2")
    void updateStatus(String status, int id);

    @Query("select c from Category c where (:search IS NULL OR :search = '' OR c.name LIKE %:search%)")
    Page<Category> findByName(Pageable page, String search);

    @Query("select c.id as categoryId, c.name as categoryName from Category c where c.status = :status")
    List<CategoryCatalogResult> findByStatus(String status);
}
