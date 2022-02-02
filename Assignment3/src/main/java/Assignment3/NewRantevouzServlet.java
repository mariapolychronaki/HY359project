package Assignment3;

import java.io.BufferedReader;
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
import mainClasses.JSON_Converter;
import mainClasses.Randevouz;

/**
 * Servlet implementation class NewRantevouzSevrelt
 */
public class NewRantevouzServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewRantevouzServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSON_Converter jc = new JSON_Converter();
		BufferedReader inputJSONfromClient=request.getReader();
		String json1  = jc.getJSONFromAjax(inputJSONfromClient);
		JsonObject jo = new JsonObject();
		
		EditRandevouzTable temp = new EditRandevouzTable();
		Randevouz ran  =new  Randevouz();
		ran=temp.jsonToRandevouz(json1);
		System.out.println(ran);	
		if(ran.getDate_time()==null || ran.getDoctor_id()==0   ||  ran.getStatus()==null ||  ran.getUser_id()!=0 || ran.getUser_info()==null ) {
			response.setStatus(400);
			jo.addProperty("error","Randevouz details are missing");
			response.getWriter().write(jo.toString());
		}else {
			try {
				temp.createNewRandevouz(ran);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.setStatus(200);
			jo.addProperty("OK","Randevouz is  created!");
			response.getWriter().write(jo.toString());
		}
	}

}
