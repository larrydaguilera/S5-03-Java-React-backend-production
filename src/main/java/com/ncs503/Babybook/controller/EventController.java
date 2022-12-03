package com.ncs503.Babybook.controller;

import com.ncs503.Babybook.models.response.EventResponse;
import com.ncs503.Babybook.models.utility.TagsEventEnum;
import com.ncs503.Babybook.service.EventService;
import io.swagger.annotations.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("events")
@Api(description ="Events CRUD" , tags = "Events")
@CrossOrigin(origins = "http://localhost:3000")
public class EventController {

    @Autowired
    private EventService eventService;

    @Transactional
//    @PostMapping("/create")
    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE })

    @ApiOperation(value = "Create Events", notes = "method that creates events")
    @ApiResponses({@ApiResponse(code = 201, message = "Events created!")})
    public ResponseEntity<EventResponse> create(@Valid
                                                @RequestHeader(name="Authorization") String token,

                                                @RequestPart(required = false) List<MultipartFile> media,
//                                                @PathVariable (required = false) List<MultipartFile> media,
//                                                @RequestParam (required = false) List<MultipartFile> media,

                                                @ApiParam( name = "title", type = "String", example = "Primer dia de Jardin" )
                                                    @RequestParam (required = false) String title,
                                                @ApiParam( name = "bodie", type = "String",example = "ajajajajajajajjajaja " )
                                                    @RequestParam (required = false) String bodie,
                                                @ApiParam( name = "date", type = "String", example = "2022-10-23" )
                                                    @RequestParam (required = false) String date,
                                                @ApiParam( name = "subjectId", type = "Long", example = "1" )
                                                    @RequestParam Long subjectId,
                                                @ApiParam( name = "eventEnum", type = "TagsEventEnum", example = "CUMPLEAÑOS" )
                                                    @RequestParam (required = false) TagsEventEnum eventEnum
                                                                                            ) throws Exception {

        LocalDate date1 = LocalDate.parse(date);

        System.out.println("\n media controller : " + media);

        EventResponse response = eventService.create(token, title, bodie, date1, media, subjectId, eventEnum);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @Transactional
    @PutMapping("/update")
    @ApiOperation(value = "update Events", notes = "method that updates events by id")
    @ApiResponses({@ApiResponse(code = 200, message = "Events modificated!")})
    public ResponseEntity<EventResponse> update(@Valid
                                                @RequestHeader(name="Authorization") String token,
                                                @RequestPart (required = false) List<MultipartFile> media,
                                                @ApiParam( name = "eventId", type = "Long",
                                                        example = "1" ) @RequestParam (required = false) Long eventId,
                                                @ApiParam( name = "title", type = "String",
                                                        example = "comienzo de clases" ) @RequestParam (required = false) String title,
                                                @ApiParam( name = "bodie", type = "String",
                                                        example = "primer dia de Secundaria " ) @RequestParam (required = false) String bodie,
                                                @ApiParam( name = "date", type = "String",
                                                        example = "2022-10-23" ) @RequestParam (required = false) String date,
                                                @ApiParam( name = "subjectId", type = "Long",
                                                           example = "1" ) @RequestParam Long subjectId,
                                                @ApiParam( name = "eventEnum", type = "TagsEventEnum",
                                                        example = "CUMPLEAÑOS" ) @RequestParam TagsEventEnum eventEnum
                                                                                         ) throws Exception {

        LocalDate date1 = LocalDate.parse(date);

        EventResponse response = eventService.update(token, eventId, title, bodie, date1, media, subjectId, eventEnum);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @Transactional
    @DeleteMapping("/delete")
    @ApiOperation(value = "Delete Events", notes = "method that deleted the events by id")
    @ApiResponses({@ApiResponse(code = 204, message = "Events deleted!")})
    public ResponseEntity<EventResponse> delete(@Valid
                                                @RequestHeader(name="Authorization") String token,
                                                @ApiParam( name = "subjectId", type = "Long",
                                                          example = "1" ) @RequestParam (required = false) Long subjectId,
                                                @ApiParam( name = "eventId", type = "Long",
                                                          example = "1" ) @RequestParam (required = false) Long eventId
                                                                                ) throws Exception {

        eventService.delete(token, subjectId, eventId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @GetMapping("/findById")
    @ApiOperation(value = "findById Events", notes = "method that shows the events by id")
    @ApiResponses({@ApiResponse(code = 200, message = "Events modificated!")})
    public ResponseEntity<EventResponse> findById(@Valid @RequestHeader(name="Authorization") String token,
                                                  @ApiParam( name = "subjectId", type = "Long",
                                                          example = "1" ) @RequestParam (required = false) Long subjectId,
                                                  @ApiParam( name = "eventId", type = "Long",
                                                          example = "1" ) @RequestParam (required = false) Long eventId
                                                                                    ) throws Exception {

        EventResponse response = eventService.findById(token, subjectId, eventId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/findByIdSubject")
    @ApiOperation(value = "findById Subjects", notes = "method that shows the events by id")
    @ApiResponses({@ApiResponse(code = 200, message = "Events modificated!")})
    public ResponseEntity<List<EventResponse>> findByIdSubject(@Valid @RequestHeader(name="Authorization") String token,
                                                  @ApiParam( name = "subjectId", type = "Long",
                                                          example = "1" ) @RequestParam (required = false) Long subjectId
                                                     ) throws Exception {

        List<EventResponse> responses = eventService.findByIdSubject(token, subjectId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);

    }

    @GetMapping("/findAllByDate")
    @ApiOperation(value = "findAllByDate Events", notes = "method that shows the events by id")
    @ApiResponses({@ApiResponse(code = 200, message = "Events modificated!")})
    public ResponseEntity<List<EventResponse>> findAllByDate(@Valid @RequestHeader(name="Authorization") String token,
                                                  @ApiParam( name = "subjectId", type = "Long",
                                                      example = "1" ) @RequestParam (required = false) Long subjectId,
                                                  @ApiParam( name = "date", type = "String",
                                                      example = "2022-10-23" ) @RequestParam (required = false, defaultValue = "${new java.util.Date()}") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.util.Date date
                                                                                  ) throws Exception {

        List<EventResponse> response = eventService.findAllByDate(token, subjectId, date.toInstant()
                                                                            .atZone(ZoneId.systemDefault())
                                                                            .toLocalDate());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
    @GetMapping("/findAllByTags")
    @ApiOperation(value = "findAllByTags Events", notes = "method that shows the events by tags")
    @ApiResponses({@ApiResponse(code = 200, message = "Events modificated!")})
    public ResponseEntity<List<EventResponse>> findAllByTags(@Valid
                                                                 @RequestHeader(name="Authorization") String token,
                                                  @ApiParam( name = "subjectId", type = "Long",
                                                        example = "1" ) @RequestParam Long subjectId,
                                                  @ApiParam( name = "eventEnum", type = "TagsEventEnum",
                                                        example = "CRECIMIENTO" ) @RequestParam (required = false) TagsEventEnum eventEnum
                                                                                 ) throws Exception {

        List<EventResponse> response = eventService.findAllByTags(token, subjectId, eventEnum);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }



}
