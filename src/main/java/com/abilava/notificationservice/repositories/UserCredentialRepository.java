package com.abilava.notificationservice.repositories;

import com.abilava.notificationservice.dtos.UserProjection;
import com.abilava.notificationservice.entities.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, String> {

    Optional<UserCredential> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query(value = "SELECT username, password FROM user_cred WHERE id = :id", nativeQuery = true)
    UserProjection fetchById(@Param("id") String id);

}
