package service.encounter;

import com.alibaba.fastjson.JSON;
import dao.ContactDao;
import dao.EncounterDao;
import entity.Contact;
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
 * 根据id查询相遇历史
 */
@WebServlet("/QueryEncounterById")
public class QueryEncounterById extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private EncounterDao encounterDao;

    @Override
    public void init() throws ServletException {
        encounterDao = new EncounterDao();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        JSONObject json = JSONObject.fromObject(ReqResUtil.getRequestBodyString(request));

        String encounterId = json.getString("encounterId");

        Encounter encounter = encounterDao.queryEncounterById(encounterId);
        String jsonStr = JSON.toJSONString(encounter);
        response.getOutputStream().write(jsonStr.getBytes(StandardCharsets.UTF_8));
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

}
