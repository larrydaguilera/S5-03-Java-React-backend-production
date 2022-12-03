package com.ncs503.Babybook.service.impl;

import com.ncs503.Babybook.auth.filter.JwtUtils;
import com.ncs503.Babybook.exception.RuntimeExceptionCustom;
import com.ncs503.Babybook.models.entity.SubjectEntity;
import com.ncs503.Babybook.models.entity.UserEntity;
import com.ncs503.Babybook.models.mapper.SubjectMapper;
import com.ncs503.Babybook.models.request.SubjectRequest;
import com.ncs503.Babybook.models.request.SubjectUpDateRequest;
import com.ncs503.Babybook.models.request.specification.SubjectByNameRequest;
import com.ncs503.Babybook.models.request.specification.SubjectByUserRequest;
import com.ncs503.Babybook.models.response.PaginationResponse;
import com.ncs503.Babybook.models.response.SubjectImageResponse;
import com.ncs503.Babybook.models.response.SubjectResponse;
import com.ncs503.Babybook.repository.SubjectRepository;
import com.ncs503.Babybook.repository.UserRepository;
import com.ncs503.Babybook.repository.specification.SubjectByNameSpecification;
import com.ncs503.Babybook.repository.specification.SubjectByUserSpecification;
import com.ncs503.Babybook.service.SubjectService;
import com.ncs503.Babybook.utils.PaginationByFiltersUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private SubjectMapper subjectMapper;


    @Autowired
    private JwtUtils jwtUtils;// TODO Verificar token

    @Autowired
    UserRepository userRepository;

    @Autowired
    SubjectByNameSpecification subjectByNameSpecification;

    @Autowired
    SubjectByUserSpecification subjectByUserSpecification;


    @Override
    public SubjectResponse create(SubjectRequest request, String token) throws IOException {
        token = token.substring(7);
        String username = jwtUtils.extractUsername(token);



        UserEntity userEntity = userRepository.findByEmail(username).get();

        Optional<SubjectEntity> subjectEntity = subjectRepository.findByName(request.getFirstName(), userEntity.getId());


        if (subjectEntity.isPresent()&&userEntity.getId().equals(subjectEntity.get().getUsers().getId())) {

            throw new RuntimeExceptionCustom("409 ::the subject already exists");

        }

        SubjectEntity entity = this.subjectMapper.Request2Entity(request, userEntity.getId());
        SubjectEntity entitySave = this.subjectRepository.save(entity);
        SubjectResponse responseCreated = this.subjectMapper.Entity2Response(entitySave);

        return responseCreated;
    }

    @Override
    public void delete(Long id, String token) {

        token = token.substring(7);
        String username = jwtUtils.extractUsername(token);
        UserEntity userEntity = userRepository.findByEmail(username).get();

        Optional<SubjectEntity> entity = this.subjectRepository.findById(id);
        SubjectEntity entity1= entity.get();



        if (!entity.isPresent()) {

            throw new RuntimeExceptionCustom("404 ::the id  does not belong to a subject");

        }
        if (userEntity.getId().equals(entity1.getUsers().getId())){

            this.subjectRepository.delete(entity.get());
        }else {
            throw new RuntimeExceptionCustom("404 ::the subject id does not belong to the user");
        }

    }

    @Override
    public SubjectResponse update(Long id, SubjectUpDateRequest request, String token) throws IOException {

        token = token.substring(7);
        String username = jwtUtils.extractUsername(token);
        UserEntity userEntity = userRepository.findByEmail(username).get();


        Optional<SubjectEntity> entity = this.subjectRepository.findById(id);
        SubjectEntity entity1= entity.get();

        if (!entity.isPresent()) {

            throw new RuntimeExceptionCustom("404 ::the id  does not belong to a subject");
        }

        if (userEntity.getId().equals(entity1.getUsers().getId())){

            SubjectEntity entityUpdate = this.subjectMapper.EntityRefreshDataValues(entity.get(), request);

            SubjectEntity entitySave = this.subjectRepository.save(entityUpdate);

            SubjectResponse response = this.subjectMapper.Entity2Response(entitySave);

            return response;

        }else {
            throw new RuntimeExceptionCustom("404 ::the subject id does not belong to the user");
        }

    }

    @Override
    public SubjectImageResponse updateImage(Long id, MultipartFile image, String token) throws IOException {
        token = token.substring(7);
        String username = jwtUtils.extractUsername(token);
        UserEntity userEntity = userRepository.findByEmail(username).get();


        Optional<SubjectEntity> entity = this.subjectRepository.findById(id);
        SubjectEntity entity1= entity.get();

        if (!entity.isPresent()) {

            throw new RuntimeExceptionCustom("404 ::the id  does not belong to a subject");
        }

        if (userEntity.getId().equals(entity1.getUsers().getId())){

            SubjectEntity entityUpdate = this.subjectMapper.EntityRefreshImageValue(entity.get(), image);

            SubjectEntity entitySave = this.subjectRepository.save(entityUpdate);

            SubjectImageResponse response = this.subjectMapper.Entity2ImageResponse(entitySave);

            return response;

        }else {
            throw new RuntimeExceptionCustom("404 ::the subject id does not belong to the user");
        }
    }

    @Override
    public SubjectResponse getById(Long id) throws IOException {

        Optional<SubjectEntity> entity = this.subjectRepository.findById(id);

        if (!entity.isPresent()) {

            throw new RuntimeExceptionCustom("404 ::the id  does not belong to a subject");
        }

        SubjectResponse response = this.subjectMapper.Entity2Response(entity.get());

        return response;
    }

    @Override
    public PaginationResponse getSubjectByName(String name, String order, Optional<Integer> pageNumber, Optional<Integer> size,
                                               String token) {
        token = token.substring(7);
        String username = jwtUtils.extractUsername(token);
        UserEntity userEntity = userRepository.findByEmail(username).get();

        Long userId = userEntity.getId();


        if (userEntity.getId()!=null){

            SubjectByNameRequest filtersRequest = new SubjectByNameRequest(name, order,userId);

            Specification<SubjectEntity> specification = subjectByNameSpecification.getByName(filtersRequest);

            PaginationByFiltersUtil pagination = new PaginationByFiltersUtil(specification, subjectRepository, pageNumber, size,
                    "/subjects/getByName?page=%d&size=%d");
            Page page = pagination.getPage();

            List<SubjectResponse> responses = page.getContent();


            return PaginationResponse.builder()
                    .entities(responses)
                    .nextPageURI(pagination.getNext())
                    .prevPageURI(pagination.getPrevious())
                    .build();

        }else {
            throw new RuntimeExceptionCustom("404 ::subject name does not belong to user");
        }



    }

    @Override
    public PaginationResponse getSubjectByUsers(String order, Optional<Integer> pageNumber, Optional<Integer> size, String token) {

        token = token.substring(7);
        String username = jwtUtils.extractUsername(token);
        UserEntity userEntity = userRepository.findByEmail(username).get();



        SubjectByUserRequest filtersRequest = new SubjectByUserRequest(userEntity.getId(), order);


        Specification<SubjectEntity> specification = subjectByUserSpecification.getByUsers(filtersRequest);


        PaginationByFiltersUtil pagination = new PaginationByFiltersUtil(specification, subjectRepository, pageNumber, size,
                "/subjects/getByUser?page=%d&size=%d");
        Page page = pagination.getPage();

        List<SubjectResponse> responses = page.getContent();
        return PaginationResponse.builder()
                .entities(responses)
                .nextPageURI(pagination.getNext())
                .prevPageURI(pagination.getPrevious())
                .build();
    }
}
