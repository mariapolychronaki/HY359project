package Assignment3;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import database.tables.EditRandevouzTable;
import mainClasses.Randevouz;

/**
 * Servlet implementation class GetSpecificRandevouzServlet
 */
public class GetSpecificRandevouzServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetSpecificRandevouzServlet() {
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
		System.out.println(randevouz_id);
		int  id =Integer.parseInt(randevouz_id);
		EditRandevouzTable rand = new EditRandevouzTable();
		

		try {
			Randevouz r=rand.databaseToRandevouz(id);
			if(r!=null) {
				JsonObject jo = new JsonObject();
				 jo.addProperty("randevouz_id", r.getRandevouz_id());
				 jo.addProperty("doctor_id", r.getDoctor_id());
				 jo.addProperty("user_id", r.getUser_id());
				 jo.addProperty("date_time", r.getDate_time());
				 jo.addProperty("price", r.getPrice());
				 jo.addProperty("doctor_info", r.getDoctor_info());
				 jo.addProperty("user_info", r.getUser_info());
				 jo.addProperty("status", r.getStatus());
				 response.setStatus(200);
				 response.getWriter().write(jo.toString());
			}else {
				response.setStatus(404);
				JsonObject jo = new JsonObject();
				jo.addProperty("error", "To randevouz den iparxei");
				response.getWriter().write(jo.toString());
				
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
