package service;

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
import dao.FollowDao;

@WebServlet("/AddOrCancelFollow")
public class AddOrCancelFollow extends HttpServlet {
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
		System.out.println(builder.toString());
		
		int activeId = json.getInt("activeId");
		int passiveId = json.getInt("passiveId");
		int type = json.getInt("type");

		JSONObject result = new JSONObject();
		try {
			if (type == 0) {
				followDao.cancelAttention(activeId, passiveId);
				
			} else {
				followDao.insertFollow(activeId, passiveId);
			}
			result.put("0", 1);
		} catch (SQLException e) {
			e.printStackTrace();
			result.put("0", 0);
		}
		response.getOutputStream().write(result.toString().getBytes(StandardCharsets.UTF_8));
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
