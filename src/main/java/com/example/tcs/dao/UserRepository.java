package com.example.tcs.dao;

import com.example.tcs.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Integer> {
    @Query("SELECT u FROM UserInfo u WHERE u.name=?1")
    UserInfo findByName(String username);
}
