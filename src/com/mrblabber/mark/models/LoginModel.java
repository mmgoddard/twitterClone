package com.mrblabber.mark.models;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.mrblabber.mark.stores.LoginStore;

public class LoginModel {
	private Cluster cluster;
	private String username, password, retUsername, retPassword;
	private Session session;

	public LoginModel() {}

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}

	public boolean login(LoginStore ls) {
		username = ls.getUsername();
		password = ls.getPassword();
		String checkName = "SELECT username,password FROM users WHERE username='"+ username + "';";
		try {
			session = cluster.connect("mgkeyspace");
			PreparedStatement statement = session.prepare(checkName);
			BoundStatement boundStatement = new BoundStatement(statement);
			ResultSet res = session.execute(boundStatement);
			//if the database returns a result
			if (res.isExhausted()) {
				closeSession();
				return false;
			} else { //there is a username that matches in the database.
				for (Row row : res) {
					retUsername = row.getString("username");
					retPassword = row.getString("password");
				}
				if(username.equals(retUsername) && password.equals(retPassword)) {
					closeSession();
					return true;
				} else {
					closeSession();
					return false;
				}
			}
		} catch (Exception e) {
			System.out.print("An Error Occurred8: "+e);
			return false;
		}
	}
	
	public void closeSession() {
		session.close();
	}
}
