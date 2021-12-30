package Assignment3;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

import database.tables.EditSimpleUserTable;
import mainClasses.SimpleUser;

/**
 * Servlet implementation class AdminLoginServlet
 */
public class AdminLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminLoginServlet() {
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
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		
		HttpSession session=request.getSession(true);
		System.out.println("MPIKA EDW!!!@!!!!!!");
		EditSimpleUserTable temp =new EditSimpleUserTable();
		try {
			SimpleUser sUser=temp.databaseToSimpleUser(username, password);
			System.out.println(username);
			System.out.println(password);
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			if(temp.databaseToSimpleUser(username, password)!=null && password.equals("admin12*") && username.equals("admin")) {
				response.setStatus(200);
				JsonObject jo = new JsonObject();
				jo.addProperty("OK", "You are loggedin as admin");
				response.getWriter().write(jo.toString());
				session.setAttribute("loggedIn",username);
			}
			else{
				response.setStatus(404);
				JsonObject jo = new JsonObject();
				jo.addProperty("error", "O xristis admin den uparxei");
				response.getWriter().write(jo.toString());
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
