package com.mrblabber.mark.stores;

public class ProfileStore {
	private String username;
	private String password;
	private String fullName;
	private String email;
	private String bio;
	private String location;
	
	public ProfileStore() { }
		
	public String getUsername() { return username; }
	public String getPassword() { return password; }
	public String getFullName() { return fullName; }
	public String getEmail() { return email; }
	public String getBio() { return bio; }
	public String getLocation() { return location; }
	public void setUsername(String username) { this.username = username; }
	public void setPassword(String password) { this.password = password; }
	public void setEmail(String email) { this.email = email; }
	public void setBio(String bio) { this.bio = bio; }
	public void setFullName(String fullName) { this.fullName = fullName; }
	public void setLocation(String location) { this.location = location; }
}
