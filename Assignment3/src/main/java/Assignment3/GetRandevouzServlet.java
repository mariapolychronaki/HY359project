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
import database.tables.EditRandevouzTable;
import mainClasses.Doctor;
import mainClasses.Randevouz;

/**
 * Servlet implementation class GetRandevouzServlet
 */
public class GetRandevouzServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetRandevouzServlet() {
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
		String doctor_id=request.getParameter("doctor_id");
		System.out.println(doctor_id);
		int  id =Integer.parseInt(doctor_id);
		EditRandevouzTable rand = new EditRandevouzTable();
		ArrayList<Randevouz> rtemp = null;
		ArrayList<JsonObject> array1 = new ArrayList<JsonObject>();
		
		HttpSession session=request.getSession(true);
		
		try {
			 rtemp = rand.databaseToRandevouzDoctor(id);
			 for(int i=0; i<rtemp.size(); i++) {
				 JsonObject jo = new JsonObject();
				 jo.addProperty("randevouz_id", rtemp.get(i).getRandevouz_id());
				 jo.addProperty("doctor_id", rtemp.get(i).getDoctor_id());
				 jo.addProperty("user_id", rtemp.get(i).getUser_id());
				 jo.addProperty("date_time", rtemp.get(i).getDate_time());
				 jo.addProperty("price", rtemp.get(i).getPrice());
				 jo.addProperty("doctor_info", rtemp.get(i).getDoctor_info());
				 jo.addProperty("user_info", rtemp.get(i).getUser_info());
				 jo.addProperty("status", rtemp.get(i).getStatus());
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
