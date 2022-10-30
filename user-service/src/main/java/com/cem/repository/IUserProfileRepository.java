package com.cem.repository;

import com.cem.repository.entity.UserProfile;
import com.cem.repository.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findOptionalByAuthid(Long id);

    Optional<UserProfile> findOptionalByUsername(String username);

    Optional<UserProfile> findOptionalByUsernameContainingIgnoreCase(String username);

    List<UserProfile> findAllByStatus(Status status);

    @Query("select u from UserProfile as u where u.status = 'ACTIVE' ")
    List<UserProfile> getActiveProfile();
}
