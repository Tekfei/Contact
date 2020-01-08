package service.comment;

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
import dao.CommentDao;
import entity.Comment;
import util.ReqResUtil;

//3.0以上使用@WebServlet注解，web.xml不再是必须；"/AddComment": url路径
@WebServlet("/AddComment")
public class AddComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    private CommentDao commentDao;

    @Override
    public void init() throws ServletException {
    	commentDao = new CommentDao();
    }

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Comment comment = ReqResUtil.getRequestBodyEntity(request, Comment.class);

		try {
			commentDao.insertComment(comment);
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
