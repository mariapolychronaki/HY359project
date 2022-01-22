package Assignment3;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import database.tables.EditBloodTestTable;
import database.tables.EditDoctorTable;
import mainClasses.BloodTest;
import mainClasses.Doctor;

/**
 * Servlet implementation class CompareBloodTestsServlet
 */
public class CompareBloodTestsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompareBloodTestsServlet() {
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

		EditBloodTestTable bdt = new EditBloodTestTable();
		ArrayList<BloodTest> array = null;
		ArrayList<JsonObject> array1 = new ArrayList<JsonObject>();
		
		try {
			array=bdt.databaseToBloodTests();
			
			for(int i=0; i<array.size(); i++) {
				JsonObject jo = new JsonObject();
				jo.addProperty("amka", array.get(i).getAmka());
				jo.addProperty("test_date", array.get(i).getTest_date());
				jo.addProperty("medical_center", array.get(i).getMedical_center());
				jo.addProperty("vitamin_d3", array.get(i).getVitamin_d3());
				jo.addProperty("vitamin_d3_level", array.get(i).getVitamin_d3_level());
				jo.addProperty("vitamin_b12", array.get(i).getVitamin_b12());
				jo.addProperty("vitamin_b12_level", array.get(i).getVitamin_b12_level());
				jo.addProperty("cholesterol", array.get(i).getCholesterol());
				jo.addProperty("cholesterol_level", array.get(i).getCholesterol_level());
				jo.addProperty("blood_sugar", array.get(i).getBlood_sugar());
				jo.addProperty("blood_sugar_level", array.get(i).getBlood_sugar_level());
				jo.addProperty("iron", array.get(i).getIron());
				jo.addProperty("iron_level", array.get(i).getIron_level());
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
