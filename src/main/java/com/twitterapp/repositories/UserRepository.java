package com.twitterapp.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.twitterapp.DTO.UserDTO;
import com.twitterapp.models.User;



@Repository
public class UserRepository {
	@Autowired
	MongoTemplate mongoTemplate;
	public UserDTO getUser(long id) {
		User user = mongoTemplate.findById(id, User.class, "smartbeats");
		UserDTO userDTO= new UserDTO();
		userDTO.setId(id);
		userDTO.setFriendSongs(user.getFriendSongs());
		userDTO.setSuggestions(user.getSuggestions());
		userDTO.setFollowingIds(user.getFollowingIds());
		return userDTO;
	}
	public String saveUser(User user) {
		mongoTemplate.save(user, "smartbeats");
		return "saved";
	}
}
