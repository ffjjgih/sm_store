package fpoly.edu.datn.vibee.service;

import fpoly.edu.datn.vibee.model.info.Filter;
import fpoly.edu.datn.vibee.model.request.TransportCompanyRequest;
import fpoly.edu.datn.vibee.model.response.DetailTransportCompanyResponse;
import fpoly.edu.datn.vibee.model.response.TransportCompaniesResponse;
import org.springframework.http.ResponseEntity;

public interface TransportService {
    ResponseEntity<TransportCompaniesResponse> create(TransportCompanyRequest request);
    ResponseEntity<TransportCompaniesResponse> update(TransportCompanyRequest request);
    ResponseEntity<TransportCompaniesResponse> delete(int id);
    ResponseEntity<DetailTransportCompanyResponse> detail(int id);
    ResponseEntity<TransportCompaniesResponse> getAll(Filter filter);
}
