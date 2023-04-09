package fpoly.edu.datn.vibee.controller;

import fpoly.edu.datn.vibee.model.info.Filter;
import fpoly.edu.datn.vibee.model.request.TransportCompanyRequest;
import fpoly.edu.datn.vibee.model.response.DetailTransportCompanyResponse;
import fpoly.edu.datn.vibee.model.response.TransportCompaniesResponse;
import fpoly.edu.datn.vibee.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vibee/api/v1/transport")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TransportController {
    @Autowired
    private TransportService transportService;

    @GetMapping("/all-company")
    public ResponseEntity<TransportCompaniesResponse> getAll(@RequestParam(name="page", required = false, defaultValue = "0") int page,
                                                             @RequestParam(name="size", required = false, defaultValue = "10") int size,
                                                             @RequestParam(name="sort", required = false, defaultValue = "asc") String sort,
                                                             @RequestParam(name="order", required = false, defaultValue = "status") String order,
                                                             @RequestParam(name="search", required = false, defaultValue = "") String search) {
        Filter filter = new Filter();
        filter.setPage(page);
        filter.setSize(size);
        filter.setSort(sort);
        filter.setOrder(order);
        filter.setSearch(search);
        return this.transportService.getAll(filter);
    }

    @PostMapping("/create-company")
    public ResponseEntity<TransportCompaniesResponse> create(@RequestBody TransportCompanyRequest request) {
        return this.transportService.create(request);
    }

    @PostMapping("/update-company")
    public ResponseEntity<TransportCompaniesResponse> update(@RequestBody TransportCompanyRequest request) {
        return this.transportService.update(request);
    }

    @DeleteMapping("/delete-company/{id}")
    public ResponseEntity<TransportCompaniesResponse> delete(@PathVariable("id") int id) {
        return this.transportService.delete(id);
    }

    @GetMapping("/detail-company/{id}")
    public ResponseEntity<DetailTransportCompanyResponse> detail(@PathVariable("id") int id) {
        return this.transportService.detail(id);
    }
}
