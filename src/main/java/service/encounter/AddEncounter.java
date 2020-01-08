package service.encounter;

import dao.EncounterDao;
import entity.Encounter;
import util.ReqResUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户自行创建相遇历史
 */
@WebServlet("/AddEncounter")
public class AddEncounter extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private EncounterDao encounterDao;

    @Override
    public void init() throws ServletException {
        encounterDao = new EncounterDao();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        Encounter encounter = ReqResUtil.getRequestBodyEntity(request, Encounter.class);

        try {
            encounterDao.insertEncounter(encounter);
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
