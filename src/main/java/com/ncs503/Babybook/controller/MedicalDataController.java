package com.ncs503.Babybook.controller;
import com.ncs503.Babybook.models.request.MedicalDataRequest;
import com.ncs503.Babybook.models.request.MedicalDataUpDateRequest;
import com.ncs503.Babybook.models.request.SubjectRequest;
import com.ncs503.Babybook.models.response.MedicalDataResponse;
import com.ncs503.Babybook.models.response.SubjectResponse;
import com.ncs503.Babybook.service.MedicalDataService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("medicals")
@Api(description ="Medical Data CRUD" , tags = "Medicals")
@CrossOrigin(origins = "http://localhost:3000")
public class MedicalDataController {

    @Autowired
    MedicalDataService medicalDataService;

    @PostMapping //FUNCIONA
    @ApiOperation(value = "Create Medical Data", notes = "Medical Data belonging to the subject")
    @ApiResponses({@ApiResponse(code = 201, message = "Medical Data created!")})
    public ResponseEntity<MedicalDataResponse> createMedicalData (@Valid @RequestBody MedicalDataRequest request,
                                                                  @RequestHeader(name="Authorization") String token) throws IOException {

        MedicalDataResponse responseCreate = this.medicalDataService.create(request, token);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseCreate);
    }


    @DeleteMapping("{id}")//FUNCIONA
    @ApiOperation(value = "Soft Delete Medical Data By Subject ID", notes = "Allows User to delete Medical Data by ID of subject")
    @ApiResponses({@ApiResponse(code = 204, message = "Medical Data deleted!"),
            @ApiResponse(code = 404, message = "The inserted ID does not belong to a subject")})
    public ResponseEntity<Void> deleteMedicalDataBySubject (@PathVariable @Valid @NotNull @NotBlank @ApiParam(
                                                            name = "id",
                                                            type = "Long",
                                                            value = "ID of the subject requested",
                                                            example = "1",
                                                            required = true) Long id,
                                                            @RequestHeader(name="Authorization") String token){

        this.medicalDataService.delete(id,token);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @PutMapping("{id}")//FUNCIONA
    @ApiOperation(value = "Update Medical Data By Subject ID", notes = "Allows User to update Medical Data by ID of subject")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Medical Data updated!"),
            @ApiResponse(code = 404, message = "The inserted ID does not belong to a subject")})
    public ResponseEntity<MedicalDataResponse> updateMedicalDataBySubject (@PathVariable @Valid @NotNull @NotBlank  @ApiParam(
                                                                             name = "id",
                                                                             type = "Long",
                                                                             value = "ID of the subject requested",
                                                                             example = "1",
                                                                             required = true) Long id,
                                                                             @Valid @RequestBody @ApiParam(
                                                                             name = "Medical Data",
                                                                             value = "Medical Data to save",
                                                                             required = true) MedicalDataUpDateRequest request,
                                                                           @RequestHeader(name="Authorization") String token) throws IOException {

       MedicalDataResponse response = this.medicalDataService.update(id, request, token);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("{id}")//FUNCIONA
    @ApiOperation(value = "Get Medical Data By Subject ID", notes = "Returns all details of Medical Data by Subject ID")
    @ApiResponses({@ApiResponse(code = 200, message = "Return the requested Medical Data"),
            @ApiResponse(code = 404, message = "The inserted ID does not belong to a Subject")})
    public ResponseEntity<MedicalDataResponse> getBySubjectId (@PathVariable @Valid @NotNull @NotBlank @ApiParam(
                                                                name = "id",
                                                                type = "Long",
                                                                value = "ID of the Subject requested",
                                                                example = "1",
                                                                required = true) Long id,
                                                               @RequestHeader(name="Authorization") String token) throws IOException {

        MedicalDataResponse response = this.medicalDataService.getById(id, token);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


}
