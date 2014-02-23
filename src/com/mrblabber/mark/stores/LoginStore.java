package com.mrblabber.mark.stores;

public class LoginStore {
    private String username;
	private String password;
	private boolean check;

	public LoginStore() {}
	public String getUsername() { return username; }
	public String getPassword() { return password; }
	public boolean getCheck() { return check; }
	public void setUsername(String username) { this.username = username; }
	public void setPassword(String password) { this.password = password; }
	public void setCheck(boolean check) { this.check = check; }
}
