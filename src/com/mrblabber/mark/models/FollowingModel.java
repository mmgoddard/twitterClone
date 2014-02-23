package com.mrblabber.mark.models;
import java.util.LinkedList;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.mrblabber.mark.stores.FollowingStore;

public class FollowingModel {
	private Cluster cluster;
	private Session session;

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}

	public LinkedList<FollowingStore> getFollowings(Object value) {
		LinkedList<FollowingStore> tweetList = new LinkedList<FollowingStore>();
		String username = value.toString();
		session = cluster.connect("mgkeyspace");
		String query = "SELECT * FROM following WHERE username='"+username+"';";
		PreparedStatement statement = session.prepare(query);
		BoundStatement boundStatement = new BoundStatement(statement);
		ResultSet res = session.execute(boundStatement);
		//if the database returns a result
		if (res.isExhausted()) {
			System.out.println("You are following no one.");
		} else { //there is a username that matches in the database.
			for (Row row : res) {
				FollowingStore fs = new FollowingStore();
				fs.setUsername(row.getString("username"));
				fs.setFollowing(row.getString("following"));
				tweetList.add(fs);
			}
		}
		session.close();
		return tweetList;
	}
	
	public boolean searchUsername(String searchUsername, String sessionUsername) {
		session = cluster.connect("mgkeyspace");
		String checkName = "SELECT username FROM users WHERE username='"+ searchUsername + "';";
		PreparedStatement statement = session.prepare(checkName);
		BoundStatement boundStatement = new BoundStatement(statement);
		ResultSet res = session.execute(boundStatement);
		//if the database returns a result
		if (res.isExhausted()) {
			session.close();
			return false;
		} else { //there is a username that matches in the database.
			String insertFollowing = "INSERT INTO following (username,following) VALUES ('"+sessionUsername+"','"+searchUsername+"');";
			PreparedStatement statement2 = session.prepare(insertFollowing);
			BoundStatement boundStatement2 = new BoundStatement(statement2);
			session.execute(boundStatement2);
				
			String insertFollowers = "INSERT INTO followers(username,followers) VALUES ('"+searchUsername+"','"+sessionUsername+"');";
			PreparedStatement statement3 = session.prepare(insertFollowers);
			BoundStatement boundStatement3 = new BoundStatement(statement3);
			session.execute(boundStatement3);
			return true;
		}
	}
	
	public void unfollowUsername(String unfollowUsername, String sessionUsername) {
		session = cluster.connect("mgkeyspace");
		String query1 = "DELETE FROM followers WHERE username='"+unfollowUsername+"' AND followers='"+sessionUsername+"'";
		PreparedStatement statement1 = session.prepare(query1);
		BoundStatement boundStatement1 = new BoundStatement(statement1);
		session.execute(boundStatement1);
		
		String query2 = "DELETE FROM following WHERE username='"+sessionUsername+"' AND following='"+unfollowUsername+"'";
		PreparedStatement statement2 = session.prepare(query2);
		BoundStatement boundStatement2 = new BoundStatement(statement2);
		session.execute(boundStatement2);
		session.close();
	}
}
