package service.circle;

import dao.CircleDao;
import entity.Circle;
import util.ReqResUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户申请创建圈子
 * cicle实体中的state设置为0未审核
 */
@WebServlet("/CreateCircle")
public class CreateCircle extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CircleDao circleDao;

    @Override
    public void init() throws ServletException {
        circleDao = new CircleDao();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        Circle circle = ReqResUtil.getRequestBodyEntity(request, Circle.class);

        try {
            circleDao.insertCircle(circle);
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
