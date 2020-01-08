package service.comment;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import dao.CommentDao;
import util.ReqResUtil;


@WebServlet("/DeleteComment")
public class DeleteComment extends HttpServlet {

    private static final long serialVersionUID = 1;
    private CommentDao commentImpl;

    @Override
    public void init() throws ServletException {
    	commentImpl = new CommentDao();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JSONObject json = JSONObject.fromObject(ReqResUtil.getRequestBodyString(request));
        String commentId = json.getString("commentId");

        try{
        	commentImpl.deleteCommentById(commentId);
        	ReqResUtil.writeResponseBooleanResult(response, true);
        } catch (Exception e) {
            e.printStackTrace();
            ReqResUtil.writeResponseBooleanResult(response, true);
        }
    }
}