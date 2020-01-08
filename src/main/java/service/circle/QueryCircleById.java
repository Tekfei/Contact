package service.circle;

import com.alibaba.fastjson.JSON;
import dao.CircleDao;
import entity.Activity;
import entity.Circle;
import net.sf.json.JSONObject;
import util.ReqResUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 用id查询Circle实体
 */
@WebServlet("/QueryCircleById")
public class QueryCircleById extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CircleDao circleDao;

    @Override
    public void init() throws ServletException {
        circleDao = new CircleDao();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        JSONObject json = JSONObject.fromObject(ReqResUtil.getRequestBodyString(request));

        String circleId = json.getString("circleId");

        Circle circle = circleDao.queryCircleById(circleId);
        String jsonStr = JSON.toJSONString(circle);
        response.getOutputStream().write(jsonStr.getBytes(StandardCharsets.UTF_8));
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

}
