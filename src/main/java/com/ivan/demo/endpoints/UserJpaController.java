package com.ivan.demo.endpoints;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ivan.demo.dto.Post;
import com.ivan.demo.dto.User;
import com.ivan.demo.exceptions.PostNotFoundException;
import com.ivan.demo.exceptions.UserNotFoundException;
import com.ivan.demo.jpa.PostRepository;
import com.ivan.demo.jpa.UserRepository;
import com.ivan.demo.service.UserDAOService;

import jakarta.validation.Valid;

@RestController
public class UserJpaController {
	
	private UserDAOService service;
	/*
	 * A Repository is an interfece that extends from JpaRepository
	 * used to manage the data base operations
	 */
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PostRepository postRepository;
	/*
	public UserJpaController(UserRepository repository, UserDAOService service) {
		this.repository = repository;
		this.service = service;
	}*/
	
	//GET/users
	@GetMapping(path = "/jpa/users")
	public List<User> retrieveAllUsers(){
		return userRepository.findAll();
	}
	
	//GET/users/{id}
	@GetMapping(path = "/jpa/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isEmpty()) {
			throw new UserNotFoundException("id:" + id);
		}
		//HAL implemetation
		//Add link to /users
		EntityModel<User> entityModel = EntityModel.of(user.get());
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		entityModel.add(link.withRel("all-users"));
		return entityModel;
	}
	
	//GET/users/{id}/posts
		@GetMapping(path = "/jpa/users/{id}/posts")
		public List<Post> retrievePostForUser(@PathVariable int id) {
			Optional<User> user = userRepository.findById(id);
			if(user.isEmpty()) {
				throw new UserNotFoundException("id:" + id);
			}
			//HAL implemetation
			//Add link to /users
			return user.get().getPosts();
		}
	
	//POST/users
	//A ResponseEntity returns an ack with the message 201
	@PostMapping(path = "/jpa/users")
	public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build(); 
	}
	
	
	//POST/users/post
	//A ResponseEntity returns an ack with the message 201
	@PostMapping(path = "/jpa/users/{id}/posts")
	public ResponseEntity<Post> addPostForUser(@PathVariable int id, @Valid@RequestBody Post post){
		Optional<User> user = userRepository.findById(id);
		if(user.isEmpty()) {
			throw new UserNotFoundException("id:" + id);
		}
		post.setUser(user.get());
		//Save in the database
		Post savedPost = postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPost.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	//GET /users/{id)/posts/{id}
	//EntityModel returns the model but with specific configurations
	@GetMapping(path = "/jpa/users/{idUser}/posts/{idPost}")
	public EntityModel<Post> retrievePost(@PathVariable int idUser, @PathVariable int idPost){
		Optional<User> user = userRepository.findById(idUser);
		if(user.isEmpty()) {
			throw new UserNotFoundException("id:" + idPost);
		}
		Post post;
		try {
			post = user.get().getPosts().get(idPost-1);
		} catch (Exception e) {
			throw new PostNotFoundException("id:" + idPost);
		}
		EntityModel<Post> entityModel = EntityModel.of(post);
		//The WebMVCBuilder add a link to the methos specified
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrievePostForUser(idUser));
		entityModel.add(link.withRel("all-posts"));
		return entityModel;
	}
	
	//DELETE/users/{id}
	@DeleteMapping(path = "/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
	}

}
