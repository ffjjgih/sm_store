package fpoly.edu.datn.vibee.service.mockapi;

import fpoly.edu.datn.vibee.model.fc.response.FCShippersResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Log4j2
public class MockApiServiceImplement implements MockApiService{
    @Autowired
    private FeignClientService feignClientService;
    @Override
    public FCShippersResponse getShipper(int transportCompanyId) {
        List<FCShippersResponse> list = feignClientService.getAll();
        List<FCShippersResponse> shippers=new ArrayList<>();
        for (FCShippersResponse s:list){
            if (Integer.parseInt(s.getTransportCompanyId())==transportCompanyId){
                shippers.add(s);
            }
        }
        Random random=new Random();
        int index=random.nextInt(shippers.size());
        return shippers.get(index);
    }
}
