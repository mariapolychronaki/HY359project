package Assignment3;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import database.tables.EditBloodTestTable;
import database.tables.EditSimpleUserTable;
import mainClasses.BloodTest;
import mainClasses.JSON_Converter;
import mainClasses.SimpleUser;

/**
 * Servlet implementation class NewBloodTestServlet
 */
public class NewBloodTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public NewBloodTestServlet() {
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
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();  
		JSON_Converter jc = new JSON_Converter();
		BufferedReader inputJSONfromClient=request.getReader();
		String json1  = jc.getJSONFromAjax(inputJSONfromClient);
		JsonObject jo = new JsonObject();
		
		
		EditBloodTestTable temp =new EditBloodTestTable();
		BloodTest lap =new BloodTest();
		lap=temp.jsonToBloodTest(json1);
		
		System.out.println(lap.getBlood_sugar());
		
		Date d1=null;
		try {
			d1 = sdformat.parse(lap.getTest_date());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (lap.getAmka()==null || lap.getTest_date()==null || lap.getMedical_center()== null) {
			response.setStatus(400);
			jo.addProperty("error","Blood test details are missing");
			response.getWriter().write(jo.toString());
		}else if (lap.getBlood_sugar()==0 && lap.getCholesterol()==0 && lap.getVitamin_b12()==0 && lap.getVitamin_d3()==0 ) {
			response.setStatus(400);
			jo.addProperty("error","Blood test details are wrong");
			response.getWriter().write(jo.toString());
		}else if (lap.getBlood_sugar()<0 || lap.getCholesterol()<0 || lap.getVitamin_b12()<0 || lap.getVitamin_d3()<0 ) {
			response.setStatus(400);
			jo.addProperty("error","Blood test details are negative");
			response.getWriter().write(jo.toString());
		}else if (d1.compareTo(date)>0) {
			response.setStatus(400);
			jo.addProperty("error","Test date is wrong");
			response.getWriter().write(jo.toString());
		}
		else {
			try {
				temp.createNewBloodTest(lap);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.setStatus(200);
			jo.addProperty("OK","Blood test is created");
			response.getWriter().write(jo.toString());
		}
		
	}

}
