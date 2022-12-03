package com.ncs503.Babybook.models.response;

import com.ncs503.Babybook.models.entity.SubjectEntity;
import com.ncs503.Babybook.models.entity.UserEntity;
import com.ncs503.Babybook.models.utility.TagsEventEnum;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventResponse {

    private Long id;
    private String title;
    private String body;
    private LocalDate date;
    private List<String> media;
    private Timestamp timestamp;
    private SubjectEntity subjectId;
    private TagsEventEnum eventEnum;
    private UserEntity userId;

}
