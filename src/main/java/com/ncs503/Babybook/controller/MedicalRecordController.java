package com.ncs503.Babybook.controller;

import com.ncs503.Babybook.models.response.EventResponse;
import com.ncs503.Babybook.models.response.MedicalRecordResponse;
import com.ncs503.Babybook.models.response.MedicalRecordResponse;
import com.ncs503.Babybook.models.utility.TagsEventEnum;
import com.ncs503.Babybook.models.utility.TagsMedicalRecordEnum;
import com.ncs503.Babybook.service.MedicalRecordService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("medicalRecord")
@Api(description ="MedicalRecords CRUD" , tags = "MedicalRecord")
@CrossOrigin(origins = "http://localhost:3000")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @PostMapping("/create")
    @ApiOperation(value = "Create MedicalRecords", notes = "method that creates MedicalRecords")
    @ApiResponses({@ApiResponse(code = 201, message = "MedicalRecords created!")})
    public ResponseEntity<MedicalRecordResponse> create(@Valid
//                                                            @RequestHeader(name="Authorization") String token,

                                                        @ApiParam( name = "title", type = "String",
                                                        example = "Estudio Medico" ) @RequestParam (required = false) String title,
                                                        @ApiParam( name = "bodie", type = "String",
                                                        example = "control de rutina hombro" ) @RequestParam (required = false) String bodie,
                                                        @ApiParam( name = "date", type = "String",
                                                        example = "2022-10-23" ) @RequestParam (required = false) String date,
                                                        @RequestPart (required = false) List<MultipartFile> media,
                                                        @ApiParam( name = "medicalDataId", type = "Long",
                                                        example = "1" ) @RequestParam Long medicalDataId,
                                                        @ApiParam( name = "MedicalRecordEnum", type = "TagsMedicalRecordEnum",
                                                        example = "ESTUDIOS MEDICOS" ) @RequestParam (required = false) TagsMedicalRecordEnum MedicalRecordEnum
                                                                                                ) throws Exception {

        String token = "aaa";
        System.out.println("\nmedia controller : " + media);

        LocalDate date1 = LocalDate.parse(date);
        MedicalRecordResponse response = this.medicalRecordService.create(token, title, bodie, date1, media, medicalDataId, MedicalRecordEnum);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/update")
    @ApiOperation(value = "update MedicalRecords", notes = "method that updates MedicalRecords by id")
    @ApiResponses({@ApiResponse(code = 200, message = "MedicalRecords modificated!")})
    public ResponseEntity<MedicalRecordResponse> update(@Valid
//                                                            @RequestHeader(name="Authorization") String token,

                                                        @ApiParam( name = "medicalRecordId", type = "Long",
                                                                example = "1" ) @RequestParam (required = false) Long medicalRecordId,
                                                        @ApiParam( name = "title", type = "String",
                                                                example = "Estudio Medico" ) @RequestParam (required = false) String title,
                                                        @ApiParam( name = "bodie", type = "String",
                                                                example = "control de rutina hombro" ) @RequestParam (required = false) String bodie,
                                                        @ApiParam( name = "date", type = "String",
                                                                example = "2022-10-23" ) @RequestParam (required = false) String date,
                                                        @RequestPart (required = false) List<MultipartFile> media,
                                                        @ApiParam( name = "medicalDataId", type = "Long",
                                                                example = "1" ) @RequestParam Long medicalDataId,
                                                        @ApiParam( name = "MedicalRecordEnum", type = "TagsMedicalRecordEnum",
                                                                example = "ESTUDIOS MEDICOS" ) @RequestParam (required = false) TagsMedicalRecordEnum MedicalRecordEnum
                                                                                            ) throws Exception {

        String token = "aaa";

        LocalDate date1 = LocalDate.parse(date);
        MedicalRecordResponse response = this.medicalRecordService.update(token, medicalRecordId, title, bodie, date1, media, medicalDataId, MedicalRecordEnum);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @DeleteMapping("/delete")
    @ApiOperation(value = "Delete medicalRecords", notes = "method that deleted the medicalRecords by id")
    @ApiResponses({@ApiResponse(code = 202, message = "medicalRecords deleted!")})
    public ResponseEntity<MedicalRecordResponse> delete(@Valid
//                                                @RequestHeader(name="Authorization") String token,
                                                @ApiParam( name = "medicalDataId", type = "Long",
                                                        example = "1" ) @RequestParam (required = false) Long medicalDataId,
                                                @ApiParam( name = "medicalRecordId", type = "Long",
                                                        example = "1" ) @RequestParam (required = false) Long medicalRecordId
    )  throws IOException {
        String token = "aaa";

        medicalRecordService.delete(token, medicalDataId, medicalRecordId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @GetMapping("/findById")
    @ApiOperation(value = "findById medicalRecords", notes = "method that shows the medicalRecords by id")
    @ApiResponses({@ApiResponse(code = 200, message = "medicalRecords modificated!")})
    public ResponseEntity<MedicalRecordResponse> findById(@Valid
//                                                @RequestHeader(name="Authorization") String token,
                                                  @ApiParam( name = "medicalDataId", type = "Long",
                                                          example = "1" ) @RequestParam (required = false) Long medicalDataId,
                                                  @ApiParam( name = "medicalRecordId", type = "Long",
                                                          example = "1" ) @RequestParam (required = false) Long medicalRecordId
    ) throws Exception {
        String token = "aaa";

        MedicalRecordResponse response = medicalRecordService.findById(token, medicalDataId, medicalRecordId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/findAllByDate")
    @ApiOperation(value = "findById medicalRecords", notes = "method that shows the medicalRecords by id")
    @ApiResponses({@ApiResponse(code = 200, message = "medicalRecords modificated!")})
    public ResponseEntity<List<MedicalRecordResponse>> findAllByDate(@Valid
//                                                @RequestHeader(name="Authorization") String token,
                                                          @ApiParam( name = "medicalDataId", type = "Long",
                                                                  example = "1" ) @RequestParam (required = false) Long medicalDataId,
                                                          @ApiParam( name = "date", type = "String",
                                                                  example = "2022-10-23" ) @RequestParam (required = false) String date
    ) throws Exception {

        String token = "aaa";
        LocalDate date1 = LocalDate.parse(date);
        List<MedicalRecordResponse> response = medicalRecordService.findAllByDate(token, medicalDataId, date1);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
    @GetMapping("/findAllByTags")
    @ApiOperation(value = "findAllByTags medicalRecords", notes = "method that shows the medicalRecords by tags")
    @ApiResponses({@ApiResponse(code = 200, message = "medicalRecords modificated!")})
    public ResponseEntity<List<MedicalRecordResponse>> findAllByTags(@Valid
//                                                  @RequestHeader(name="Authorization") String token,
                                                             @ApiParam( name = "medicalDataId", type = "Long",
                                                                     example = "1" ) @RequestParam Long medicalDataId,
                                                             @ApiParam( name = "medicalRecordEnum", type = "TagsMedicalRecordEnum",
                                                                     example = "CRECIMIENTO" ) @RequestParam (required = false) TagsMedicalRecordEnum medicalRecordEnum
    ) throws Exception {

        String token = "aaa";

        List<MedicalRecordResponse> response = medicalRecordService.findAllByTags(token, medicalDataId, medicalRecordEnum);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }



}
