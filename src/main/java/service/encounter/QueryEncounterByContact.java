package service.encounter;

import com.alibaba.fastjson.JSON;
import dao.EncounterDao;
import entity.Encounter;
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
 * 查询跟某个好友的相遇历史列表
 */
@WebServlet("/QueryEncounterByContact")
public class QueryEncounterByContact extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private EncounterDao encounterDao;

    @Override
    public void init() throws ServletException {
        encounterDao = new EncounterDao();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        JSONObject json = JSONObject.fromObject(ReqResUtil.getRequestBodyString(request));

        String userId = json.getString("userId");
        String contactId = json.getString("contactId");

        List<Encounter> encounterList = encounterDao.queryEncounterByContact(userId, contactId);
        String jsonStr = JSON.toJSONString(encounterList);
        response.getOutputStream().write(jsonStr.getBytes(StandardCharsets.UTF_8));
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

}
