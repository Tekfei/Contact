package service.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import dao.UserDao;

import entity.User;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import util.DateJsonValueProcessor;
import util.ReqResUtil;


@WebServlet("/QueryUser")
public class QueryUser extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private UserDao userDao;
	@Override
	public void init() throws ServletException {
		userDao = new UserDao();
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		JSONObject json = JSONObject.fromObject(ReqResUtil.getRequestBodyString(request));
		String userId = json.getString("userId");

		User user = userDao.queryByUserId(userId);
		String jsonStr = JSON.toJSONString(user);
		response.getOutputStream().write(jsonStr.getBytes(StandardCharsets.UTF_8));
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}
