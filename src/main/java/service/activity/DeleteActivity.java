package service.activity;

import dao.ActivityDao;
import net.sf.json.JSONObject;
import util.ReqResUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 删除Activity
 */
@WebServlet("/DeleteActivity")
public class DeleteActivity extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ActivityDao activityDao;

    @Override
    public void init() throws ServletException {
        activityDao = new ActivityDao();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        JSONObject json = JSONObject.fromObject(ReqResUtil.getRequestBodyString(request));
        String activityId = json.getString("activityId");

        try {
            activityDao.deleteByActivityId(activityId);
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
