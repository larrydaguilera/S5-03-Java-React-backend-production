package com.ncs503.Babybook.service;

import com.ncs503.Babybook.models.request.SubjectRequest;
import com.ncs503.Babybook.models.request.SubjectUpDateRequest;
import com.ncs503.Babybook.models.response.PaginationResponse;
import com.ncs503.Babybook.models.response.SubjectImageResponse;
import com.ncs503.Babybook.models.response.SubjectResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


public interface SubjectService {

    SubjectResponse create (SubjectRequest request, String token) throws IOException;
    void delete (Long id, String token);
    SubjectResponse update (Long id, SubjectUpDateRequest request, String token) throws IOException;

    SubjectImageResponse updateImage (Long id, MultipartFile image, String token) throws IOException;
    SubjectResponse getById (Long id) throws IOException;
    PaginationResponse getSubjectByName(String name, String order, Optional<Integer> pageNumber, Optional<Integer> size, String token);

    PaginationResponse getSubjectByUsers(String order, Optional<Integer> page, Optional<Integer> size,String token);
}
