package com.mrblabber.mark.models;

import java.util.LinkedList;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.mrblabber.mark.stores.FollowersStore;

public class FollowersModel {
	private Cluster cluster;
	private Session session;

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}
	
	public LinkedList<FollowersStore> getFollowers(Object value) {
		LinkedList<FollowersStore> followersList = new LinkedList<FollowersStore>();
		String username = value.toString();
		String query = "SELECT * FROM followers WHERE username='"+username+"';";
		try {
			session = cluster.connect("mgkeyspace");
			PreparedStatement statement = session.prepare(query);
			BoundStatement boundStatement = new BoundStatement(statement);
			ResultSet res = session.execute(boundStatement);
			//if the database returns a result
			if (res.isExhausted()) {
				System.out.println("You are following no one.");
			} else { //there is a username that matches in the database.
				for (Row row : res) {
					FollowersStore fs = new FollowersStore();
					fs.setUsername(row.getString("username"));
					fs.setFollowers(row.getString("followers"));
					followersList.add(fs);
				}
			}
			session.close();
		} catch (Exception e) {
			System.out.print("An Error Occurred11: "+e);
		}
		return followersList;
	}
}
