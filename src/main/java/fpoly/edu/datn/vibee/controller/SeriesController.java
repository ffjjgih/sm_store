package fpoly.edu.datn.vibee.controller;

import fpoly.edu.datn.vibee.model.impl.SeriesCatalogResult;
import fpoly.edu.datn.vibee.model.request.SeriesRequest;
import fpoly.edu.datn.vibee.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vibee/api/v1/series")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SeriesController {
    @Autowired
    private SeriesService   seriesService;

    @PostMapping("/create")
    public ResponseEntity<SeriesCatalogResult> create(@RequestBody SeriesRequest request){
        return seriesService.create(request);
    }
}
