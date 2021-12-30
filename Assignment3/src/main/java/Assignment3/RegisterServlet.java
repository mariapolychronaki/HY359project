package Assignment3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import database.tables.EditDoctorTable;
import database.tables.EditSimpleUserTable;
import mainClasses.Doctor;
import mainClasses.JSON_Converter;
import mainClasses.SimpleUser;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSON_Converter jc = new JSON_Converter();
		BufferedReader inputJSONfromClient=request.getReader();
		String json1  = jc.getJSONFromAjax(inputJSONfromClient);
		EditSimpleUserTable temp =new EditSimpleUserTable();
		EditDoctorTable dtemp = new EditDoctorTable();
		
		SimpleUser p;
		Doctor d;
		boolean flag=false;
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		
		if(json1.contains("\"User\"")) {
			p = temp.jsonToSimpleUser(json1);
			try {
				ArrayList<SimpleUser> tempArray = temp.getAllUsers();
				for(int i=0; i<tempArray.size(); i++) {
					if(tempArray.get(i).getUsername().equals(p.getUsername()) || tempArray.get(i).getAmka().equals(p.getAmka()) || tempArray.get(i).getEmail().equals(p.getEmail())) {
						flag=true;
						break;
					}
				}
				
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				
				temp.addNewSimpleUser(p);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(flag==true) {
				response.setStatus(403);
				JsonObject jo = new JsonObject();
				jo.addProperty("error", "To onoma(email,amka) uparxei idi");
				response.getWriter().write(jo.toString());
			}else {
				response.setStatus(200);
				String JsonString = temp.simpleUserToJSON(p);
				PrintWriter out = response.getWriter();
				response.getWriter().write(JsonString);
			}
		}else {
			d = dtemp.jsonToDoctor(json1);
			
			try {
				ArrayList<Doctor> tempArray = dtemp.databaseToDoctors();
				for(int i=0; i<tempArray.size(); i++) {
					if(tempArray.get(i).getUsername().equals(d.getUsername()) || tempArray.get(i).getAmka().equals(d.getAmka()) || tempArray.get(i).getEmail().equals(d.getEmail())) {
						flag=true;
						break;
					}
				}
				
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				
				dtemp.addNewDoctor(d);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(flag==true) {
				response.setStatus(403);
				JsonObject jo = new JsonObject();
				jo.addProperty("error", "To onoma(email,amka) uparxei idi");
				response.getWriter().write(jo.toString());
			}else {
				response.setStatus(200);
				String JsonString = dtemp.doctorToJSON(d);
				PrintWriter out = response.getWriter();
				response.getWriter().write(JsonString);
			}
			
		}
		
		
		
		
		
		

	}

}
