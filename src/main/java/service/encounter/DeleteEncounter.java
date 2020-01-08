package service.encounter;

import dao.EncounterDao;
import net.sf.json.JSONObject;
import util.ReqResUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 删除相遇历史
 */
@WebServlet("/DeleteEncounter")
public class DeleteEncounter extends HttpServlet {
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

        try {
            encounterDao.deleteEncounter(encounterId);
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
