package com.ivan.demo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ivan.demo.dto.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{

}
