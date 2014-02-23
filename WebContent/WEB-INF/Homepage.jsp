<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/stylesheet.css"/>
<title>Home</title>
</head>
<body>
	<!-- Header -->
	<header>
		<h1>Blah. Blah. Blah.</h1>
		<h3>Welcome to MrBlabber</h3>
	</header>
		
	<!-- Section -->
	<section>
		<!-- Sign In -->
		<article>
			<div id="articleWrapper">
				<h3>Sign-In</h3>
				<form action="Homepage" method="POST" onsubmit="return validateLogin();" name="Login">
					<input type="text" id="Username" name="Username" placeholder="Username"/>
					<input type="password" id="Password" name="Password" placeholder="Password"/>
					<input type="hidden" name="ACTION" value="Login"/>
					<input type="submit" name="Login" value="Sign In" class="submitBtn">
					<p class="errorMessage">${messageIncorrect}</p>
					<div id="loginErrorMessage">
						<script src="${pageContext.request.contextPath}/javascript/HomepageValidation.js" type="text/javascript"></script>
					</div>
				</form>
			</div>
		</article>
		
		<!-- Sign Up -->
		<article>
			<div id="articleWrapper">
				<h3>No Account? Sign Up</h3>
				<form action="Homepage" method="POST" onsubmit="return validateRegister();" name="Register">
					<input type="text" id="FullName" name="FullName" placeholder="FullName"/>
					<input type="text" id="EmailAddress" name="EmailAddress" placeholder="Email Address"/>
					<input type="password" id="Password" name="Password" placeholder="Create a password"/>
					<input type="text" id="Username" name="Username" placeholder="Choose your username"/>
					<input type="hidden" name="ACTION" value="Register" />
					<input type="submit" name="Register" value="Sign up for MrBlabber" class="submitBtn"/>
					<p class="errorMessage">${messageUserExists}</p>
					<div id="registerErrorMessage">
						<script src="${pageContext.request.contextPath}/javascript/HomepageValidation.js" type="text/javascript"></script>
					</div>
				</form>
			</div>
		</article>
	</section>
		
	<!-- Footer -->		
	<footer>
		<p>Mark Goddard 2013</p>
	</footer>
</body>
</html>