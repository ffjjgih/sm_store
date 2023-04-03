package fpoly.edu.datn.vibee.service.implement;

import fpoly.edu.datn.vibee.entity.ProductSeries;
import fpoly.edu.datn.vibee.model.impl.SeriesCatalogResult;
import fpoly.edu.datn.vibee.model.request.SeriesRequest;
import fpoly.edu.datn.vibee.repository.SeriesRepository;
import fpoly.edu.datn.vibee.service.SeriesService;
import fpoly.edu.datn.vibee.utilities.CommonUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class SeriesServiceImplement implements SeriesService {
    @Autowired
    private SeriesRepository seriesRepository;
    @Override
    public ResponseEntity<SeriesCatalogResult> create(SeriesRequest request) {
        log.info("Create series with data: {}", CommonUtil.beanToString(request));
        ProductSeries productSeries = new ProductSeries();
        productSeries.setSeriesName(request.getName());
        productSeries.setBrandId(request.getBrandId());
        productSeries.setCategoryId(request.getCategoryId());
        productSeries=seriesRepository.save(productSeries);
        ProductSeries finalProductSeries = productSeries;
        SeriesCatalogResult seriesCatalogResult = new SeriesCatalogResult() {
            @Override
            public int getSeriesId() {
                return finalProductSeries.getId();
            }

            @Override
            public String getSeriesName() {
                return finalProductSeries.getSeriesName();
            }
        };
        log.info("Create series success-data: {}", CommonUtil.beanToString(seriesCatalogResult));
        return ResponseEntity.ok(seriesCatalogResult);
    }
}
