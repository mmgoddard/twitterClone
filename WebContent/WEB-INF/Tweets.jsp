<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.mrblabber.mark.stores.TweetStore" %>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/stylesheet.css"/>
<script src="/MarkGoddard/javascript/jQuery.js" type="text/javascript"></script>
<script src="/MarkGoddard/javascript/Tweets.js" type="text/javascript"></script>
<title>MrBlabber/Blab</title>
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
	
	<!-- Section -->
	<section>
		<article>
			<div id="composeTweetWrapper">
				<form id="createAccount" action="Tweets" method="POST" name="ComposeTweet">
					<textarea class="textArea" name="Tweet" placeholder="Compose Blab..." maxlength="140"></textarea>
					<input class="blab" type="submit" name="ComposeTweet" value="Blab"/>
					<p class="countOutput">140</p>
				</form>	
			</div>
		</article>
		
		<article>
			<%
	    		List<TweetStore> lTweet = (List<TweetStore>) request.getAttribute("Tweets");
	    		if (lTweet == null) { 
			%>
			<p>No Tweet found</p>
			<%
	    	} else {
			%>
			<%
	    		Iterator<TweetStore> iterator;
				iterator = lTweet.iterator();
				while (iterator.hasNext()) {
			    	TweetStore ts = (TweetStore) iterator.next();
			    	UUID uuid = UUID.fromString(ts.getUuid());
			    	long time = ((uuid.timestamp() - 0x01b21dd213814000L) / 10000);
			    	Date date = new Date(time);
			%>
			<div id="tweetWrapper">
				<p><a class="dash" onclick="" href="/MarkGoddard/Tweets?userline=true&user=<%=ts.getPostedBy()%>"><%=ts.getPostedBy()%></a>: <%=ts.getTweet()%></p>
				<p><%=date%></p>
				<form id="deleteTweet" action="Tweets" method="POST" name="DeleteTweet">
					<input type="hidden" name="uuidValue" value="<%=ts.getUuid()%>"/>
					<input type="hidden" name="username" value="<%=ts.getUser()%>"/>
					<%String sessionUsername = request.getSession().getAttribute("User").toString();
					String username = ts.getUser();
					if(sessionUsername.equals(ts.getPostedBy()))
					{%>
						<input type="submit" name="DeleteTweet" value="Delete Tweet" id="deleteBtn"/>
					<%}%>
				</form>
			</div>
			<%}}%>
		</article>
	</section>

	<div id="dashboard">
		<p class="ClickToClose">Click to close</p>
		<div id="userline">		
			<article>
			<p class="profileSummary">Profile Summary</p>
			<%
				String clickedUser = "";
				if(session.getAttribute("clickedUser") != null) {
					clickedUser = request.getSession().getAttribute("clickedUser").toString();
				}
	    		List<TweetStore> userline = (List<TweetStore>) request.getAttribute("Tweets");
	    		if (userline == null) { 
			%>
			<p class="profileSummary">for <%=clickedUser%></p>
			<p>No Tweet(s) found</p>
			<%
	    	} else {
			%>
			<%
	    		Iterator<TweetStore> iterator2;
				iterator2 = userline.iterator();
				while (iterator2.hasNext()) {
					TweetStore ts = (TweetStore) iterator2.next();
			    	UUID uuid = UUID.fromString(ts.getUuid());
			    	long time = ((uuid.timestamp() - 0x01b21dd213814000L) / 10000);
			    	Date date = new Date(time);
			    	if(clickedUser.equals(ts.getUser())) {
			%>
			<div id="tweetWrapper">
				<p><%=ts.getUser()%> : <%=ts.getTweet()%></p>
				<p><%=date%></p>
				<form id="deleteTweet" action="Tweets" method="POST" name="DeleteTweet">
					<input type="hidden" name="uuidValue" value="<%=ts.getUuid()%>"/>
					<input type="hidden" name="username" value="<%=ts.getUser()%>"/>
				</form>
			</div>
			<hr>
			<%}}}%>
		</article>
		</div>
	</div>	
	
	<!-- Footer -->		
	<footer>
		<p>Mark Goddard 2013</p>
	</footer>
</body>
</html>