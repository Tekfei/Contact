package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import dao.MessageDao;
import entity.Message;
import net.sf.json.JsonConfig;
import util.DateJsonValueProcessor;

@WebServlet("/QueryMessages")
public class QueryMessages extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MessageDao messageDao;

	@Override
	public void init() throws ServletException {
		messageDao = new MessageDao();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		BufferedReader br = new BufferedReader(new InputStreamReader(
				request.getInputStream(), StandardCharsets.UTF_8));
		String str;
		StringBuilder builder = new StringBuilder();
		while ((str = br.readLine()) != null) {
			builder.append(str);
		}
		br.close();

		JSONObject json = JSONObject.fromObject(builder.toString());
		int userId = json.getInt("userId");

		JSONObject result = new JSONObject();
		try {
			List<Message> messages = messageDao.queryMessage(userId);
			result.put("0", 1);
			result.put("1", messages);
		} catch (SQLException e) {
			result.put("0", 0);
			e.printStackTrace();
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
