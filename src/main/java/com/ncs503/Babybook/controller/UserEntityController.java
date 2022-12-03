
package com.ncs503.Babybook.controller;

import com.ncs503.Babybook.exception.GuestNotFoundException;
import com.ncs503.Babybook.exception.InvalidUserException;
import com.ncs503.Babybook.exception.UserNotFoundException;
import com.ncs503.Babybook.models.request.UpdateUserRequest;
import com.ncs503.Babybook.models.request.UserRequest;
import com.ncs503.Babybook.models.response.UserResponse;
import com.ncs503.Babybook.service.UserService;
import io.swagger.annotations.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Leonardo Terlizzi
 */
@RestController
@RequestMapping(path = "/user")
@Api(description = "User CRUD" , tags = "Users")
public class UserEntityController {

    @Autowired
    private UserService userServ;


    @GetMapping("/all")
    @ApiOperation(value = "List all the users", notes = "Endpoint that return a list of users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Users's list"),
            @ApiResponse(code = 403, message = "Forbidden action")
    })
    public ResponseEntity<List<UserResponse>> getUsers() throws UserNotFoundException {
        List<UserResponse> users = userServ.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/getPagination")
    @ApiOperation(value = "List users by page",notes ="Endpoint that returns a list of users using pagination" )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User's list by page"),
            @ApiResponse(code = 403, message = "Forbidden action")
    })
    public ResponseEntity<?> getUsersPagination(@RequestParam Optional<Integer> page,
                                                @RequestParam Optional<Integer> size) throws UserNotFoundException{
        return new ResponseEntity<>(userServ.getUsersByPage(page, size), HttpStatus.OK);
    }

    @GetMapping("/getById")
    @ApiOperation(value = "Get an user", notes = "Endpoint that return an user by the info inside the token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User information"),
            @ApiResponse(code = 403, message = "Forbidden action")
    })
    public ResponseEntity<UserResponse> getUser(@RequestHeader(name="Authorization") String token,
                                                @ApiParam(
                                                        name= "id",
                                                        type = "Long",
                                                        example = "1325",
                                                        value = "Id of the user"
                                                )
                                                @RequestParam Long id
                                                ) throws UserNotFoundException, InvalidUserException, GuestNotFoundException, IOException {
        return new ResponseEntity<>(userServ.getUser(token, id), HttpStatus.OK);

    }




    @DeleteMapping("/delete")
    @ApiOperation(value = "Delete an User", notes = "Endpoint that allows an user to delete himself/herself")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User deleted"),
            @ApiResponse(code = 403, message = "Forbbiden action")
    })
    public ResponseEntity<Void> deleteUser(@ApiParam(name= "id",type = "Long",example = "1325",
                                                        value = "Id of the user")
                                                @RequestParam Long id,
                                                @RequestHeader(name="Authorization") String token)
            throws UserNotFoundException, InvalidUserException {
        userServ.deleteUser(id, token);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/su/delete")
    @ApiOperation(value = "Delete an User(admin)", notes ="Endpoint that allows an admin to delete an user")
    @ApiResponses(value = {
            @ApiResponse( code = 200, message = "User deleted"),
            @ApiResponse( code = 403, message = "Forbbiden action")
    })
    public ResponseEntity<Void> adminDeleteUser(@RequestHeader(name="Authorizaation") String token,
                                                @ApiParam(name= "id",type = "Long",example = "1325",
                                                        value = "Id of the user")
                                                @RequestParam Long id)
            throws UserNotFoundException, InvalidUserException {
        userServ.deleteUser(id, token);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @PatchMapping("/update")
    @ApiOperation(value = "Update an User", notes ="Endpoint that allows to update user's information")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User updated"),
            @ApiResponse(code = 403, message = "Forbbiden action")
    })
    public ResponseEntity<UserResponse> editUser(@RequestHeader(name="Authorization") String token,
                                                    @RequestBody UpdateUserRequest userReq,
                                                    @ApiParam(name= "id",type = "Long",example = "1325",
                                                         value = "Id of the user")
                                                    @RequestParam Long id)
            throws InvalidUserException, UserNotFoundException, GuestNotFoundException, IOException {

        return new ResponseEntity<>(userServ.updateUser(userReq, id, token), HttpStatus.OK);

    }

    @PatchMapping("/su/update")
    @ApiOperation(value = "Update an User(admin)", notes ="Endpoint that allows an admin to update an user's information")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User updated"),
            @ApiResponse(code = 403, message = "Forbidden action")
    })
    public ResponseEntity<UserResponse> adminEditUser(@RequestBody UpdateUserRequest userReq,
                                                      @RequestHeader(name="Authorization") String token,
                                                      @ApiParam(name= "id",type = "Long",example = "1325",
                                                              value = "Id of the user")
                                                      @RequestParam Long id)
            throws InvalidUserException, UserNotFoundException, GuestNotFoundException, IOException {
        return new ResponseEntity<>(userServ.updateUser(userReq, id, token), HttpStatus.OK);

    }


}
