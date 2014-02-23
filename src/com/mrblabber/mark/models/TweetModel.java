package com.mrblabber.mark.models;

import java.util.LinkedList;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.mrblabber.mark.stores.FollowersStore;
import com.mrblabber.mark.stores.TweetStore;
import com.mrblabber.mark.stores.FollowingStore;
import com.eaio.uuid.UUID;

import java.util.*;

public class TweetModel {
	Cluster cluster;
	Session session;

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}

	public LinkedList<TweetStore> getTweets(String username) {
		LinkedList<TweetStore> tweetList = new LinkedList<TweetStore>();
		try {
			FollowingModel fm = new FollowingModel();
			fm.setCluster(cluster);
			LinkedList<FollowingStore> followersList = fm.getFollowings(username);
			session = cluster.connect("mgkeyspace");	
			Iterator<FollowingStore> iterator = followersList.iterator();
			while (iterator.hasNext()) {
				FollowingStore fs = (FollowingStore) iterator.next();
				String query = "SELECT * from timeline WHERE username='"+fs.getFollowing()+"';";
				PreparedStatement statement = session.prepare(query);
				BoundStatement boundStatement = new BoundStatement(statement);
				ResultSet rs = session.execute(boundStatement);
				if (rs.isExhausted()) {
					System.out.println("No Tweets returned");
				} else {
					for (Row row : rs) {
						TweetStore ts = new TweetStore();
						ts.setTweet(row.getString("tweet"));
						ts.setUser(row.getString("username"));
						ts.setPostedBy(row.getString("posted_by"));
						String temp = row.getUUID("interaction_time").toString();
						ts.setUuid(temp);
						tweetList.add(ts);
					}
				}
			}
			session.close();
			}
		catch (Exception e){
		}
		return tweetList;
	}
	
	public void insertTweet(TweetStore ts, Object value)
	{
		//code to insert a tweet into the database
		String username = ts.getUser();
		String tweet = ts.getTweet();
		//create a timeuuid for the tweet that is being inserted
		UUID uuid = new UUID();
		//connect to the database mgkeyspace
		session = cluster.connect("mgkeyspace");
		//create the query
		String query = "INSERT INTO tweets (username,interaction_time,tweet) VALUES ('"+username+"',"+uuid+",'"+tweet+"')";
		PreparedStatement statement = session.prepare(query);
		BoundStatement boundStatement = new BoundStatement(statement);
		//execute the query on the database
		session.execute(boundStatement);
		
		FollowersModel fm = new FollowersModel();
		fm.setCluster(cluster);
		LinkedList<FollowersStore> followersList = fm.getFollowers(value);
		
		Iterator<FollowersStore> iterator;
		iterator = followersList.iterator();
		while (iterator.hasNext()) {
	    	FollowersStore fs = (FollowersStore) iterator.next();
	    	String query1 = "INSERT INTO timeline (username,interaction_time,posted_by, tweet) VALUES ('"+fs.getFollowers()+"',"+uuid+",'"+username+"','"+tweet+"');";
	    	PreparedStatement statement1 = session.prepare(query1);
	    	BoundStatement boundStatement1 = new BoundStatement(statement1);
	    	session.execute(boundStatement1);
		}
		String query2 = "INSERT INTO userline (username,interaction_time,tweet) VALUES ('"+username+"',"+uuid+",'"+tweet+"');";
    	PreparedStatement statement2 = session.prepare(query2);
    	BoundStatement boundStatement2 = new BoundStatement(statement2);
    	session.execute(boundStatement2);
    	
		String query3 = "INSERT INTO timeline (username,interaction_time,posted_by,tweet) VALUES ('"+username+"',"+uuid+",'"+username+"','"+tweet+"');";
    	PreparedStatement statement3 = session.prepare(query3);
    	BoundStatement boundStatement3 = new BoundStatement(statement3);
    	session.execute(boundStatement3);
		session.close();	
	}
	
	public void deleteTweet(TweetStore ts) {
		//get the user and uuid from bean
		String user = ts.getUser();
		String strUuid = ts.getUuid();
		UUID uuid = new UUID(strUuid);
		//connect to the database
		session = cluster.connect("mgkeyspace");
		//create the query	
		String deleteQuery = "BEGIN BATCH "
				+ "DELETE FROM tweets WHERE interaction_time="+uuid+" AND username='"+user+"';"
				+ "DELETE FROM userline WHERE interaction_time="+uuid+" AND username='"+user+"';"
				+ "DELETE FROM timeline WHERE interaction_time="+uuid+" AND username='"+user+"';"
				+ "APPLY BATCH;";
		//prepare the query for execution
		PreparedStatement statement = session.prepare(deleteQuery);
		BoundStatement boundStatement = new BoundStatement(statement);
		//execute the query on the database
		session.execute(boundStatement);
		session.close();	
	}
}
