package fpoly.edu.datn.vibee.service;

import fpoly.edu.datn.vibee.model.info.Filter;
import fpoly.edu.datn.vibee.model.request.BrandRequest;
import fpoly.edu.datn.vibee.model.response.BrandsResponse;

public interface BrandService {
    BrandsResponse getAllBrand(Filter filter);
    BrandsResponse createBrand(BrandRequest request);
    BrandsResponse updateBrand(BrandRequest request);
    BrandsResponse deleteBrand(int id);
}
