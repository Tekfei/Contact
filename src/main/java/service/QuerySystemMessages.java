package service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import dao.SystemMessageDao;
import entity.SystemMessage;
import net.sf.json.JsonConfig;
import util.DateJsonValueProcessor;

@WebServlet("/QuerySystemMessages")
public class QuerySystemMessages extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SystemMessageDao systemMessageDao;

	@Override
	public void init() throws ServletException {
		systemMessageDao = new SystemMessageDao();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");

		JSONObject result = new JSONObject();
		try {
			List<SystemMessage> systemMessages = systemMessageDao.getSystemMessage();
			result.put("0", 1);
			result.put("1", systemMessages);
		} catch (SQLException e) {
			e.printStackTrace();
			result.put("0", 0);
		} finally {
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor());
			result = JSONObject.fromObject(result, jsonConfig);
			response.getOutputStream().write(result.toString().getBytes(StandardCharsets.UTF_8));
		}
	}
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
