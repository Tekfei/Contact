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
 * 修改Activity的置顶状态
 * popularizeState为推广状态，0为未推广，1为已推广，2为申请推广中
 */
@WebServlet("/UpdateActivityIsTop")
public class UpdateActivityIsTop extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ActivityDao activityDao;

    @Override
    public void init() throws ServletException {
        activityDao = new ActivityDao();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        JSONObject json = JSONObject.fromObject(ReqResUtil.getRequestBodyString(request));

        int popularizeState = json.getInt("popularizeState");
        String id = json.getString("id");
        String popularizeThemeId = null;
        try {
            //如果是申请推广，要填推广在哪个theme主题广场
            popularizeThemeId = json.getString("popularizeThemeId");
        }catch (Exception e){
        }

        try {
            activityDao.updatePopularizeState(popularizeState, id, popularizeThemeId);
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
