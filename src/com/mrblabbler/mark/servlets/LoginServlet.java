package com.mrblabbler.mark.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;

import com.datastax.driver.core.Cluster;
import com.mrblabber.mark.lib.CassandraHosts;
import com.mrblabber.mark.models.LoginModel;
import com.mrblabber.mark.models.RegisterModel;
import com.mrblabber.mark.stores.RegisterStore;
import com.mrblabber.mark.stores.LoginStore;

@WebServlet("/Homepage")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Cluster cluster;
    public LoginServlet() { super(); }
    
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
	    cluster = CassandraHosts.getCluster();
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Object value = request.getSession().getAttribute("User");
		if(value == null)
		{
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/Homepage.jsp");
			rd.include(request, response);
		} else {
			response.sendRedirect("/MarkGoddard/Tweets");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("Login") != null) {
			//perform login
			doLogin(request, response);
		}
		else if(request.getParameter("Register") != null) {
			//perform register
			doRegister(request, response);
		}
	}
	
	public void doLogin(HttpServletRequest request, HttpServletResponse response) {
		//get request parameters for the username and password
		String username = request.getParameter("Username");
		String password = request.getParameter("Password");
		
		//set the request parameters for the username and password
		LoginStore ls = new LoginStore();
		ls.setUsername(username);
		ls.setPassword(password);
		
		LoginModel lm = new LoginModel();
		lm.setCluster(cluster);
		boolean check = lm.login(ls);
		
		//if the username and password matches in the database
		if(check) {
			HttpSession session = request.getSession();
			session.setAttribute("User", username);
			session.setMaxInactiveInterval(30*60);
			Cookie userName = new Cookie("User", username);
			userName.setMaxAge(30*60);
			response.addCookie(userName);
			try {
				response.sendRedirect("/MarkGoddard/Tweets");
			} catch (Exception e) { e.printStackTrace(); }
		}
		else {
			System.out.println("Redirect to the homepage.");
			String messageIncorrect = "Error Message: either the username or password is incorrect.";
			request.setAttribute("messageIncorrect", messageIncorrect);
			try {
				request.getRequestDispatcher("WEB-INF/Homepage.jsp").forward(request, response);
			}
			catch(Exception e) { System.out.println("error with redirection"); }
		}
	}
	
	public void doRegister(HttpServletRequest request, HttpServletResponse response) {
		//get request parameters for the register form
		String fullName = request.getParameter("FullName");
		String emailAddress = request.getParameter("EmailAddress");
		String password = request.getParameter("Password");
		String username = request.getParameter("Username");
		
		RegisterStore rs = new RegisterStore();
		rs.setName(fullName);
		rs.setEmail(emailAddress);
		rs.setPassword(password);
		rs.setUsername(username);
		
		RegisterModel rm = new RegisterModel();
		rm.setCluster(cluster);
		boolean check = rm.register(rs);
		
		if(check) {
			HttpSession session = request.getSession();
			session.setAttribute("User", username);
			session.setMaxInactiveInterval(30*60);
			Cookie userName = new Cookie("User", username);
			userName.setMaxAge(30*60);
			response.addCookie(userName);
			try {
				response.sendRedirect("/MarkGoddard/Tweets");
			} catch (Exception e) { e.printStackTrace(); }
			
		} else {
			System.out.println("Redirect to the homepage.");
			String messageUserExists = "Error Message: the username already exists. Please try again.";
			request.setAttribute("messageUserExists", messageUserExists);
			try {
				request.getRequestDispatcher("WEB-INF/Homepage.jsp").forward(request, response);
			}
			catch(Exception e) { System.out.println("error with redirection"); }
		}
	}
}
