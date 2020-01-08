package service.activity;

import com.alibaba.fastjson.JSON;
import dao.ActivityDao;
import entity.Activity;
import net.sf.json.JSONObject;
import util.ReqResUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 查询某个circle下的Activity列表
 */
@WebServlet("/QueryActivityByCircleId")
public class QueryActivityByCircleId extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ActivityDao activityDao;

    @Override
    public void init() throws ServletException {
        activityDao = new ActivityDao();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        JSONObject json = JSONObject.fromObject(ReqResUtil.getRequestBodyString(request));

        String circleId = json.getString("circleId");

        List<Activity> activityList = activityDao.queryActivityByCircleId(circleId);
        String jsonStr = JSON.toJSONString(activityList);
        response.getOutputStream().write(jsonStr.getBytes(StandardCharsets.UTF_8));
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

}
