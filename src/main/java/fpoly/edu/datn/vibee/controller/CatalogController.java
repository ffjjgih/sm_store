package fpoly.edu.datn.vibee.controller;

import fpoly.edu.datn.vibee.model.response.CatalogResponse;
import fpoly.edu.datn.vibee.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vibee/api/v1/catalog")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;
    @GetMapping("/all")
    public ResponseEntity<CatalogResponse> getAllCatalog(){
        return catalogService.getAllCatalog();
    }
}
