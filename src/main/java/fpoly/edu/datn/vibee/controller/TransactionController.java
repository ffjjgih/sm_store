package fpoly.edu.datn.vibee.controller;

import fpoly.edu.datn.vibee.model.request.TransactionRequest;
import fpoly.edu.datn.vibee.model.response.BillResponse;
import fpoly.edu.datn.vibee.model.response.DetailBillResponse;
import fpoly.edu.datn.vibee.model.response.TransactionResponse;
import fpoly.edu.datn.vibee.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vibee/api/v1/bill")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TransactionController {
    @Autowired
    private BillService billService;

    @PostMapping("/transaction")
    public ResponseEntity<TransactionResponse> transaction(@RequestBody TransactionRequest request){
        return billService.transaction(request);
    }

    @GetMapping("/getBills")
    public ResponseEntity<BillResponse> getBills(@RequestParam(name="page", required = false, defaultValue = "0") int page,
                                                 @RequestParam(name="size", required = false, defaultValue = "10") int size,
                                                 @RequestParam(name="status", required = false, defaultValue = "đang xử lý") String status,
                                                 @RequestParam(name="search", required = false, defaultValue = "0") String search){
        return billService.getBills(search, status, page, size);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<DetailBillResponse> getBillDetail(@PathVariable(name="id") int id){
        return billService.getBillDetail(id);
    }
}
