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
import util.ReqResUtil;


@WebServlet("/DeleteTopic")
public class DeleteTopic extends HttpServlet {

    private static final long serialVersionUID = 1;
    private TopicDao topicImpl;

    @Override
    public void init() throws ServletException {
    	topicImpl = new TopicDao();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JSONObject json = JSONObject.fromObject(ReqResUtil.getRequestBodyString(request));
        String topicId = json.getString("topicId");

        try{
        	topicImpl.deleteByTopicId(topicId);
            ReqResUtil.writeResponseBooleanResult(response, true);
        } catch (Exception e) {
            e.printStackTrace();
            ReqResUtil.writeResponseBooleanResult(response, false);
        }
    }
}