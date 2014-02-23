package com.mrblabber.mark.models;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.BoundStatement;
import com.mrblabber.mark.stores.*;
	
public class RegisterModel {
	private Cluster cluster;
	private Session session;
	
	public RegisterModel() { 
	}
	
	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}
	
	public boolean register(RegisterStore rs) {
		String name = rs.getName();
		String email = rs.getEmail();
		String password = rs.getPassword();
		String username = rs.getUsername();
		
		session = cluster.connect("mgkeyspace");
		
		boolean checkUsername = checkUsername(username, session);
		if(checkUsername) {
			//insert details into the database
			//Put together a database query
		    String query = "INSERT INTO users (name,email,password,username) values ('"+name+"','"+email+"','"+password+"','"+username+"');";
		    //Holds a connection to a Cassandra cluster allowing it to be queried
		    Session session1 = cluster.connect("mgkeyspace");
		    //Represents a prepared statement, prepared by the database
		    PreparedStatement statement1 = session1.prepare(query);
		    //A prepared statement with values bound to the bind variables
		    BoundStatement boundStatement1 = new BoundStatement(statement1);
		    //execute the statement
		    session.execute(boundStatement1);
		    
		    String updateFollowing = "BEGIN BATCH "
					+ "INSERT INTO following (username,following) VALUES ('"+username+"','"+username+"');"
					+ "INSERT INTO followers(username,followers) VALUES ('"+username+"','"+username+"');"
					+ "APPLY BATCH;";
		    
		    PreparedStatement statement2 = session.prepare(updateFollowing);
			BoundStatement boundStatement2 = new BoundStatement(statement2);
			session.execute(boundStatement2);
			closeSession();
			return true;
		} else {
			//username already exists in the database
			closeSession();
			return false;
		}
	}
	
	public boolean checkUsername(String username, Session session) {
		String checkName = "SELECT username FROM users WHERE username='"+username+"';";
		PreparedStatement statement = session.prepare(checkName);
		BoundStatement boundStatement = new BoundStatement(statement);
		ResultSet res = session.execute(boundStatement);

		if(res.isExhausted())
		{		
			//the username does not exist
			System.out.println("The username does not exist in the database.");
			return true;
		} else {
			//the username does exist
			System.out.println("The username does exist in the database.");
			return false;
		}
	}
	
	public void closeSession() {
		session.close();
	}
}
