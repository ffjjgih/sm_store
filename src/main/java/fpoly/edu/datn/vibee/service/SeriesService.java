package fpoly.edu.datn.vibee.service;

import fpoly.edu.datn.vibee.model.impl.SeriesCatalogResult;
import fpoly.edu.datn.vibee.model.request.SeriesRequest;
import org.springframework.http.ResponseEntity;

public interface SeriesService {
    ResponseEntity<SeriesCatalogResult> create(SeriesRequest request);
}
