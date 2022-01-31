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
import database.tables.EditSimpleUserTable;
import mainClasses.Doctor;
import mainClasses.SimpleUser;

/**
 * Servlet implementation class CertifyDoctorServlet
 */
public class CertifyDoctorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CertifyDoctorServlet() {
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
		System.out.println(username);
		EditDoctorTable dt = new EditDoctorTable();
		EditSimpleUserTable su = new EditSimpleUserTable();
		
		try {
			ArrayList<JsonObject> array1 = new ArrayList<JsonObject>();
			dt.certifyDoctor(username);
			JsonObject jo = new JsonObject();
			jo.addProperty("OK", "user "+ username+ "is certified");
			array1.add(jo);
			
			
			EditSimpleUserTable temp= new EditSimpleUserTable();
			ArrayList<SimpleUser>suarray=temp.databaseToCertifiedSimmpleUser();
			System.out.println(suarray.size());
			ArrayList<Doctor> array = dt.databaseToDoctors();
			
				
				for(int i=0; i<array.size(); i++) {
					JsonObject jo1 = new JsonObject();
					jo1.addProperty("username", array.get(i).getUsername());
					jo1.addProperty("email", array.get(i).getEmail());
					jo1.addProperty("firstName", array.get(i).getFirstname());
					jo1.addProperty("lastName", array.get(i).getLastname());
					jo1.addProperty("birthdate", array.get(i).getBirthdate());
					jo1.addProperty("certified", array.get(i).getCertified());
					array1.add(jo1);
				}
				for(int i=0; i<suarray.size(); i++) {
					JsonObject jo1 = new JsonObject();
					jo1.addProperty("username", suarray.get(i).getUsername());
					jo1.addProperty("email", suarray.get(i).getEmail());
					jo1.addProperty("firstName", suarray.get(i).getFirstname());
					jo1.addProperty("lastName", suarray.get(i).getLastname());
					jo1.addProperty("birthdate", suarray.get(i).getBirthdate());
					array1.add(jo1);
				}
				response.getWriter().write(array1.toString());
			
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
