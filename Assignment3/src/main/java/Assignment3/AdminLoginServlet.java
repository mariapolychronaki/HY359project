package Assignment3;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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
import mainClasses.User;

/**
 * Servlet implementation class AdminLoginServlet
 */
public class AdminLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminLoginServlet() {
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
		
		EditDoctorTable dt = new EditDoctorTable();
		ArrayList<Doctor> array = null;
		ArrayList<JsonObject> array1 = new ArrayList<JsonObject>();
		
		
		HttpSession session=request.getSession(true);
		
		EditSimpleUserTable temp =new EditSimpleUserTable();
		try {
			SimpleUser sUser=temp.databaseToSimpleUser(username, password);
			System.out.println(username);
			System.out.println(password);
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			if(temp.databaseToSimpleUser(username, password)!=null && password.equals("admin12*") && username.equals("admin")) {
				response.setStatus(200);
				JsonObject jo = new JsonObject();
				jo.addProperty("OK", "You are loggedin as admin");
				array1.add(jo);
			
				session.setAttribute("loggedIn",username);
				ArrayList<SimpleUser>suarray=temp.databaseToCertifiedSimmpleUser();
				System.out.println(suarray.size());
				array = dt.databaseToDoctors();
					for(int i=0; i<array.size(); i++) {
						JsonObject jo1 = new JsonObject();
						jo1.addProperty("username", array.get(i).getUsername());
						jo1.addProperty("email", array.get(i).getEmail());
						jo1.addProperty("firstName", array.get(i).getFirstname());
						jo1.addProperty("lastName", array.get(i).getLastname());
						jo1.addProperty("birthdate", array.get(i).getBirthdate());
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
				
				
			}
			else{
				response.setStatus(404);
				JsonObject jo = new JsonObject();
				jo.addProperty("error", "O xristis admin den uparxei");
				response.getWriter().write(jo.toString());
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
