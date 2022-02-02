package Assignment3;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import database.tables.EditRandevouzTable;
import mainClasses.JSON_Converter;
import mainClasses.Randevouz;

/**
 * Servlet implementation class UpdateRandevouzServlet
 */
public class UpdateRandevouzServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateRandevouzServlet() {
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
	
		JSON_Converter jc = new JSON_Converter();
		BufferedReader inputJSONfromClient=request.getReader();
		String json1  = jc.getJSONFromAjax(inputJSONfromClient);
		
		EditRandevouzTable er = new EditRandevouzTable();		
		Randevouz r = er.jsonToRandevouz(json1);
		try {
			er.updateRandevouz(r.getRandevouz_id(), r.getUser_id(), r.getUser_info(), r.getStatus());
			JsonObject jo = new JsonObject();
			response.setStatus(200);
			jo.addProperty("OK", "To Randevouz gia tis "+r.getDate_time()+" egine updated!");
			response.getWriter().write(jo.toString());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
