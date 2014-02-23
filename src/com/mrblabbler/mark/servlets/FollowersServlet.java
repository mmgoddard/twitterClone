package com.mrblabbler.mark.servlets;

import java.io.IOException;
import java.util.LinkedList;
import com.datastax.driver.core.Cluster;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mrblabber.mark.lib.CassandraHosts;
import com.mrblabber.mark.models.FollowersModel;
import com.mrblabber.mark.stores.FollowersStore;

@WebServlet("/Followers")
public class FollowersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Cluster cluster;
      
    public FollowersServlet() {
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
			getFollowings(value,request,response);
		}
	}
	
	public void getFollowings(Object value, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		FollowersModel fm = new FollowersModel();
		fm.setCluster(cluster);
		LinkedList<FollowersStore> tweetList = fm.getFollowers(value);
		req.setAttribute("Tweets", tweetList);	
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/Followers.jsp");
		rd.include(req, res);
	}
}
