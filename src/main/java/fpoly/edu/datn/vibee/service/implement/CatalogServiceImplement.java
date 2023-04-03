package fpoly.edu.datn.vibee.service.implement;

import fpoly.edu.datn.vibee.entity.Category;
import fpoly.edu.datn.vibee.model.impl.BrandCatalogResult;
import fpoly.edu.datn.vibee.model.impl.SeriesCatalogResult;
import fpoly.edu.datn.vibee.model.impl.SupplierCatalogResult;
import fpoly.edu.datn.vibee.model.response.CatalogResponse;
import fpoly.edu.datn.vibee.model.impl.CategoryCatalogResult;
import fpoly.edu.datn.vibee.repository.BrandRepository;
import fpoly.edu.datn.vibee.repository.CategoryRepository;
import fpoly.edu.datn.vibee.repository.SeriesRepository;
import fpoly.edu.datn.vibee.repository.SupplierRepository;
import fpoly.edu.datn.vibee.service.CatalogService;
import fpoly.edu.datn.vibee.utilities.CommonUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class CatalogServiceImplement implements CatalogService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public ResponseEntity<CatalogResponse> getAllCatalog() {
        log.info("Get all catalog");
        CatalogResponse catalogResponse = new CatalogResponse();
        List<CategoryCatalogResult> categoryCatalogResults =  categoryRepository.findByStatus("hoạt động");
        List<SupplierCatalogResult> supplierCatalogResults = supplierRepository.findByStatus("hoạt động");
        List<BrandCatalogResult> brandCatalogResults = brandRepository.findByStatus("hoạt động");
        List<SeriesCatalogResult> seriesCatalogResults = seriesRepository.findByStatus();
        catalogResponse.setCategories(categoryCatalogResults);
        catalogResponse.setSuppliers(supplierCatalogResults);
        catalogResponse.setBrands(brandCatalogResults);
        catalogResponse.setSeries(seriesCatalogResults);
        log.info("Get all catalog success-data: {}", CommonUtil.beanToString(catalogResponse));
        return ResponseEntity.ok(catalogResponse);
    }
}
