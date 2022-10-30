package com.cem.service;

import com.cem.repository.IUserProfileRepository;
import com.cem.repository.entity.UserProfile;
import com.cem.repository.enums.Status;
import com.cem.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileService extends ServiceManager<UserProfile, Long> {

    private final IUserProfileRepository userProfileRepository;



    public UserProfileService(IUserProfileRepository userProfileRepository){
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
    }

    public List<UserProfile> findAllContainingUser(String username) {
        return userProfileRepository.findByUsernameContainingIgnoreCase(username);
    }

    public List<UserProfile> findAllByStatus(String status) {
        return userProfileRepository.findAllByStatus(Status.valueOf(status));
    }

    public List<UserProfile> findAllContainingEmail(String email) {
        return userProfileRepository.findByEmailContainingIgnoreCase(email);
    }
}
