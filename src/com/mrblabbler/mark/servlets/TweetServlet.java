package com.mrblabbler.mark.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;

import com.datastax.driver.core.Cluster;
import com.mrblabber.mark.lib.*;
import com.mrblabber.mark.models.*;
import com.mrblabber.mark.stores.*;

@WebServlet("/Tweets")
public class TweetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Cluster cluster;
    public TweetServlet() {
        super();
    }
    
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	cluster = CassandraHosts.getCluster();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String temp = request.getParameter("LogOut");
		String logout = "yes";
		
		if(temp != null) {
			if(temp.equals(logout)) {
				HttpSession session = request.getSession();
				session.invalidate();
		        response.sendRedirect("/MarkGoddard/Homepage");
		        System.out.println("You will be logged out.");
		        return;
			}
		}
		
		String userline = request.getParameter("userline");
		String user = request.getParameter("user");
		if(userline != null) {
			if(userline.equals("true")) {
				request.getSession().setAttribute("clickedUser", user);
				getTweets(request, response, user);
				response.sendRedirect("/MarkGoddard/Tweets?userline=false");
				return;
			}
		}
		
		//getting the current session
		Object value = request.getSession().getAttribute("User");
		System.out.println("USERNAME: "+value);
		if(value == null)
		{
			try {
				response.sendRedirect("/MarkGoddard/Homepage");
			} catch (Exception e) { e.printStackTrace(); }
			System.out.println("Redirect to homepage as you are not logged in.");
		} else {
			String username = request.getSession().getAttribute("User").toString();
			getTweets(request, response, username);
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Tweets.jsp");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("DeleteTweet") != null) {
			//Delete Tweet
			System.out.println("Delete Tweet");
			TweetStore ts = new TweetStore();
			//get the uuidValue from jsp tweet
			String uuidValue = request.getParameter("uuidValue");
			//store the uid in the TweetStore bean
			ts.setUuid(uuidValue);
			//get current session
			HttpSession session = request.getSession();
			//get the username from the session
			String user = session.getAttribute("User").toString();
			//and store in the bean
			ts.setUser(user);
			//NOTE: go to the Tweet Model method to handle the deletion of a tweet
			TweetModel tm = new TweetModel();
			tm.setCluster(cluster);
			tm.deleteTweet(ts);
			response.sendRedirect("/MarkGoddard/Tweets");
		}
		else if(request.getParameter("ComposeTweet") != null) {
			//Compose Tweet
			String tweet = request.getParameter("Tweet");
			String username = request.getSession().getAttribute("User").toString();
			TweetStore ts = new TweetStore();
			ts.setTweet(tweet);
			ts.setUser(username);
			TweetModel tm = new TweetModel();
			tm.setCluster(cluster);
			tm.insertTweet(ts, username);
			getTweets(request, response, username);
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Tweets.jsp");
			rd.forward(request, response);
		}
	}
	
	public void getTweets(HttpServletRequest request, HttpServletResponse response, String username) throws ServletException, IOException {
		TweetModel tm = new TweetModel();
		tm.setCluster(cluster);
		LinkedList<TweetStore> tweetList = tm.getTweets(username);
		request.setAttribute("Tweets", tweetList);	
	}
}
