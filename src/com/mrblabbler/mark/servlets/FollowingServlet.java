package com.mrblabbler.mark.servlets;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.datastax.driver.core.Cluster;
import com.mrblabber.mark.lib.CassandraHosts;
import com.mrblabber.mark.models.FollowingModel;
import com.mrblabber.mark.stores.FollowingStore;

@WebServlet("/Following")
public class FollowingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Cluster cluster;
	
    public FollowingServlet() {
        super();
    }
    
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
			performFollow(value,request,response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("Follow") != null) {
			//perform follow
			String searchUsername = request.getParameter("Search");
			String sessionUsername = request.getSession().getAttribute("User").toString();
			FollowingModel fm = new FollowingModel();
			fm.setCluster(cluster);
			boolean check = fm.searchUsername(searchUsername, sessionUsername);
			if(!check) {
				String notFound = "Username not found, try again.";
				request.setAttribute("userNotFound", notFound);
			}
			performFollow(sessionUsername, request,response);
		}
		else if(request.getParameter("UnFollow") != null) {
			//perform unfollow
			String usernameToUnfollow = request.getParameter("usernameToUnfollow");
			String usernameSession = request.getSession().getAttribute("User").toString();
			FollowingModel fm = new FollowingModel();
			fm.setCluster(cluster);
			fm.unfollowUsername(usernameToUnfollow, usernameSession);
			performFollow(usernameSession, request, response);
		}
	}
	
	public void performFollow(Object value, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		FollowingModel fm = new FollowingModel();
		fm.setCluster(cluster);
		LinkedList<FollowingStore> tweetList = fm.getFollowings(value);
		req.setAttribute("Tweets", tweetList);	
		RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/Following.jsp");
		rd.forward(req, res);
	}
}
