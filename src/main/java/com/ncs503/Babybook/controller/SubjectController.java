package com.ncs503.Babybook.controller;
import com.ncs503.Babybook.models.request.SubjectRequest;
import com.ncs503.Babybook.models.request.SubjectUpDateRequest;
import com.ncs503.Babybook.models.response.PaginationResponse;
import com.ncs503.Babybook.models.response.SubjectImageResponse;
import com.ncs503.Babybook.models.response.SubjectResponse;
import com.ncs503.Babybook.service.SubjectService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("subjects")
@Api(description ="Subjects CRUD" , tags = "Subjects")
@CrossOrigin(origins = "http://localhost:3000")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;


//    @PostMapping //FUNCIONA
    @PostMapping(consumes = {"*/*"})
    @ApiOperation(value = "Create subjects", notes = "Allows User to insert subjects")
    @ApiResponses({@ApiResponse(code = 201, message = "Subject created!")})
    public ResponseEntity<SubjectResponse> createSubject ( @RequestPart @Nullable MultipartFile image,

                                                           @ApiParam( name = "firstName", type = "String", example = "José" )
                                                           @RequestParam (required = false) String firstName,
                                                           @ApiParam( name = "lastName", type = "String",example = "Ibarrondo " )
                                                           @RequestParam (required = false) String lastName,
                                                           @ApiParam( name = "birthDate", type = "String", example = "2022-10-23" )
                                                           @RequestParam (required = false) String birthDate,
                                                           @ApiParam( name = "dni", type = "String", example = "23546789" )
                                                           @RequestParam (required = false) String dni,
                                                           @RequestHeader(name="Authorization") String token) throws IOException {


    SubjectRequest request = SubjectRequest.builder().image(image).firstName(firstName)
            .lastName(lastName).birthDate(LocalDate.parse(birthDate)).dni(dni).build();
    SubjectResponse responseCreate = this.subjectService.create(request, token);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseCreate);


    }


    @DeleteMapping("{id}")//FUNCIONA
    @ApiOperation(value = "Soft Delete subject By ID", notes = "Allows User to delete subject by ID")
    @ApiResponses({@ApiResponse(code = 204, message = "Subject deleted!"),
            @ApiResponse(code = 404, message = "The inserted ID does not belong to a business")})
    public ResponseEntity<Void> deleteSubject (@PathVariable @Valid @NotNull @NotBlank @ApiParam(
                                                name = "id",
                                                type = "Long",
                                                value = "ID of the subject requested",
                                                example = "1",
                                                required = true) Long id,
                                               @RequestHeader(name="Authorization") String token){



        this.subjectService.delete(id, token);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @PutMapping(value = "{id}", consumes = {"*/*"})//FUNCIONA
    @ApiOperation(value = "Update Data Subject By ID", notes = "Allows User to update data an existing Subject by ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Subject updated!"),
            @ApiResponse(code = 404, message = "The inserted ID does not belong to a subject")})
    public ResponseEntity<SubjectResponse> updateSubjectData (@PathVariable @Valid @NotNull @NotBlank  @ApiParam(
                                                            name = "id",
                                                            type = "Long",
                                                            value = "ID of the subject requested",
                                                            example = "1",
                                                            required = true) Long id,
                                                          @ApiParam( name = "firstName", type = "String", example = "José" )
                                                              @RequestParam (required = false) String firstName,
                                                          @ApiParam( name = "lastName", type = "String",example = "Ibarrondo " )
                                                              @RequestParam (required = false) String lastName,
                                                          @ApiParam( name = "birthDate", type = "String", example = "2022-10-23" )
                                                              @RequestParam (required = false) String birthDate,
                                                          @ApiParam( name = "dni", type = "String", example = "23546789" )
                                                              @RequestParam (required = false) String dni,
                                                          @RequestHeader(name="Authorization") String token) throws IOException {


       SubjectUpDateRequest request = SubjectUpDateRequest.builder().firstName(firstName)
                .lastName(lastName).dni(dni).birthDate(LocalDate.parse(birthDate)).build();



        SubjectResponse response = this.subjectService.update(id, request, token);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
    @PatchMapping(value = "{id}", consumes = {"*/*"})
    @ApiOperation(value = "Update Data Subject By ID", notes = "Allows User to update data an existing Subject by ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Subject updated!"),
            @ApiResponse(code = 404, message = "The inserted ID does not belong to a subject")})
    public ResponseEntity<SubjectImageResponse> updateSubjectImage(@PathVariable @Valid @NotNull @NotBlank  @ApiParam(
                                                                    name = "id",
                                                                    type = "Long",
                                                                    value = "ID of the subject requested",
                                                                    example = "1",
                                                                    required = true) Long id,
                                                                    @RequestPart(required = false) MultipartFile image,
                                                                    @RequestHeader(name="Authorization") String token) throws IOException{


        SubjectImageResponse response = this.subjectService.updateImage(id,image, token);

        return ResponseEntity.status(HttpStatus.OK).body(response);


    }

    @GetMapping("/getByUser")//FUNCIONA
    @ApiOperation(value = "Get Subject By User ID", notes = "Returns all the subject according to the User")
    @ApiResponses({@ApiResponse(code = 200, message = "Return the requested subjects"),
            @ApiResponse(code = 404, message = "The inserted ID does not belong to a user")})
    public ResponseEntity<?> getByUser( @RequestParam (required = false, defaultValue = "ASC") String order,
                                        @RequestParam(value = "page", required = false) Optional<Integer> page,
                                        @RequestParam(value = "size", required = false) Optional<Integer> size,
                                        @RequestHeader(name="Authorization") String token) {

        return new ResponseEntity<>(subjectService.getSubjectByUsers(order,page, size, token), HttpStatus.OK);
    }


    @GetMapping("/getByName/{firstName}")//FUNCIONA
    @ApiOperation(value = "Get Subject By name", notes = "Returns all the subject according to the name")
    @ApiResponses({@ApiResponse(code = 200, message = "Return the requested subjects"),
            @ApiResponse(code = 404, message = "The inserted NAME does not belong to a user")})
    public ResponseEntity <PaginationResponse> getByFilters (@PathVariable("firstName") @Valid @NotNull @NotBlank @ApiParam(name = "firstName",
                                                            type = "String",
                                                            value = "name of the Subject",
                                                            example = "Pepito") String firstName,
                                                             @RequestParam (required = false, defaultValue = "ASC") String order,
                                                             @RequestParam(value = "page", required = false)@ApiParam(
                                                                     name = "page",
                                                                     type = "Integer",
                                                                     value = "page number I want to see",
                                                                     example = "1")Optional<Integer> page,
                                                             @RequestParam(value = "size", required = false)@ApiParam(
                                                                     name = "size",
                                                                     type = "Integer",
                                                                     value = "number of items per page",
                                                                     example = "3") Optional<Integer> size,
                                                             @RequestHeader(name="Authorization") String token) {

        return new ResponseEntity<>(subjectService.getSubjectByName(firstName, order, page, size, token),
                HttpStatus.OK);

    }




}
