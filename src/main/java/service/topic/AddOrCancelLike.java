package service.topic;

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
import dao.LikeDao;
import util.ReqResUtil;

@WebServlet("/AddOrCancelLike")
public class AddOrCancelLike extends HttpServlet {

	private static final long serialVersionUID = 1;
	private LikeDao likeDao;

	@Override
	public void init() throws ServletException {
		likeDao = new LikeDao();
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JSONObject json = JSONObject.fromObject(ReqResUtil.getRequestBodyString(request));
		int type = json.getInt("type");
		String cId = json.getString("topicId");
		String userId = json.getString("userId");

		try {
			if (type == 1)
				likeDao.insertRecord(userId, cId);
			else
				likeDao.deleteRecord(userId, cId);
			ReqResUtil.writeResponseBooleanResult(response, true);
		} catch (Exception e) {
			e.printStackTrace();
			ReqResUtil.writeResponseBooleanResult(response, false);
		}
	}

}