package fpoly.edu.datn.vibee.controller;

import fpoly.edu.datn.vibee.model.info.Filter;
import fpoly.edu.datn.vibee.model.request.BrandRequest;
import fpoly.edu.datn.vibee.model.response.BrandsResponse;
import fpoly.edu.datn.vibee.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vibee/api/v1/brand")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("/all")
    public ResponseEntity<BrandsResponse> getAllBrand(@RequestParam(name="page", required = false, defaultValue = "0") int page,
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
        BrandsResponse brandsResponse = this.brandService.getAllBrand(filter);
        return ResponseEntity.ok(brandsResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<BrandsResponse> createBrand(@RequestBody BrandRequest request){
        BrandsResponse brandsResponse = this.brandService.createBrand(request);
        return ResponseEntity.ok(brandsResponse);
    }

    @PutMapping("/update")
    public ResponseEntity<BrandsResponse> updateBrand(@RequestBody BrandRequest request){
        BrandsResponse brandsResponse = this.brandService.updateBrand(request);
        return ResponseEntity.ok(brandsResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BrandsResponse> deleteBrand(@PathVariable("id") int id){
        BrandsResponse brandsResponse = this.brandService.deleteBrand(id);
        return ResponseEntity.ok(brandsResponse);
    }
}
