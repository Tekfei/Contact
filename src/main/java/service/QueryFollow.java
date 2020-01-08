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
import dao.FollowDao;
import entity.Follow;
import net.sf.json.JsonConfig;
import util.DateJsonValueProcessor;

@WebServlet("/QueryFollow")
public class QueryFollow extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FollowDao followDao;

	@Override
	public void init() throws ServletException {
		followDao = new FollowDao();
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
		int type = json.getInt("type"); //0: 关注我的人，1：我关注的人

		JSONObject result = new JSONObject();
		try {
			List<Follow> follows;
			if (type == 0) {
				follows = followDao.getPassiveFollow(userId);
			} else {
				follows = followDao.getFollow(userId);
			}
			result.put("0", 1);
			result.put("1", follows);
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
