package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Topic;
import net.sf.json.JSONObject;
import dao.CollectDao;
import dao.CommentDao;
import dao.TopicDao;
import dao.UserDao;
import net.sf.json.JsonConfig;
import util.DateJsonValueProcessor;

@WebServlet("/QueryCollection")
public class QueryCollection extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CollectDao collectDao;
	private TopicDao topicDao;

	@Override
	public void init() throws ServletException {
		collectDao = new CollectDao();
		topicDao = new TopicDao();
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

		List<Topic> topicList = new ArrayList<>();
		JSONObject result = new JSONObject();
		try {
			List<Integer> integers = collectDao.queryCollection(userId);

			int[] commentNumArray;

			for (Integer integer : integers) {
				Topic collectedTopicMap = topicDao.queryByTopicId(String.valueOf(integer));
				topicList.add(collectedTopicMap);
			}
			int topicListSize = topicList.size();

			result.put("0", 1);
			result.put("1", topicList);
		} catch (SQLException e) {
			e.printStackTrace();
			result.put("0", 0);
		} finally {
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor());
			result = JSONObject.fromObject(result, jsonConfig);
			response.getOutputStream().write(
					result.toString().getBytes(StandardCharsets.UTF_8));
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
