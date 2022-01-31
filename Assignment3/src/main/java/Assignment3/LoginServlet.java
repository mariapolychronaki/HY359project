package Assignment3;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

import database.tables.EditDoctorTable;
import database.tables.EditSimpleUserTable;
import mainClasses.Doctor;
import mainClasses.SimpleUser;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		
		HttpSession session=request.getSession(true);
		
		EditSimpleUserTable temp =new EditSimpleUserTable();
		EditDoctorTable dtemp = new EditDoctorTable();
		SimpleUser p=null;
		Doctor d = null;
		
		try {
			p=temp.databaseToSimpleUser(username, password);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(p==null) {
			try {
				d= dtemp.databaseToDoctor(username, password);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(d!=null  && d.getCertified()==1) {
				session.setAttribute("loggedIn",username);
				response.setStatus(200);
				JsonObject jo = new JsonObject();
				jo.addProperty("username", username);
				jo.addProperty("password", d.getPassword());
				jo.addProperty("email", d.getEmail());
				jo.addProperty("firstName", d.getFirstname());
				jo.addProperty("lastName", d.getLastname());
				jo.addProperty("birthdate", d.getBirthdate());
				jo.addProperty("gender", d.getGender());
				jo.addProperty("country", d.getCountry());
				jo.addProperty("city", d.getCity());
				jo.addProperty("address", d.getAddress());
				jo.addProperty("lat", d.getLat());
				jo.addProperty("lon", d.getLon());
				jo.addProperty("telephone", d.getTelephone());
				jo.addProperty("height", d.getHeight());
				jo.addProperty("weight", d.getWeight());
				jo.addProperty("blooddonor", d.getBlooddonor());
				jo.addProperty("bloodtype", d.getBloodtype());
				jo.addProperty("specialty", d.getSpecialty());
				jo.addProperty("id", d.getDoctor_id());
				response.getWriter().write(jo.toString());
			}else {
				response.setStatus(403);
				JsonObject jo = new JsonObject();
				if(d==null) {
					jo.addProperty("error", "To username i o kwdikos einai lathos");
				}else {
					jo.addProperty("error", "O Doctor den exei ginei certified");
				}
				
				response.getWriter().write(jo.toString());
			}
		}else {
			session.setAttribute("loggedIn",username);
			response.setStatus(200);
			JsonObject jo = new JsonObject();
			jo.addProperty("username", username);
			jo.addProperty("password", p.getPassword());
			jo.addProperty("email", p.getEmail());
			jo.addProperty("firstName", p.getFirstname());
			jo.addProperty("lastName", p.getLastname());
			jo.addProperty("birthdate", p.getBirthdate());
			jo.addProperty("gender", p.getGender());
			jo.addProperty("country", p.getCountry());
			jo.addProperty("city", p.getCity());
			jo.addProperty("address", p.getAddress());
			jo.addProperty("lat", p.getLat());
			jo.addProperty("lon", p.getLon());
			jo.addProperty("telephone", p.getTelephone());
			jo.addProperty("height", p.getHeight());
			jo.addProperty("weight", p.getWeight());
			jo.addProperty("blooddonor", p.getBlooddonor());
			jo.addProperty("bloodtype", p.getBloodtype());
			response.getWriter().write(jo.toString());
		}
		
		
	}

}
