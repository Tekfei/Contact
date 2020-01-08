package service.circle;

import dao.CircleDao;
import entity.Circle;
import net.sf.json.JSONObject;
import util.ReqResUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户申请成为圈子管理员
 */
@WebServlet("/ApplyCircleManager")
public class ApplyCircleManager extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CircleDao circleDao;

    @Override
    public void init() throws ServletException {
        circleDao = new CircleDao();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        JSONObject body = JSONObject.fromObject(ReqResUtil.getRequestBodyString(request));
        String circleId = body.getString("circleId");
        String userId = body.getString("userId");

        Circle circle = new Circle();
        circle.setId(circleId);
        circle.setUserId(userId);
        circle.setState(4);

        try {
            circleDao.updateCircle(circle);
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
