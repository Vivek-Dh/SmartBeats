package com.twitterapp.DTO;

import java.util.List;
import java.util.Map;

public class UserDTO {
	long id;
	List<String> suggestions;
	Map<String,List<String> > friendSongs;
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
	public List<String> getSuggestions() {
		return suggestions;
	}
	public void setSuggestions(List<String> suggestions) {
		this.suggestions = suggestions;
	}
	public Map<String, List<String>> getFriendSongs() {
		return friendSongs;
	}
	public void setFriendSongs(Map<String, List<String>> friendSongs) {
		this.friendSongs = friendSongs;
	}
	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", suggestions=" + suggestions + ", friendSongs=" + friendSongs + "]";
	}
	
}
