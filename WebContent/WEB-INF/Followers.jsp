<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.mrblabber.mark.stores.FollowersStore"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/stylesheet.css"/>
<title>MrBlabber/Followers</title>
</head>
<body>
	<header>
		<div id="wrapHeaderTitle">	
			<div id="title">
				<h2>Blah. Blah. Blah.</h2>
			</div>
			<div id="nav">
				<ul>
					<li><a class="selected" href="/MarkGoddard/Homepage">Blabs</a></li>
					<li><a class="selected" href="/MarkGoddard/Profile">Profile</a></li>
					<li><a class="selected" href="/MarkGoddard/Followers">Followers</a></li>
					<li><a class="selected" href="/MarkGoddard/Following">Following</a></li>
					<li><a class="selected" href="/MarkGoddard/Tweets?LogOut=yes">Log Out</a></li>
				</ul>
			</div>
		</div>
	</header>

	<section>
		<article>
			<div id="tweetWrapper">
				<h3>People who are following you:</h3>
				<%  System.out.println("In render");
	    			List<FollowersStore> lTweet = (List<FollowersStore>) request.getAttribute("Tweets");
	    			if (lTweet == null) { 
					%>
				<p>You have no one following you.</p>
				<%
	    			} else {
					%>
				<%
	    				Iterator<FollowersStore> iterator;
						iterator = lTweet.iterator();
						String sessionUsername = request.getSession().getAttribute("User").toString();
						while (iterator.hasNext()) {
			    			FollowersStore ts = (FollowersStore) iterator.next();
			    			if(!ts.getFollowers().equals(sessionUsername)) {
							%><p><%=ts.getFollowers()%></p>
				<%}}}%>
			</div>
		</article>
	</section>
	
	<!-- Footer -->		
	<footer>
		<p>Mark Goddard 2013</p>
	</footer>
</body>
</html>