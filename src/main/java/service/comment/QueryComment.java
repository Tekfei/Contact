package service.comment;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import entity.Comment;
import net.sf.json.JSONObject;
import dao.CommentDao;
import util.ReqResUtil;

@WebServlet("/QueryComment")
public class QueryComment extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private CommentDao commentDao;

	@Override
	public void init() throws ServletException {
		commentDao = new CommentDao();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		JSONObject json = JSONObject.fromObject(ReqResUtil.getRequestBodyString(request));
		String topicId = json.getString("topicId");

		List<Comment> commentList = commentDao.queryComment(topicId);
		String jsonStr = JSON.toJSONString(commentList);
		response.getOutputStream().write(jsonStr.getBytes(StandardCharsets.UTF_8));
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
