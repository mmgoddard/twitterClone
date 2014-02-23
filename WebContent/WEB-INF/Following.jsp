<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.mrblabber.mark.stores.FollowingStore"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/stylesheet.css" />
<title>MrBlabber/Following</title>
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
				<h3>Add Friends</h3>
				<form action="Following" method="POST" name="Follow">
					<input type="text" id="Search" name="Search" placeholder="type friend's username here..." /> 
					<input type="submit" name="Follow" value="Add as a friend" class="submitBtn">
					<p class="errorMessage">${userNotFound}</p>
				</form>
			</div>
		</article>

		<article>
			<div id="tweetWrapper">
				<h3>You are following:</h3>
			</div>
			<%
				System.out.println("In render");
	    		List<FollowingStore> lTweet = (List<FollowingStore>) request.getAttribute("Tweets");
	    		if (lTweet == null) { 
			%>
			<p>You are following no one.</p>
			<%
	    		} else {
			%>
			<%
	    		Iterator<FollowingStore> iterator;
				iterator = lTweet.iterator();
				String sessionUsername = request.getSession().getAttribute("User").toString();
				while (iterator.hasNext()) {
			    	FollowingStore ts = (FollowingStore) iterator.next();
			    	if(!ts.getFollowing().equals(sessionUsername)) {
			%>
			<div id="tweetWrapper">
				<p><%=ts.getFollowing()%></p>
				<form action="Following" method="POST" name="UnFollow">
					<input type="hidden" name="usernameToUnfollow" value="<%=ts.getFollowing()%>"/>
					<input type="submit" name="UnFollow" value="Unfollow" class="submitBtn"/>
				</form>
			</div>
			<%}}}%>
		</article>
	</section>

	<!-- Footer -->
	<footer>
		<p>Mark Goddard 2013</p>
	</footer>
</body>
</html>