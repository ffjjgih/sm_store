package fpoly.edu.datn.vibee.service;

import fpoly.edu.datn.vibee.model.info.Filter;
import fpoly.edu.datn.vibee.model.request.CategoryRequest;
import fpoly.edu.datn.vibee.model.response.CategoriesResponse;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    ResponseEntity<CategoriesResponse> getAllCategory(Filter filter);
    ResponseEntity<CategoriesResponse> createCategory(CategoryRequest request);
    ResponseEntity<CategoriesResponse> updateCategory(CategoryRequest request);
    ResponseEntity<CategoriesResponse> deleteCategory(int id);

    ResponseEntity<CategoriesResponse> getCategoryById(int id);
}
