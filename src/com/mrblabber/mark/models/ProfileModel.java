package com.mrblabber.mark.models;

import java.util.LinkedList;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.mrblabber.mark.stores.ProfileStore;

public class ProfileModel {
	private Cluster cluster;
	private Session session;
	
	public ProfileModel() {}
	
	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}
	
	public LinkedList<ProfileStore> getProfileDetails(String username) {
		LinkedList<ProfileStore> profileDetails = new LinkedList<ProfileStore>();
		String query = "SELECT * FROM users WHERE username='"+username+"'";
		try {
			session = cluster.connect("mgkeyspace");
			PreparedStatement statement = session.prepare(query);
			BoundStatement boundStatement = new BoundStatement(statement);
			ResultSet res = session.execute(boundStatement);
			//if the database returns a result
			if (res.isExhausted()) {
			} else { //there is a username that matches in the database.
				for (Row row : res) {
					ProfileStore ps = new ProfileStore();
					ps.setUsername(row.getString("username"));
					ps.setEmail(row.getString("email"));
					ps.setFullName(row.getString("name"));
					ps.setPassword(row.getString("password"));
					ps.setBio(row.getString("bio"));
					ps.setLocation (row.getString("location"));
					profileDetails.add(ps);
				}
			}
			session.close();
		} catch (Exception e) {
			System.out.print("An Error Occurred6: "+e);
		}
		return profileDetails;
	}
	
	public boolean updateProfile(ProfileStore ps) {
		try {
			session = cluster.connect("mgkeyspace");
			String query = "UPDATE users SET "
				+ "name='"+ps.getFullName()+"', "
				+ "email='"+ps.getEmail()+"', "
				+ "bio='"+ps.getBio()+"', "
				+ "location='"+ps.getLocation()+"', "
				+ "password='"+ps.getPassword()+"' "
				+ "WHERE username='"+ps.getUsername()+"'";
			PreparedStatement statement = session.prepare(query);
			BoundStatement boundStatement = new BoundStatement(statement);
			session.execute(boundStatement);
			return true;
		} catch (Exception e) {
			System.out.print("An Error Occurred7: "+e);
			return false;
		}
	}
}
