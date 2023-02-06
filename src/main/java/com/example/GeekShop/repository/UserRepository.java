package com.example.GeekShop.repository;

import com.example.GeekShop.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
    void deleteUserByEmail(String email);
    @Query("SELECT U FROM User AS U WHERE UPPER(U.firstname) LIKE %:name% OR UPPER(U.lastname) LIKE %:name%")
    List<User> findAllByUsername(@Param("name") String name);
}
