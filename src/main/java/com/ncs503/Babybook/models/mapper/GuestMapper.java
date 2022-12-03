package com.ncs503.Babybook.models.mapper;

import com.ncs503.Babybook.exception.GuestNotFoundException;
import com.ncs503.Babybook.exception.InvalidGuestException;
import com.ncs503.Babybook.models.entity.GuestEntity;
import com.ncs503.Babybook.models.entity.UserEntity;
import com.ncs503.Babybook.models.request.GuestRequest;
import com.ncs503.Babybook.models.response.GuestResponse;
import com.ncs503.Babybook.repository.GuestRepository;
import com.ncs503.Babybook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Leonardo Terlizzi
 */

@Component
public class GuestMapper {

    @Autowired
    private UserRepository userRepo;

    public GuestEntity toGuestEntity(GuestRequest guestReq) throws InvalidGuestException {
        GuestEntity guest = new GuestEntity();
        guest.setId(guestReq.getId());
        guest.setFirstName(guestReq.getFirstName());
        guest.setLastName(guestReq.getLastName());
        guest.setEmail(guestReq.getEmail());
        UserEntity user = userRepo.findById(guestReq.getUser_id()).orElse(null);
        guest.setUser_id(user);
        return guest;
    }

    public GuestResponse toGuestResponse(GuestEntity guest) throws GuestNotFoundException{
        GuestResponse guestRes = new GuestResponse();
        guestRes.setId(guest.getId());
        guestRes.setFirstName(guest.getFirstName());
        guestRes.setLastName(guest.getLastName());
        guestRes.setEmail(guest.getEmail());
        guestRes.setUser_id(guest.getUser_id().getId());
        return guestRes;
    }

    public List<GuestResponse> guestsToGuestResponseList(List<GuestEntity> guests) throws GuestNotFoundException {
        List<GuestResponse> guestResList = new ArrayList<>();
        guests.forEach(guest ->{
            try{
                guestResList.add(toGuestResponse(guest));
            } catch (GuestNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        return guestResList;
    }

    public List<GuestEntity> guestRequestToGuestEntityList(List<GuestRequest> guests) throws GuestNotFoundException{
        List<GuestEntity> guestEntityList = new ArrayList<>();
        guests.forEach(guest -> {
            try{
                guestEntityList.add(toGuestEntity(guest));
            } catch (InvalidGuestException e) {
                throw new RuntimeException(e);
            }
        });
        return guestEntityList;
    }

}
