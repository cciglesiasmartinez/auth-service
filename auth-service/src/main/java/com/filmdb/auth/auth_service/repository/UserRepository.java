package com.filmdb.auth.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.filmdb.auth.auth_service.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

}
