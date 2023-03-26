package fpoly.edu.datn.vibee.controller;

import fpoly.edu.datn.vibee.model.info.Filter;
import fpoly.edu.datn.vibee.model.request.SupplierRequest;
import fpoly.edu.datn.vibee.model.response.SuppliersResponse;
import fpoly.edu.datn.vibee.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vibee/api/v1/supplier")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @GetMapping("/all")
    public ResponseEntity<SuppliersResponse> getAllSupplier(@RequestParam(name="page", required = false, defaultValue = "0") int page,
                                                            @RequestParam(name="size", required = false, defaultValue = "10") int size,
                                                            @RequestParam(name="sort", required = false, defaultValue = "asc") String sort,
                                                            @RequestParam(name="order", required = false, defaultValue = "status") String order,
                                                            @RequestParam(name="search", required = false, defaultValue = "") String search){
        Filter filter = new Filter();
        filter.setPage(page);
        filter.setSize(size);
        filter.setSort(sort);
        filter.setOrder(order);
        filter.setSearch(search);
        return this.supplierService.getAllSupplier(filter);
    }

    @PostMapping("/create")
    public ResponseEntity<SuppliersResponse> createSupplier(@RequestBody SupplierRequest request){
        return this.supplierService.createSupplier(request);
    }

    @PutMapping("/update")
    public ResponseEntity<SuppliersResponse> updateSupplier(@RequestBody SupplierRequest request){
        return this.supplierService.updateSupplier(request);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<SuppliersResponse> deleteSupplier(@PathVariable("id") int id){
        return this.supplierService.deleteSupplier(id);
    }
}
