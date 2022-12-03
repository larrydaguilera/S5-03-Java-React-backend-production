package com.ncs503.Babybook.repository;

import com.ncs503.Babybook.models.entity.EventEntity;
import com.ncs503.Babybook.models.utility.TagsEventEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
    @Query(value =  "SELECT * FROM events e WHERE e.subject_id = ? AND e.date = ?", nativeQuery = true)
    List<EventEntity> findAllByDate(Long subject_id, LocalDate date);
    @Query(value =  "SELECT * FROM events e WHERE e.subject_id = ? AND e.event_enum = ?", nativeQuery = true)
    List<EventEntity> findAllByTags(Long subject_id, int eventEnum);
    @Query(value =  "SELECT * FROM events e WHERE e.subject_id = ? ", nativeQuery = true)
    List<EventEntity> findAllBySubject(Long subject_id);
}
