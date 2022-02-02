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
import mainClasses.Doctor;
import mainClasses.Randevouz;

/**
 * Servlet implementation class GetDocRandevouzServlet
 */
public class GetDocRandevouzServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public GetDocRandevouzServlet() {
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
		ArrayList<Randevouz> array = new ArrayList<>();
		ArrayList<JsonObject> array1 = new ArrayList<>();
		EditDoctorTable ed= new EditDoctorTable();
		EditRandevouzTable rt=new EditRandevouzTable();
		try {
			Doctor d=ed.getDatabaseToDoctor(username);
			array = rt.databaseToRandevouzDoctor(d.getDoctor_id());
			
			
			for(int i=0; i<array.size(); i++) {
				JsonObject jo = new JsonObject();
				jo.addProperty("randevouz_id", array.get(i).getRandevouz_id());
				jo.addProperty("user_id", array.get(i).getUser_id());
				jo.addProperty("doctor_id", array.get(i).getDoctor_id());
				jo.addProperty("date_time", array.get(i).getDate_time());
				jo.addProperty("price", array.get(i).getPrice());
				jo.addProperty("doctor_info", array.get(i).getDoctor_info());
				jo.addProperty("user_info", array.get(i).getUser_info());
				jo.addProperty("status", array.get(i).getStatus());
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
