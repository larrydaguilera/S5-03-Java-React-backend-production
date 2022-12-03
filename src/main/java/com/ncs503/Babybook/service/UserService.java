
package com.ncs503.Babybook.service;

import com.ncs503.Babybook.exception.GuestNotFoundException;
import com.ncs503.Babybook.exception.InvalidUserException;
import com.ncs503.Babybook.exception.UserNotFoundException;
import com.ncs503.Babybook.models.request.UpdateUserRequest;
import com.ncs503.Babybook.models.response.PaginationResponse;
import com.ncs503.Babybook.models.response.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    
    public List<UserResponse> getUsers() throws UserNotFoundException;

    public PaginationResponse getUsersByPage( Optional<Integer> page, Optional<Integer> size) throws UserNotFoundException;

    public UserResponse getUser(String token, Long id) throws UserNotFoundException, InvalidUserException, GuestNotFoundException, IOException;

    public void deleteUser(Long id, String token) throws UserNotFoundException, InvalidUserException;
    
    public UserResponse updateUser(UpdateUserRequest userReq, Long id, String token) throws InvalidUserException, UserNotFoundException, GuestNotFoundException, IOException;
    
}
