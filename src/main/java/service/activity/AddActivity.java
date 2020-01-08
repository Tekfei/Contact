package service.activity;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ActivityDao;
import entity.Activity;
import util.ReqResUtil;

// 3.0以上使用@WebServlet注解，web.xml不再是必须
@WebServlet("/AddActivity")
public class AddActivity extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ActivityDao activityDao;

    @Override
    public void init() throws ServletException {
        activityDao = new ActivityDao();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        Activity activity = ReqResUtil.getRequestBodyEntity(request, Activity.class);

        try {
            activityDao.insertActivity(activity);
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
