package service.contact;

import dao.ContactDao;
import net.sf.json.JSONObject;
import util.ReqResUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 删除好友
 */
@WebServlet("/DeleteContact")
public class DeleteContact extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ContactDao contactDao;

    @Override
    public void init() throws ServletException {
        contactDao = new ContactDao();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        JSONObject json = JSONObject.fromObject(ReqResUtil.getRequestBodyString(request));

        String userId = json.getString("userId");
        String contactId = json.getString("contactId");

        try {
            contactDao.deleteContact(userId, contactId);
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
