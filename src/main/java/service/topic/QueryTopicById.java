package service.topic;

import com.alibaba.fastjson.JSON;
import dao.TopicDao;
import entity.Topic;
import net.sf.json.JSONObject;
import util.ReqResUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/QueryTopicById")
public class QueryTopicById extends HttpServlet {

	private static final long serialVersionUID = 1L;

    private TopicDao topicDao;

    @Override
    public void init() throws ServletException {
    	topicDao = new TopicDao();
    }

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		JSONObject json = JSONObject.fromObject(ReqResUtil.getRequestBodyString(request));

		String topicId = json.getString("topicId");

		try {
			Topic topic = topicDao.queryByTopicId(topicId);
			String jsonStr = JSON.toJSONString(topic);
			response.getOutputStream().write(jsonStr.getBytes(StandardCharsets.UTF_8));
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
