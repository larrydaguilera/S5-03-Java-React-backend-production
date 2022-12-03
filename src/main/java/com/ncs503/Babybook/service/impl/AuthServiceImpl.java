package com.ncs503.Babybook.service.impl;

import com.ncs503.Babybook.auth.filter.JwtUtils;
import com.ncs503.Babybook.auth.utility.RoleEnum;
import com.ncs503.Babybook.exception.*;
import com.ncs503.Babybook.models.entity.RoleEntity;
import com.ncs503.Babybook.models.entity.UserEntity;
import com.ncs503.Babybook.models.mapper.UserMapper;
import com.ncs503.Babybook.models.request.LoginRequest;
import com.ncs503.Babybook.models.request.UserRequest;
import com.ncs503.Babybook.models.response.LoginResponse;
import com.ncs503.Babybook.models.response.UserResponse;
import com.ncs503.Babybook.repository.RoleRepository;
import com.ncs503.Babybook.repository.UserRepository;
import com.ncs503.Babybook.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passEnc;



    public LoginResponse login (LoginRequest request){

        try{
            String token = jwtUtils.generateToken(authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())), request.getEmail());
            return LoginResponse.builder()
                    .email(request.getEmail())
                    .token(token)
                    .build();
        } catch (Exception e){
            return LoginResponse.builder().build();
        }

    }


    @Override
    public UserResponse saveUser(UserRequest userReq) throws InvalidUserException, UserProfileAlreadyExistsException, UserNotFoundException, GuestNotFoundException, IOException {
        if (userRepo.findByEmail(userReq.getEmail()).isPresent())
            throw new UserProfileAlreadyExistsException("E-mail already used");
        if(userRepo.findByUsername(userReq.getUsername()).isPresent())
            throw new UserProfileAlreadyExistsException("Username already taken, choose a new one");

        String pass = userReq.getPassword();
        userReq.setPassword(passEnc.encode(pass));

//        String rol2 = new String();
//
//        if(!true) {
//            rol2 = RoleEnum.USER.getSimpleRoleName();
//        }


        Set<RoleEntity> roles = roleRepo.findByName(RoleEnum.USER.getSimpleRoleName());
        if (roles.isEmpty()) {
            RoleEntity rol = new RoleEntity();
            rol.setName(RoleEnum.USER.getSimpleRoleName());
            rol = roleRepo.save(rol);
            roles.add(rol);
        }
        UserEntity user = userMapper.toUserEntityWithRoles(userReq, roles);
        userRepo.save(user);
        UserEntity userWithId = userRepo.findByEmail(userReq.getEmail()).orElse(null);
        return userMapper.toUserResponse(userWithId);

    }

    @Override
    public UserResponse saveAdminUser(UserRequest userReq) throws InvalidUserException, UserProfileAlreadyExistsException, UserNotFoundException, GuestNotFoundException, IOException {
        if (userRepo.findByEmail(userReq.getEmail()).isPresent())
            throw new UserProfileAlreadyExistsException("E-mail already used");
        if(userRepo.findByUsername(userReq.getUsername()).isPresent())
            throw new UserProfileAlreadyExistsException("Username already taken, choose a new one");

        String pass = userReq.getPassword();
        userReq.setPassword(passEnc.encode(pass));

        Set<RoleEntity> roles = roleRepo.findByName(RoleEnum.ADMIN.getSimpleRoleName());
        if (roles.isEmpty()) {
            RoleEntity rol = new RoleEntity();
            rol.setName(RoleEnum.ADMIN.getSimpleRoleName());
            rol = roleRepo.save(rol);
            roles.add(rol);
        }
        roles.add((RoleEntity) roleRepo.findByName(RoleEnum.USER.getSimpleRoleName()));
        UserEntity user = userMapper.toUserEntityWithRoles(userReq, roles);
        userRepo.save(user);
        UserEntity userWithId = userRepo.findByEmail(userReq.getEmail()).orElse(null);
        return userMapper.toUserResponse(userWithId);
    }

    @Override
    public UserResponse saveGuestUser(UserRequest userReq, Boolean wantsToBeUserToo) throws InvalidUserException, UserProfileAlreadyExistsException, UserNotFoundException, IOException, GuestNotFoundException {
        if(userRepo.findByEmail(userReq.getEmail()).isPresent())
            throw new UserProfileAlreadyExistsException("E-mail already taken!");
        if(userRepo.findByEmail(userReq.getUsername()).isPresent())
            throw new UserProfileAlreadyExistsException("Username is already taken, choose a new one");
        String pass = userReq.getPassword();
        userReq.setPassword(passEnc.encode(pass));

        Set<RoleEntity> roles = roleRepo.findByName(RoleEnum.GUEST.getSimpleRoleName());
        if(roles.isEmpty()) {
            RoleEntity rol = new RoleEntity();
            rol.setName((RoleEnum.GUEST.getSimpleRoleName()));
            rol = roleRepo.save(rol);
            roles.add(rol);
        }
        if(wantsToBeUserToo){
            roles.addAll(roleRepo.findByName(RoleEnum.USER.getSimpleRoleName()));
        }
        UserEntity user = userMapper.toUserEntityWithRoles(userReq, roles);
        userRepo.save(user);
        UserEntity userWithId = userRepo.findByEmail(userReq.getEmail()).orElse(null);
        return userMapper.toUserResponse(userWithId);
    }
}
