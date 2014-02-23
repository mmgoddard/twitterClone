<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.mrblabber.mark.stores.ProfileStore"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/stylesheet.css"/>
<title>MrBlabber/Profile</title>
</head>
<body>
	<!-- Header -->
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
				<h3>Your Profile</h3>
			</div>
			<%
				System.out.println("In render");
	    		List<ProfileStore> lTweet = (List<ProfileStore>) request.getAttribute("profileDetails");
	    		if (lTweet == null) {
	    		} else {
				%><%
	    			Iterator<ProfileStore> iterator;
					iterator = lTweet.iterator();
					while (iterator.hasNext()) {
			    		ProfileStore ts = (ProfileStore) iterator.next();
			    	if(ts.getBio() == null)
						ts.setBio("");
			    	if(ts.getLocation() == null)
			    		ts.setLocation("");
				%>
			<div id="tweetWrapper">
				<form id="updateProfile" action="Profile" method="POST">
					<p>Full Name: <input type="text" value="<%=ts.getFullName()%>" name="name"/></p>
					<p>Email: <input type="text" value="<%=ts.getEmail()%>" name="email"/></p>
					<p>Password: <input type="password" value="<%=ts.getPassword()%>" name="password"/></p>
					<p>Bio: <input type="text" value="<%=ts.getBio()%>" name="bio"/></p>
					<p>Location: <input type="text" value="<%=ts.getLocation()%>" name="location"/></p>
					<input type="submit" value="Save Changes" class="submitBtn"/>
					<p class="errorMessage">${updateProfileError}</p>
				<%}}%> 
				</form>
			</div>
		</article>
	</section>
	
	<footer>
		<p>Mark Goddard 2013</p>
	</footer>
</body>
</html>