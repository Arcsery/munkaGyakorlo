package com.app.gyakorlo.repository;

import com.app.gyakorlo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    @Query(value = "SELECT * from users WHERE users.username = :UsernameOrEmail or users.email = :UsernameOrEmail", nativeQuery = true)
    User findUserByEmailOrUsername(
            @Param("UsernameOrEmail") String UsernameOrEmail
    );


    @Query(value = "UPDATE users SET name = :name, email = :email, age = :age where id = :id", nativeQuery = true)
    User updateUserById(
            @Param("id") Integer id,
            @Param("name") String name,
            @Param("email") String email,
            @Param("age") Integer age
    );

}

