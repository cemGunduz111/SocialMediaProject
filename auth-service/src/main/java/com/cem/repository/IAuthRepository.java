package com.cem.repository;

import com.cem.repository.entity.Auth;
import com.cem.repository.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAuthRepository extends JpaRepository<Auth, Long> {

    @Query("select count(a.username) > 0 from Auth as a where a.username = ?1")
    Boolean existUserName(String username);

    Optional<Auth> findOptionalByUsernameAndPassword(String username, String password);

    List<Auth> findAllByRoles(Roles roles);


}
