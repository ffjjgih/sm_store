package fpoly.edu.datn.vibee.service;

import fpoly.edu.datn.vibee.model.response.CatalogResponse;
import org.springframework.http.ResponseEntity;

public interface CatalogService {

    ResponseEntity<CatalogResponse> getAllCatalog();
}
