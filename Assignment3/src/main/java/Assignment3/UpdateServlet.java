package Assignment3;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import database.tables.EditSimpleUserTable;
import mainClasses.JSON_Converter;
import mainClasses.SimpleUser;

/**
 * Servlet implementation class UpdateServlet
 */
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateServlet() {
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
		
		EditSimpleUserTable temp =new EditSimpleUserTable();
		SimpleUser p =new SimpleUser();
		p=temp.jsonToSimpleUser(json1);
		
		try {
			
			temp.updateSimpleUser(p.getUsername(),p.getWeight());
			response.setStatus(200);
			JsonObject jo = new JsonObject();
			jo.addProperty("username", p.getUsername());
			response.getWriter().write(jo.toString());
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
