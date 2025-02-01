package com.youcode.cuisinio.user.infrastructure.repository;

import com.youcode.cuisinio.user.domain.model.agregate.User;
import com.youcode.cuisinio.user.domain.model.valueObject.Email;
import com.youcode.cuisinio.user.domain.model.valueObject.UserId;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, UserId> {

    @Query("SELECT COUNT(*) > 0 FROM users WHERE email = :email")
    boolean existsByEmail(Email email);

    @Query("SELECT * FROM users WHERE email = :email")
    Optional<User> findByEmail(Email email);
}