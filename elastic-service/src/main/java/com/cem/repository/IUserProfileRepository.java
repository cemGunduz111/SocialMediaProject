package com.cem.repository;

import com.cem.repository.entity.UserProfile;
import com.cem.repository.enums.Status;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserProfileRepository extends ElasticsearchRepository<UserProfile, Long> {

    Optional<UserProfile> findOptionalByAuthid(Long id);

    Optional<UserProfile> findOptionalByUsername(String username);

    Optional<UserProfile> findOptionalByUsernameContainingIgnoreCase(String username);

    List<UserProfile> findAllByStatus(Status status);

    List<UserProfile> findByUsernameContainingIgnoreCase(String username);
    List<UserProfile> findByEmailContainingIgnoreCase(String email);

}
