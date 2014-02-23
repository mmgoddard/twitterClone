package com.mrblabber.mark.lib;

import com.datastax.driver.core.*;

public final class Keyspaces {

	public Keyspaces() {}

	public static void SetUpKeySpaces(Cluster c) {
		try {
			// Add some keyspaces here
			String createkeyspace = "create keyspace if not exists mgkeyspace WITH replication = {'class':'SimpleStrategy', 'replication_factor':1}";
			String CreateTweetsTable = "CREATE TABLE IF NOT EXISTS tweets ("
					+ "username text," 
					+ "interaction_time timeuuid,"
					+ "tweet varchar,"
					+ "PRIMARY KEY (username, interaction_time)"
					+ ") WITH CLUSTERING ORDER BY (interaction_time DESC);";
			String CreateUsersTable = "CREATE TABLE IF NOT EXISTS users("
					+ "username text,"
					+ "email text,"
					+ "name text,"
					+ "password text,"
					+ "bio text,"
					+ "location text,"
					+ "PRIMARY KEY(username));";
			String CreateFollowingTable = "CREATE TABLE IF NOT EXISTS following ("
					+ "username text,"
					+ "following text,"
					+ "PRIMARY KEY(username, following));";
			String CreateFollowersTable = "CREATE TABLE IF NOT EXISTS followers ("
					+ "username text,"
					+ "followers text,"
					+ "PRIMARY KEY(username, followers));";
			String CreateUserlineTable = "CREATE TABLE IF NOT EXISTS userline ("
					+ "username text,"
					+ "interaction_time timeuuid,"
					+ "tweet text,"
					+ "PRIMARY KEY(username, interaction_time)) WITH CLUSTERING ORDER BY(interaction_time DESC);";
			String CreateTimelineTable = "CREATE TABLE IF NOT EXISTS timeline ("
					+ "username text,"
					+ "interaction_time timeuuid,"
					+ "posted_by text,"
					+ "tweet text,"
					+ "PRIMARY KEY(username, interaction_time)) WITH CLUSTERING ORDER BY(interaction_time DESC);";
			
			//Create a new session
			Session session = c.connect();
			//Create a new mgkeyspace if there isn't one
			try {
				PreparedStatement statement = session.prepare(createkeyspace);
				BoundStatement boundStatement = new BoundStatement(statement);
				session.execute(boundStatement);

			} catch (Exception et) {
				System.out.println("Can't create mgkeyspace " + et);
			}
			session.close();
			
			//Add columnfamily tweets to keyspace
			session = c.connect("mgkeyspace");
			//System.out.println("" + CreateTweetsTable);
			try {
				SimpleStatement cqlQuery = new SimpleStatement(CreateTweetsTable);
				session.execute(cqlQuery);
			} catch (Exception et) {
				System.out.println("Can't create tweets table " + et);
			}
			session.close();
			
			//Add columnfamily users to keyspace
			session = c.connect("mgkeyspace");
			//System.out.println("" + CreateUsersTable);
			try {
				SimpleStatement cqlQuery = new SimpleStatement(CreateUsersTable);
				session.execute(cqlQuery);
			} catch (Exception et) {
				System.out.println("Can't create users table " + et);
			}
			session.close();
			
			//Add columnfamily following to keyspace
			session = c.connect("mgkeyspace");
			//System.out.println("" + CreateFollowingTable);
			try {
				SimpleStatement cqlQuery = new SimpleStatement(CreateFollowingTable);
				session.execute(cqlQuery);
			} catch (Exception et) {
				System.out.println("Can't create following table " + et);
			}
			session.close();
			
			//Add columnfamily followers to keyspace
			session = c.connect("mgkeyspace");
			//System.out.println("" + CreateFollowersTable);
			try {
				SimpleStatement cqlQuery = new SimpleStatement(CreateFollowersTable);
				session.execute(cqlQuery);
			} catch (Exception et) {
				System.out.println("Can't create followers table " + et);
			}
			session.close();
			
			//Add columnfamily userline to keyspace
			session = c.connect("mgkeyspace");
			//System.out.println("" + CreateUserlineTable);
			try {
				SimpleStatement cqlQuery = new SimpleStatement(CreateUserlineTable);
				session.execute(cqlQuery);
			} catch (Exception et) {
				System.out.println("Can't create userline table " + et);
			}
			session.close();
			
			//Add columnfamily timeline to keyspace
			session = c.connect("mgkeyspace");
			//System.out.println("" + CreateTimelineTable);
			try {
				SimpleStatement cqlQuery = new SimpleStatement(CreateTimelineTable);
				session.execute(cqlQuery);
			} catch (Exception et) {
				System.out.println("Can't create timeline table " + et);
			}
			session.close();
		} catch (Exception et) {
			System.out.println("Other keyspace or column definition error" + et);
		}
	}
}