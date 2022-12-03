package com.ncs503.Babybook.models.mapper;
import com.ncs503.Babybook.models.entity.MedicalDataEntity;
import com.ncs503.Babybook.models.entity.SubjectEntity;
import com.ncs503.Babybook.models.request.MedicalDataRequest;
import com.ncs503.Babybook.models.request.MedicalDataUpDateRequest;
import com.ncs503.Babybook.models.request.SubjectRequest;
import com.ncs503.Babybook.models.response.MedicalDataResponse;
import com.ncs503.Babybook.models.response.SubjectResponse;
import com.ncs503.Babybook.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class MedicalDataMapper {



    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    SubjectMapper subjectMapper;

    public MedicalDataEntity Request2Entity (MedicalDataRequest request) throws IOException {


        MedicalDataEntity entity = MedicalDataEntity.builder().bloodType(request.getBloodType())
                .alergies(request.getAlergies())
                .relevantInfo(request.getRelevantInfo())
                .subject(this.subjectRepository.getById(request.getSubject()))
                .build();


        return entity;

    }

    public MedicalDataResponse Entity2Response (MedicalDataEntity entity) throws IOException {



        MedicalDataResponse response = MedicalDataResponse.builder().bloodType(entity.getBloodType())
                .id(entity.getId())
                .alergies(entity.getAlergies())
                .relevantInfo(entity.getRelevantInfo())
                .subject(entity.getSubject().getId())
                .createDate(entity.getTimestamp())
                .build();

        return response;
    }

    public MedicalDataEntity EntityRefreshValues (MedicalDataEntity entity, MedicalDataUpDateRequest request) throws IOException {

        entity.setBloodType(request.getBloodType());
        entity.setAlergies(request.getAlergies());
        entity.setRelevantInfo(request.getRelevantInfo());
        entity.setTimestamp(new Timestamp(System.currentTimeMillis()));

        return entity;
    }


    public List<MedicalDataResponse> EntityList2Response(List<MedicalDataEntity> entities) throws IOException {
        List<MedicalDataResponse> responses = new ArrayList<>();
        for ( MedicalDataEntity medicals: entities){
            responses.add(Entity2Response(medicals));
        }

        return responses;
    }

}
