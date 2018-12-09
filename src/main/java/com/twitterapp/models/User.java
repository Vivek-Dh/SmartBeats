package com.twitterapp.models;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="smartbeats")
public class User {
	
	@Id
	long id;
	String name;
	Map<String,List<String> > songs;
	Map<String,List<String> > friendSongs;
	List<Long> friends;
	List<String> suggestions;
	List<Long> followingIds;
	public List<Long> getFollowingIds() {
		return followingIds;
	}
	public void setFollowingIds(List<Long> followingIds) {
		this.followingIds = followingIds;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, List<String>> getSongs() {
		return songs;
	}
	public void setSongs(Map<String, List<String>> songs) {
		this.songs = songs;
	}
	public Map<String, List<String>> getFriendSongs() {
		return friendSongs;
	}
	public void setFriendSongs(Map<String, List<String>> friendSongs) {
		this.friendSongs = friendSongs;
	}
	public List<Long> getFriends() {
		return friends;
	}
	public void setFriends(List<Long> friends) {
		this.friends = friends;
	}
	public List<String> getSuggestions() {
		return suggestions;
	}
	public void setSuggestions(List<String> suggestions) {
		this.suggestions = suggestions;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", songs=" + songs + ", friendSongs=" + friendSongs + ", friends="
				+ friends + ", suggestions=" + suggestions + "]";
	}
	
}
