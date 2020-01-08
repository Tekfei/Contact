package service.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import dao.UserDao;
import entity.User;
import util.ReqResUtil;

@WebServlet("/UpdateUser")
public class UpdateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserDao userDao;
	@Override
	public void init() throws ServletException {
		userDao = new UserDao();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User user = ReqResUtil.getRequestBodyEntity(request, User.class);

		try {
			userDao.updateUser(user);
			ReqResUtil.writeResponseBooleanResult(response, true);
		} catch (Exception e) {
			e.printStackTrace();
			ReqResUtil.writeResponseBooleanResult(response, false);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}
