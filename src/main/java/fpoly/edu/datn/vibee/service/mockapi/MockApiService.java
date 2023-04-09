package fpoly.edu.datn.vibee.service.mockapi;

import fpoly.edu.datn.vibee.model.fc.response.FCShippersResponse;

public interface MockApiService {
    FCShippersResponse getShipper(int transportCompanyId);
}
