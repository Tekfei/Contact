package service.topic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import dao.TopicDao;

import entity.Topic;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import util.ReqResUtil;

@WebServlet("/QueryTopic")
public class QueryTopic extends HttpServlet {

	private static final long serialVersionUID = 1L;

    private TopicDao topicDao;
		
    @Override
    public void init() throws ServletException {
    	topicDao = new TopicDao();
    }
    
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		JSONObject json = JSONObject.fromObject(ReqResUtil.getRequestBodyString(request));

		String userId = null,belongId = null;
		Integer belongType = null;

		try {
			userId = json.getString("userId");
		}catch (Exception e){
			belongId = json.getString("belongId");
			belongType = json.getInt("belongType");
		}

		try {
			List<Topic> listTopic = null;
			if (StringUtils.isEmpty(userId) || userId.equals("-1")) {
				listTopic = topicDao.queryTopicByBelongId(belongId, belongType);
			} else {
				listTopic = topicDao.queryTopicByUserId(userId);
			}
			String jsonStr = JSON.toJSONString(listTopic);
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
