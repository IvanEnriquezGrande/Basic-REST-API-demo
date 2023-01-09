package com.ivan.demo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ivan.demo.dto.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
