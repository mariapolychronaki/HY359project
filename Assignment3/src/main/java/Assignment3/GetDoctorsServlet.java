package Assignment3;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import database.tables.EditDoctorTable;
import mainClasses.Doctor;

/**
 * Servlet implementation class GetDoctors
 */
public class GetDoctorsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDoctorsServlet() {
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
		
		EditDoctorTable dt = new EditDoctorTable();
		ArrayList<Doctor> array = null;
		ArrayList<JsonObject> array1 = new ArrayList<JsonObject>();
		
		
		try {
			array = dt.databaseToCertifiedDoctors();
			for(int i=0; i<array.size(); i++) {
				JsonObject jo = new JsonObject();
				jo.addProperty("username", array.get(i).getUsername());
				jo.addProperty("password", array.get(i).getPassword());
				jo.addProperty("email", array.get(i).getEmail());
				jo.addProperty("firstName", array.get(i).getFirstname());
				jo.addProperty("lastName", array.get(i).getLastname());
				jo.addProperty("birthdate", array.get(i).getBirthdate());
				jo.addProperty("gender", array.get(i).getGender());
				jo.addProperty("country", array.get(i).getCountry());
				jo.addProperty("city", array.get(i).getCity());
				jo.addProperty("address", array.get(i).getAddress());
				jo.addProperty("lat", array.get(i).getLat());
				jo.addProperty("lon", array.get(i).getLon());
				jo.addProperty("telephone", array.get(i).getTelephone());
				jo.addProperty("height", array.get(i).getHeight());
				jo.addProperty("weight", array.get(i).getWeight());
				jo.addProperty("blooddonor", array.get(i).getBlooddonor());
				jo.addProperty("bloodtype", array.get(i).getBloodtype());
				array1.add(jo);
			}
			response.setStatus(200);
			response.getWriter().write(array1.toString());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
