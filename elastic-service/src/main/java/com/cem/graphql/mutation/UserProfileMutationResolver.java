package com.cem.graphql.mutation;

import com.cem.graphql.model.UserProfileInput;
import com.cem.mapper.IUserMapper;
import com.cem.service.UserProfileService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserProfileMutationResolver implements GraphQLMutationResolver {

    private final UserProfileService userProfileService;

    public Boolean createUserProfile(UserProfileInput profile){
        try {
            userProfileService.save(IUserMapper.INSTANCE.toUserProfile(profile));
            return true;
        }catch (Exception e){
            return false;
        }

    }

}
