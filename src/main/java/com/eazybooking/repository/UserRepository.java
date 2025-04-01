package com.eazybooking.repository;

import com.eazybooking.model.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Lazy
    Optional<User> findByEmail(String email);

    @Lazy
    Optional<User> findByName(String name);
}
