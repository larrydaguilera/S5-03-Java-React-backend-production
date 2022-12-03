package com.ncs503.Babybook.service.impl;

import com.ncs503.Babybook.auth.filter.JwtUtils;
import com.ncs503.Babybook.exception.RuntimeExceptionCustom;
import com.ncs503.Babybook.models.entity.MedicalDataEntity;
import com.ncs503.Babybook.models.entity.SubjectEntity;
import com.ncs503.Babybook.models.entity.UserEntity;
import com.ncs503.Babybook.models.mapper.MedicalDataMapper;
import com.ncs503.Babybook.models.request.MedicalDataRequest;
import com.ncs503.Babybook.models.request.MedicalDataUpDateRequest;
import com.ncs503.Babybook.models.response.MedicalDataResponse;
import com.ncs503.Babybook.models.response.SubjectResponse;
import com.ncs503.Babybook.repository.MedicalDataRepository;
import com.ncs503.Babybook.repository.SubjectRepository;
import com.ncs503.Babybook.repository.UserRepository;
import com.ncs503.Babybook.service.MedicalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MedicalDataServiceImpl implements MedicalDataService {

    @Autowired
    MedicalDataMapper medicalDataMapper;

    @Autowired
    MedicalDataRepository medicalDataRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Override
    public MedicalDataResponse create(MedicalDataRequest request, String token) throws IOException {

        token = token.substring(7);
        String username = jwtUtils.extractUsername(token);

        UserEntity userEntity = userRepository.findByEmail(username).get();

        Optional<SubjectEntity> subjectEntity = subjectRepository.findById(request.getSubject());

        Optional<MedicalDataEntity> medicalDataEntity = medicalDataRepository.findBySubjectId(subjectEntity.get().getId());

       if (!userEntity.getId().equals(subjectEntity.get().getUsers().getId())){
           throw new RuntimeExceptionCustom("409 ::the subject id does not belong to the user");
       }

       if (medicalDataEntity.isPresent()){

           throw new RuntimeExceptionCustom("409 ::the subject already has a medical data");
       }

       MedicalDataEntity entity = this.medicalDataMapper.Request2Entity(request);
       MedicalDataEntity entitySave = this.medicalDataRepository.save(entity);
       MedicalDataResponse responseCreated = this.medicalDataMapper.Entity2Response(entitySave);

        return responseCreated;
    }

    @Override
    public void delete(Long id, String token) {

        token = token.substring(7);
        String username = jwtUtils.extractUsername(token);

        UserEntity userEntity = userRepository.findByEmail(username).get();

        Optional<SubjectEntity> subjectEntity = subjectRepository.findById(id);

        if (!userEntity.getId().equals(subjectEntity.get().getUsers().getId())){
            throw new RuntimeExceptionCustom("409 ::the subject id does not belong to the user");
        }

        Optional<MedicalDataEntity> entity = this.medicalDataRepository.findBySubjectId(id);

        if (!entity.isPresent()) {

            throw new RuntimeExceptionCustom("404 ::the id  does not belong to a Subject");

        }

        this.medicalDataRepository.deleteById(entity.get().getId());
    }

    @Override
    public MedicalDataResponse update(Long id, MedicalDataUpDateRequest request, String token) throws IOException {

        token = token.substring(7);
        String username = jwtUtils.extractUsername(token);

        UserEntity userEntity = userRepository.findByEmail(username).get();

        Optional<SubjectEntity> subjectEntity = subjectRepository.findById(id);

        if (!userEntity.getId().equals(subjectEntity.get().getUsers().getId())){
            throw new RuntimeExceptionCustom("409 ::the subject id does not belong to the user");
        }


        Optional<MedicalDataEntity> entity = this.medicalDataRepository.findBySubjectId(id);

        if (!entity.isPresent()) {

            throw new RuntimeExceptionCustom("404 ::the id  does not belong to a Subject");
        }

        MedicalDataEntity entityUpdate = this.medicalDataMapper.EntityRefreshValues(entity.get(), request);

        MedicalDataEntity entitySave = this.medicalDataRepository.save(entityUpdate);

        MedicalDataResponse response = this.medicalDataMapper.Entity2Response(entitySave);

        return response;
    }

    @Override
    public MedicalDataResponse getById(Long id,String token) throws IOException {

        token = token.substring(7);
        String username = jwtUtils.extractUsername(token);

        UserEntity userEntity = userRepository.findByEmail(username).get();

        Optional<SubjectEntity> subjectEntity = subjectRepository.findById(id);

        if (!userEntity.getId().equals(subjectEntity.get().getUsers().getId())){
            throw new RuntimeExceptionCustom("409 ::the subject id does not belong to the user");
        }


        Optional<MedicalDataEntity> entity = this.medicalDataRepository.findBySubjectId(id);

        if (!entity.isPresent()) {

            throw new RuntimeExceptionCustom("404 ::the id  does not belong to aMedical Data");
        }

        MedicalDataResponse response = this.medicalDataMapper.Entity2Response(entity.get());

        return response;
    }


}
