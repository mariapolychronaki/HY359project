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
import database.tables.EditRandevouzTable;
import database.tables.EditSimpleUserTable;
import mainClasses.Doctor;
import mainClasses.Randevouz;
import mainClasses.SimpleUser;

/**
 * Servlet implementation class DeleteRandevouzServlet
 */
public class DeleteRandevouzServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteRandevouzServlet() {
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
		String doctor_id=request.getParameter("doctor_id");
		System.out.println(randevouz_id);
		EditRandevouzTable dt = new EditRandevouzTable();
		
		try {
			ArrayList<JsonObject> array1 = new ArrayList<JsonObject>();
			dt.deleteRandevouz(Integer.parseInt(randevouz_id));
			JsonObject jo1 = new JsonObject();
			jo1.addProperty("OK", "randevouz "+ randevouz_id+ "is deleted");
			array1.add(jo1);
			
			ArrayList<Randevouz> array = dt.databaseToRandevouzDoctor(Integer.parseInt(doctor_id));
			
				
				for(int i=0; i<array.size(); i++) {
					 JsonObject jo = new JsonObject();
					 jo.addProperty("randevouz_id", array.get(i).getRandevouz_id());
					 jo.addProperty("doctor_id", array.get(i).getDoctor_id());
					 jo.addProperty("user_id", array.get(i).getUser_id());
					 jo.addProperty("date_time", array.get(i).getDate_time());
					 jo.addProperty("price", array.get(i).getPrice());
					 jo.addProperty("doctor_info", array.get(i).getDoctor_info());
					 jo.addProperty("user_info", array.get(i).getUser_info());
					 jo.addProperty("status", array.get(i).getStatus());
					 array1.add(jo);
				}

				response.getWriter().write(array1.toString());
			
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
