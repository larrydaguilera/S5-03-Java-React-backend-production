
package com.ncs503.Babybook.service.impl;

import com.ncs503.Babybook.auth.filter.JwtUtils;
import com.ncs503.Babybook.exception.GuestNotFoundException;
import com.ncs503.Babybook.exception.InvalidUserException;
import com.ncs503.Babybook.exception.UserNotFoundException;
import com.ncs503.Babybook.models.entity.UserEntity;
import com.ncs503.Babybook.models.mapper.UserMapper;
import com.ncs503.Babybook.models.request.UpdateUserRequest;
import com.ncs503.Babybook.models.response.PaginationResponse;
import com.ncs503.Babybook.models.response.UserResponse;
import com.ncs503.Babybook.repository.UserRepository;
import com.ncs503.Babybook.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.ncs503.Babybook.utils.PaginationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * @author Leonardo Terlizzi
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public List<UserResponse> getUsers() throws UserNotFoundException {
        return mapper.usersToUserResponseList(userRepo.findAll());
    }

    @Override
    public PaginationResponse getUsersByPage( Optional<Integer> pageNumber, Optional<Integer> size) throws UserNotFoundException {
        PaginationUtils pagination = new PaginationUtils(userRepo, pageNumber, size, "/user/getPagination/page%d&size%d");
        Page page = pagination.getPage();
        List<UserResponse> users = mapper.usersToUserResponseList(page.getContent());
        return PaginationResponse.builder()
                .entities(users)
                .nextPageURI(pagination.getNext())
                .prevPageURI(pagination.getPrevious())
                .build();
    }

    @Override
    public UserResponse getUser(String token, Long id) throws UserNotFoundException, InvalidUserException, GuestNotFoundException, IOException {
        String userToken = this.getToken(token);
        UserEntity user = this.getUserByToken(token);
        if(user.getId().equals(id)){
            return mapper.toUserResponse(user);
        }
        else throw new InvalidUserException("Invalid user");
    }

    @Override
    public void deleteUser(Long id, String token) throws UserNotFoundException, InvalidUserException {
        UserEntity user = this.getUserByToken(token);
        if(user.getId().equals(id)){
            userRepo.deleteById(id);
        }
        else throw new InvalidUserException("Invalid user, action forbidden");

    }

    @Override
    public UserResponse updateUser(UpdateUserRequest userReq, Long id, String token) throws InvalidUserException, UserNotFoundException, GuestNotFoundException, IOException {
        UserEntity user = this.getUserByToken(token);
        if(user.getId().equals(id)){
            UserEntity userEdited = mapper.toUserEntity(userReq);
            userEdited.setId(id);
            userRepo.save(userEdited);
            return mapper.toUserResponse(userEdited);
        }
        else throw new InvalidUserException("Invalid user");
    }



    public String getToken(String token) {
        String[] part = token.split(" ");
        String tokenWithoutBearer = part[1];
        return tokenWithoutBearer;
    }

    public UserEntity getUserByToken(String token){
        String token2 = this.getToken(token);
        return userRepo.findByEmail(jwtUtils.extractUsername(token2)).orElse(null);
    }


    }
