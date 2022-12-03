package com.ncs503.Babybook.service;

import com.ncs503.Babybook.models.request.MedicalDataRequest;
import com.ncs503.Babybook.models.request.MedicalDataUpDateRequest;
import com.ncs503.Babybook.models.request.SubjectRequest;
import com.ncs503.Babybook.models.response.MedicalDataResponse;
import com.ncs503.Babybook.models.response.SubjectResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


public interface MedicalDataService {

    MedicalDataResponse create (MedicalDataRequest request, String token) throws IOException;
    void delete (Long id, String token);
    MedicalDataResponse update (Long id, MedicalDataUpDateRequest request, String token)  throws IOException;
    MedicalDataResponse getById (Long id, String token) throws IOException;

}
