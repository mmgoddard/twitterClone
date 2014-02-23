package com.mrblabber.mark.stores;

public class RegisterStore {
	private String fullName;
	private String emailAddress;
	private String password;
	private String username;
	private boolean check;

	public RegisterStore() {}
	
	public String getName() { return fullName; }
	public String getEmail() { return emailAddress; }
	public String getPassword() { return password; }
	public String getUsername() { return username; }
	public boolean getCheck() { return check; }
	
	public void setName(String fullName) { this.fullName = fullName; }
	public void setEmail(String emailAddress) { this.emailAddress = emailAddress; }
	public void setPassword(String password) { this.password = password; }
	public void setUsername(String username) { this.username = username; }
	public void setCheck(boolean check) { this.check = check; }
}
