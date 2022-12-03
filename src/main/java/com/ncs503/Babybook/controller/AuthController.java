package com.ncs503.Babybook.controller;

import com.ncs503.Babybook.exception.GuestNotFoundException;
import com.ncs503.Babybook.exception.InvalidUserException;
import com.ncs503.Babybook.exception.UserNotFoundException;
import com.ncs503.Babybook.exception.UserProfileAlreadyExistsException;
import com.ncs503.Babybook.models.entity.UserEntity;
import com.ncs503.Babybook.models.mapper.UserMapper;
import com.ncs503.Babybook.models.request.LoginRequest;
import com.ncs503.Babybook.models.request.UserRequest;
import com.ncs503.Babybook.models.response.LoginResponse;
import com.ncs503.Babybook.models.response.UserResponse;
import com.ncs503.Babybook.repository.UserRepository;
import com.ncs503.Babybook.service.AuthService;
import io.swagger.annotations.*;
import jdk.jfr.Registered;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(path = "/auth")
@Api(description = "Authentication controller", tags = "Auth")
public class AuthController {

    @Autowired
    AuthService authServ;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    @ApiOperation(value = "Login an user",
            response = LoginResponse.class)
    @ApiResponse(code = 200, message = "OK")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) throws UserNotFoundException {
        return ResponseEntity.ok(authServ.login(request));
    }



    @PostMapping("/register")
    @ApiOperation(value = "Register an user", response = LoginResponse.class, notes = "Endpoint used to register an user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Register an user"),
            @ApiResponse(code = 403, message = "Something went wrong")

    })
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest userReq) throws InvalidUserException, UserProfileAlreadyExistsException, UserNotFoundException, GuestNotFoundException, IOException {

        return new ResponseEntity<>(authServ.saveUser(userReq), HttpStatus.OK);

    }

    @PostMapping("/su/register")
    @ApiOperation(value = "Register an admin user",
            response = LoginResponse.class, notes = "Endpoint used to register an adminuser")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Register an admin user"),
            @ApiResponse(code = 403, message = "Something went wrong")

    })
    public ResponseEntity<UserResponse> registerAdmin(@RequestBody UserRequest userReq) throws InvalidUserException, UserProfileAlreadyExistsException, UserNotFoundException, GuestNotFoundException, IOException {
        return new ResponseEntity<>(authServ.saveAdminUser(userReq), HttpStatus.OK);
    }



    @PostMapping("/guestRegister")
    @ApiOperation(value = "Register an guest", response = LoginResponse.class, notes = "Endpoint used to register an guest user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Register a guest user"),
            @ApiResponse(code = 403, message = "Something went wrong")

    })
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest userReq,
                                                     @ApiParam(name= "wantsToBeUserToo",
                                                                type = "Boolean",
                                                                example = "true",
                                                                value = "Points if the guest user wants to be an user too")
                                                     @RequestParam Boolean wantsToBeUserToo) throws InvalidUserException, UserProfileAlreadyExistsException, UserNotFoundException, GuestNotFoundException, IOException{
        return new ResponseEntity<>(authServ.saveGuestUser(userReq, wantsToBeUserToo), HttpStatus.OK);

    }


}
