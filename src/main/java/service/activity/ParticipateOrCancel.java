package service.activity;

import dao.ActivityDao;
import dao.ParticipateDao;
import daoentity.ParticipateEntity;
import entity.Activity;
import net.sf.json.JSONObject;
import util.ReqResUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户参加活动
 */
@WebServlet("/ParticipateOrCancel")
public class ParticipateOrCancel extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ParticipateDao participateDao;

    @Override
    public void init() throws ServletException {
        participateDao = new ParticipateDao();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        JSONObject json = JSONObject.fromObject(ReqResUtil.getRequestBodyString(request));
        int type = json.getInt("type");
        String userId = json.getString("userId");
        String activityId = json.getString("activityId");

        try {
            if (type == 1){
                participateDao.insertParticipate(activityId, userId);
            }else{
                participateDao.deleteParticipate(activityId, userId);
            }
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
