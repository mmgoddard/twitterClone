/**
 * 
 */
function validateRegister() {
	document.getElementById('registerErrorMessage').innerHTML = "";
	var fullName = document.forms["Register"]["FullName"].value;
	var email = document.forms["Register"]["EmailAddress"].value;
	var password = document.forms["Register"]["Password"].value;
	var username = document.forms["Register"]["Username"].value;

	//fullName Validation
	if(!validateFullName(fullName)) {
		document.getElementById('registerErrorMessage').innerHTML = "Invalid full name. No capitals.";
		return false;
	}
	//email validation
	if(!validateEmail(email)) {
		document.getElementById('registerErrorMessage').innerHTML = "Invalid email. Wrong format.";
		return false;
	}
	//password validation
	if(!validatePassword(password)) {
		document.getElementById('registerErrorMessage').innerHTML = "Invalid password. Must be at least 6 characters.";
		return false;
	}
	//username validation
	if(!validateUsername(username)) {
		document.getElementById('registerErrorMessage').innerHTML = "Invalid username. No capitals.";
		return false;
	}
	return true;
}

function validateLogin() {
	document.getElementById('loginErrorMessage').innerHTML = "";
	var username = document.forms["Login"]["Username"].value;
	var password = document.forms["Login"]["Password"].value;
	
	//username validation
	if(!validateUsername(username)) {
		document.getElementById('loginErrorMessage').innerHTML = "Invalid username. No capitals.";
		return false;
	}
	//password validation
	if(!validatePassword(password)) {
		document.getElementById('loginErrorMessage').innerHTML = "Invalid password. Must be at least 6 characters.";
		return false;
	}
	return true;
}

function validateFullName(fullName) {
	var re = /^[A-Za-z0-9_-]{3,16}$/;
	return re.test(fullName);
}

function validateEmail(email) { 
	var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return re.test(email);
}

function validatePassword(password) {
	var re = /^[a-z0-9_-]{6,18}$/;
	return re.test(password);
}

function validateUsername(username) {
	var re = /^[a-z0-9_-]{3,16}$/;
	return re.test(username);
}
