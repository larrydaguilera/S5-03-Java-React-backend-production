package com.ncs503.Babybook.repository;

import com.ncs503.Babybook.models.entity.EventEntity;
import com.ncs503.Babybook.models.entity.MedicalRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecordEntity, Long> {

    @Query(value =  "SELECT * FROM medicalrecords m WHERE m.subject_id = ? AND m.date = ?", nativeQuery = true)
    List<MedicalRecordEntity> findAllByDate(Long subject_id, LocalDate date);
    @Query(value =  "SELECT * FROM medicalrecords m WHERE m.subject_id = ? AND m.medical_record_enums = ?", nativeQuery = true)
    List<MedicalRecordEntity> findAllByTags(Long subject_id, int medicalRecordEnum);


}
