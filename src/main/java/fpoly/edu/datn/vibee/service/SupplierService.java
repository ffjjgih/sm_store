package fpoly.edu.datn.vibee.service;

import fpoly.edu.datn.vibee.model.info.Filter;
import fpoly.edu.datn.vibee.model.request.SupplierRequest;
import fpoly.edu.datn.vibee.model.response.SuppliersResponse;
import org.springframework.http.ResponseEntity;

public interface SupplierService {
    ResponseEntity<SuppliersResponse> getAllSupplier(Filter filter);
    ResponseEntity<SuppliersResponse> createSupplier(SupplierRequest request);
    ResponseEntity<SuppliersResponse> updateSupplier(SupplierRequest request);
    ResponseEntity<SuppliersResponse> deleteSupplier(int id);
}
