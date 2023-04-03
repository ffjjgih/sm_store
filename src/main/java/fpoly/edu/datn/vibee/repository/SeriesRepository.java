package fpoly.edu.datn.vibee.repository;

import fpoly.edu.datn.vibee.entity.ProductSeries;
import fpoly.edu.datn.vibee.model.impl.SeriesCatalogResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeriesRepository extends JpaRepository<ProductSeries, Integer> {
    @Query("select s.id as seriesId, s.seriesName as seriesName from ProductSeries s")
    List<SeriesCatalogResult> findByStatus();
}
