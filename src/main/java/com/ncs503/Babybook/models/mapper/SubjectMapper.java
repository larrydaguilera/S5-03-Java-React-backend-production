package com.ncs503.Babybook.models.mapper;

import com.ncs503.Babybook.models.entity.SubjectEntity;
import com.ncs503.Babybook.models.request.SubjectRequest;
import com.ncs503.Babybook.models.request.SubjectUpDateRequest;
import com.ncs503.Babybook.models.response.SubjectGuestResponse;
import com.ncs503.Babybook.models.response.SubjectImageResponse;
import com.ncs503.Babybook.models.response.SubjectResponse;
import com.ncs503.Babybook.repository.SubjectRepository;
import com.ncs503.Babybook.repository.UserRepository;
import com.ncs503.Babybook.service.AwsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@Component
public class SubjectMapper {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AwsService awsService;

    @Autowired
    SubjectRepository subjectRepository;


    public SubjectEntity Request2Entity (SubjectRequest request, Long userID) throws IOException {

        SubjectEntity entity = new SubjectEntity();

        if (request.getImage()!=null){

            entity.setImage(awsService.uploadFile(request.getImage()));
        }
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setBirthDate(request.getBirthDate());
        entity.setDni(request.getDni());
        entity.setUsers(userRepository.findById(userID).get());



        return entity;


    }

    public SubjectResponse Entity2Response (SubjectEntity entity) throws IOException {

        SubjectResponse response = SubjectResponse.builder().firstName(entity.getFirstName())
                .id(entity.getId())
                .image(entity.getImage())
                .lastName(entity.getLastName())
                .dni(entity.getDni())
                .birthDate(entity.getBirthDate())
                .idUser(entity.getUsers().getId())
                .createDate(entity.getTimestamp())
                .build();

        return response;

    }

    public SubjectEntity EntityRefreshDataValues (SubjectEntity entity, SubjectUpDateRequest request) throws IOException {

        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setBirthDate(request.getBirthDate());
        entity.setDni(request.getDni());
        entity.setTimestamp(new Timestamp(System.currentTimeMillis()));

        return entity;
    }

    public SubjectEntity EntityRefreshImageValue(SubjectEntity subjectEntity, MultipartFile image) throws IOException {

        subjectEntity.setImage(awsService.uploadFile(image));
        subjectEntity.setTimestamp(new Timestamp(System.currentTimeMillis()));

        return subjectEntity;
    }

    public SubjectImageResponse Entity2ImageResponse(SubjectEntity entitySave) {

        SubjectImageResponse response = SubjectImageResponse.builder().image(entitySave.getImage()).id(entitySave.getId()).build();

        return response;

    }

    public List<SubjectGuestResponse> EntityList2Response(List<SubjectEntity> entityList) throws IOException {
        List<SubjectGuestResponse> responses = new ArrayList<>();
        for ( SubjectEntity subject: entityList){
            responses.add(Entity2ResponseGuest(subject));
        }

        return responses;
    }

    public List<SubjectEntity> subjectRequestList2Entity(List<SubjectRequest> subjectRequestList, Long user_id) throws  IOException {
        List<SubjectEntity> responses = new ArrayList<>();
        subjectRequestList.forEach(subReq -> {
            try {
                responses.add(Request2Entity(subReq, user_id));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return responses;
    }

    private SubjectGuestResponse Entity2ResponseGuest(SubjectEntity entity) {

        SubjectGuestResponse response = SubjectGuestResponse.builder().firstName(entity.getFirstName())
                .id(entity.getId())
                .image(entity.getImage())
                .lastName(entity.getLastName())
                .birthDate(entity.getBirthDate())
                .idUser(entity.getUsers().getId())
                .build();

        return response;

    }

}





