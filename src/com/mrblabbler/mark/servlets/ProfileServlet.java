package com.mrblabbler.mark.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mrblabber.mark.lib.CassandraHosts;
import com.mrblabber.mark.models.ProfileModel;
import com.mrblabber.mark.stores.ProfileStore;
import com.datastax.driver.core.Cluster;

import java.util.LinkedList;

@WebServlet("/Profile")
public class ProfileServlet extends HttpServlet {
	private Cluster cluster;
	private static final long serialVersionUID = 1L;
       
    public ProfileServlet() {
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
			getUserDetails(request,response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProfileStore ps = new ProfileStore();
		ps.setUsername(request.getSession().getAttribute("User").toString());
		ps.setFullName(request.getParameter("name"));
		ps.setEmail(request.getParameter("email"));
		ps.setPassword(request.getParameter("password"));
		ps.setBio(request.getParameter("bio"));
		ps.setLocation(request.getParameter("location"));
		
		ProfileModel pm = new ProfileModel();
		pm.setCluster(cluster);
		boolean check = pm.updateProfile(ps);
		String updateProfileError = "";
		if(check == true)
			updateProfileError = "Your details were saved.";
		else
			updateProfileError = "Error with saving your details.";
		request.setAttribute("updateProfileError", updateProfileError);	
		getUserDetails(request, response);
	}
	
	protected void getUserDetails(HttpServletRequest req, HttpServletResponse res) {
		String username = req.getSession().getAttribute("User").toString();
		ProfileModel pm = new ProfileModel();
		pm.setCluster(cluster);
		LinkedList<ProfileStore> profileDetails = pm.getProfileDetails(username);
		req.setAttribute("profileDetails", profileDetails);
		try { RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/Profile.jsp");
			rd.include(req, res);
		} catch(Exception e) { };
	}
}
