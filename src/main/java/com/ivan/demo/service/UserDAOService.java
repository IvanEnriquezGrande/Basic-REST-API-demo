package com.ivan.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import com.ivan.demo.dto.User;

@Component
public class UserDAOService {

	private static List<User> users = new ArrayList<User>();
	private static int countUser = 0;
	
	static {
		users.add(new User(++UserDAOService.countUser, "Ivan", LocalDate.now().minusYears(30)));
		users.add(new User(++UserDAOService.countUser,  "Alex", LocalDate.now().minusYears(40)));
		users.add(new User(++UserDAOService.countUser, "Random", LocalDate.now().minusYears(15)));
	}
	
	//Retrieve All users
	public List findAll() {
		return users;
	}
	
	//Retrieve a specific user
	public User findUser(int id) {
		Predicate<? super User> predicate = user -> user.getId().equals(id);
		return users.stream().filter(predicate).findFirst().orElse(null);
	}
	
	//delete an user by id
	public void deleteById(int id) {
		Predicate<? super User> predicate = user -> user.getId().equals(id);
		users.removeIf(predicate);
	}
	
	//Save a user
	public User saveUser(User user) {
		user.setId(++UserDAOService.countUser);
		users.add(user);
		return user;
	}
}
