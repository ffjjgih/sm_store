package fpoly.edu.datn.vibee.service;

import fpoly.edu.datn.vibee.model.request.TransactionRequest;
import fpoly.edu.datn.vibee.model.response.TransactionResponse;
import org.springframework.http.ResponseEntity;

public interface BillService {
    ResponseEntity<TransactionResponse> transaction(TransactionRequest request);
}
