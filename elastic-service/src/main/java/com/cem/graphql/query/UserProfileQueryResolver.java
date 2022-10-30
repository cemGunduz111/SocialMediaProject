package com.cem.graphql.query;


import com.cem.repository.IUserProfileRepository;
import com.cem.repository.entity.UserProfile;
import com.cem.repository.enums.Status;
import com.cem.service.UserProfileService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserProfileQueryResolver implements GraphQLQueryResolver {

    private final UserProfileService userProfileService;

    public Iterable<UserProfile> findAll(){
        return userProfileService.findAll();
    }

    public List<UserProfile> findAllContainingUser(String username){
        return userProfileService.findAllContainingUser(username);
    }

    public List<UserProfile> findAllByStatus(String status){
        return userProfileService.findAllByStatus(status);
    }

    public List<UserProfile> findAllContainingEmail(String email){
        return userProfileService.findAllContainingEmail(email);
    }
}
