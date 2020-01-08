package service.topic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
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
import dao.LikeDao;
import util.ReqResUtil;

@WebServlet("/QueryAllLikedTopicByUserId")
public class QueryAllLikedTopicByUserId extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private LikeDao likeDao;
    private TopicDao topicDao;

    @Override
    public void init() throws ServletException {
    	likeDao = new LikeDao();
    	topicDao = new TopicDao();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        
        JSONObject json = JSONObject.fromObject(ReqResUtil.getRequestBodyString(request));
        String userId = json.getString("userId");
        List<Topic> topicList = new ArrayList<>();
        try {
            List<String> topicIdList = likeDao.queryLikedTopicIdByUserId(userId);
            for(String topicId : topicIdList){
                Topic topic = topicDao.queryByTopicId(topicId);
                topicList.add(topic);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	response.getOutputStream().write(JSON.toJSONString(topicList).getBytes(StandardCharsets.UTF_8));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
