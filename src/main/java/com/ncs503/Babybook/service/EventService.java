package com.ncs503.Babybook.service;

import com.ncs503.Babybook.models.response.EventResponse;
import com.ncs503.Babybook.models.utility.TagsEventEnum;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface EventService {

    public EventResponse create(String token,String title, String body, LocalDate date,
                                List<MultipartFile> media, Long subjectId, TagsEventEnum eventEnum) throws Exception;
    public EventResponse update(String token, Long eventId, String title, String body, LocalDate date,
                                List<MultipartFile> media, Long subjectId, TagsEventEnum eventEnum) throws Exception;
    public void delete(String token, Long subjectId, Long eventId) throws Exception;
    public EventResponse findById(String token, Long subjectId, Long eventId) throws Exception;
    public List<EventResponse> findAllByDate(String token, Long subjectId, LocalDate date) throws Exception;
    public List<EventResponse> findAllByTags(String token, Long subjectId, TagsEventEnum eventEnum) throws Exception;
    public List<EventResponse> findByIdSubject(String token, Long subjectId) throws Exception;

}
