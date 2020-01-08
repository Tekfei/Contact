package service.comment;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CommentDao;

import net.sf.json.JSONObject;
import util.ReqResUtil;

//3.0以上使用@WebServlet注解，web.xml不再是必须；"/AddComment": url路径
@WebServlet("/CountComment")
public class CountComment extends HttpServlet {
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

		JSONObject jo = new JSONObject();
		int commentNum;
		try {
			commentNum = commentDao.countComment(topicId);
			jo.put("0", 1);
			jo.put("1", commentNum);
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("0", 0);
		}
		response.getOutputStream().write(jo.toString().getBytes(StandardCharsets.UTF_8));
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}
