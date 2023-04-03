package fpoly.edu.datn.vibee.model.response;

import fpoly.edu.datn.vibee.model.impl.BrandCatalogResult;
import fpoly.edu.datn.vibee.model.impl.CategoryCatalogResult;
import fpoly.edu.datn.vibee.model.impl.SeriesCatalogResult;
import fpoly.edu.datn.vibee.model.impl.SupplierCatalogResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CatalogResponse {
    private List<CategoryCatalogResult> categories;
    private List<BrandCatalogResult> brands;
    private List<SeriesCatalogResult> series;
    private List<SupplierCatalogResult> suppliers;
}
