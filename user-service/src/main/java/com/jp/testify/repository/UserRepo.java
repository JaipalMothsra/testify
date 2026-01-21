//package com.jp.testify.repository;
//
//import com.jp.testify.Entity.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface UserRepo extends JpaRepository<User,Long> {
//    User findByEmail(String email);
//}
package com.jp.testify.repository;

import com.jp.testify.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
