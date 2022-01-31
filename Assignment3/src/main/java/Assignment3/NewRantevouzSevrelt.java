package Assignment3;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.tables.EditDoctorTable;
import database.tables.EditRandevouzTable;
import database.tables.EditSimpleUserTable;
import mainClasses.Doctor;
import mainClasses.JSON_Converter;
import mainClasses.Randevouz;

/**
 * Servlet implementation class NewRantevouzSevrelt
 */
public class NewRantevouzSevrelt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewRantevouzSevrelt() {
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
		BufferedReader inputJSONfromClient = request.getReader();
		String json1 = jc.getJSONFromAjax(inputJSONfromClient);
		EditRandevouzTable temp = new EditRandevouzTable();
		EditDoctorTable dtemp = new EditDoctorTable();
		Randevouz r;
		Doctor d;
		boolean flag = false;

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		if (json1.contains("\"Doctors\"")) {
			d = dtemp.jsonToDoctor(json1);
			ArrayList<Doctor> tempArray;
			try {
				tempArray = dtemp.databaseToDoctors();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
