package fpoly.edu.datn.vibee.service;

import fpoly.edu.datn.vibee.model.info.Filter;
import fpoly.edu.datn.vibee.model.request.ProductRequest;
import fpoly.edu.datn.vibee.model.response.ProductsResponse;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    ResponseEntity<ProductsResponse> createProduct(ProductRequest productRequest);

    ResponseEntity<ProductsResponse> getAllProduct(Filter filter);
    ResponseEntity<ProductsResponse> getProductsSell(Filter filter);
}
