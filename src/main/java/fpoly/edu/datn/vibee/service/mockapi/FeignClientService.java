package fpoly.edu.datn.vibee.service.mockapi;

import fpoly.edu.datn.vibee.model.fc.response.FCShippersResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "mockApi", url = "https://642e316e2b883abc6408bd0b.mockapi.io",path="/api/v1")
public interface FeignClientService {
    @GetMapping("/shipper")
    List<FCShippersResponse> getAll();
}
