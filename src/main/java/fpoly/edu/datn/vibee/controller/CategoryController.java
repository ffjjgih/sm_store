package fpoly.edu.datn.vibee.controller;

import fpoly.edu.datn.vibee.model.info.Filter;
import fpoly.edu.datn.vibee.model.request.CategoryRequest;
import fpoly.edu.datn.vibee.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vibee/api/v1/category")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity createCategory(@RequestBody CategoryRequest request){
        return ResponseEntity.ok(categoryService.createCategory(request));
    }

    @PutMapping("/update")
    public ResponseEntity updateCategory(@RequestBody CategoryRequest request){
        return ResponseEntity.ok(categoryService.updateCategory(request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable("id") int id){
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }

    @GetMapping("/all")
    public ResponseEntity getAllCategory(@RequestParam(name="page", required = false, defaultValue = "0") int page,
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
        return ResponseEntity.ok(categoryService.getAllCategory(filter));
    }

//    @GetMapping("/all")
//    public ResponseEntity getAllCategory(){
//        Filter filter = new Filter();
//        filter.setPage(0);
//        filter.setSize(10);
//        filter.setSort("asc");
//        filter.setOrder("status");
//        filter.setSearch("");
//        return ResponseEntity.ok(categoryService.getAllCategory(filter));
//    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity getCategoryById(@PathVariable("id") int id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }
}
