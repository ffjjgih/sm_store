package fpoly.edu.datn.vibee.controller;

import fpoly.edu.datn.vibee.model.info.Filter;
import fpoly.edu.datn.vibee.model.request.ProductRequest;
import fpoly.edu.datn.vibee.model.response.BrandsResponse;
import fpoly.edu.datn.vibee.model.response.ProductsResponse;
import fpoly.edu.datn.vibee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vibee/api/v1/product")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ProductsResponse> createProduct(@RequestBody ProductRequest productRequest){
        return productService.createProduct(productRequest);
    }

    @GetMapping("/all")
    public ResponseEntity<ProductsResponse> getAllProduct(@RequestParam(name="page", required = false, defaultValue = "0") int page,
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
        return this.productService.getAllProduct(filter);
    }

    @GetMapping("/get-sell")
    public ResponseEntity<ProductsResponse> getProductsSell(@RequestParam(name="page", required = false, defaultValue = "0") int page,
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
        return this.productService.getProductsSell(filter);
    }
}
