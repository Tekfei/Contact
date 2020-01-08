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
 * 修改Circle状态
 */
@WebServlet("/UpdateCircleStatus")
public class UpdateCircleStatus extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CircleDao circleDao;

    @Override
    public void init() throws ServletException {
        circleDao = new CircleDao();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        JSONObject json = JSONObject.fromObject(ReqResUtil.getRequestBodyString(request));

        int state = json.getInt("state");
        String id = json.getString("id");

        Circle circle = new Circle();
        circle.setState(state);
        circle.setId(id);

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
