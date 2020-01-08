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
import dao.TopicDao;
import entity.Topic;
import util.ReqResUtil;

// 3.0以上使用@WebServlet注解，web.xml不再是必须；"/AddTopic": url路径
@WebServlet("/AddTopic")
public class AddTopic extends HttpServlet {
	private static final long serialVersionUID = 1L;


    private TopicDao topicDao;

    @Override
    public void init() throws ServletException {
    	topicDao = new TopicDao();
    }
    
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Topic topic = ReqResUtil.getRequestBodyEntity(request, Topic.class);
		try {
			topicDao.insertTopic(topic);
			ReqResUtil.writeResponseBooleanResult(response, true);
		} catch (Exception e) {
			e.printStackTrace();
			ReqResUtil.writeResponseBooleanResult(response, false);
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
