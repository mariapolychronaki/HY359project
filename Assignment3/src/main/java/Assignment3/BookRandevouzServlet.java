package Assignment3;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

import database.tables.EditDoctorTable;
import database.tables.EditRandevouzTable;
import database.tables.EditSimpleUserTable;
import mainClasses.Doctor;
import mainClasses.Randevouz;
import mainClasses.SimpleUser;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 * Servlet implementation class BookRandevouzServlet
 */
public class BookRandevouzServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookRandevouzServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String randevouz_id=request.getParameter("randevouz_id");
		int  id =Integer.parseInt(randevouz_id);
		
		HttpSession session=request.getSession(true);
		String username=(String) session.getAttribute("loggedIn");
		
		EditRandevouzTable er = new EditRandevouzTable();
		EditSimpleUserTable ed =new EditSimpleUserTable();
		EditDoctorTable edt = new EditDoctorTable();
		
		
		try {
			Randevouz rand=er.databaseToRandevouz(id);
			Doctor d=edt.databaseToDoctorID(rand.getDoctor_id());
			SimpleUser s=ed.databaseToSimpleUserID(username);
			
			if(rand.getStatus().equals("free")) {
				rand.setUser_info(username);
				rand.setUser_id(ed.databaseToSimpleUserID(username).getUser_id());
				er.updateRandevouz(id, rand.getUser_id(), rand.getUser_info(), "selected");
				
				/*
				
				 // Recipient's email ID needs to be mentioned.
			      String to = d.getEmail();

			      // Sender's email ID needs to be mentioned
			      String from =s.getEmail() ;

			      // Assuming you are sending email from localhost
			      String host = "localhost";

			      // Get system properties
			      Properties properties = System.getProperties();

			      // Setup mail server
			      properties.setProperty("mail.smtp.host", host);

			      // Get the default Session object.
			      Session session1 = Session.getDefaultInstance(properties);

			      try {
			         // Create a default MimeMessage object.
			         MimeMessage message = new MimeMessage(session1);

			         // Set From: header field of the header.
			         message.setFrom(new InternetAddress(from));

			         // Set To: header field of the header.
			         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			         // Set Subject: header field
			         message.setSubject("New Randevouz!");

			         // Now set the actual message
			         message.setText("User "+s.getUsername()+"booked a randevouz for "+rand.getDate_time()+".Please respond!");
	

			         // Send message
			         Transport.send(message);
			         System.out.println("Sent message successfully....");
			      } catch (MessagingException mex) {
			         mex.printStackTrace();
			      }
			      */
				
				JsonObject jo = new JsonObject();
				response.setStatus(200);
				jo.addProperty("OK", "To Randevouz gia tis"+rand.getDate_time()+" egine selected!");
				response.getWriter().write(jo.toString());
				
			}else {
				response.setStatus(403);
				JsonObject jo = new JsonObject();
				jo.addProperty("error", "To Randevouz den einai free status!");
				response.getWriter().write(jo.toString());
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
