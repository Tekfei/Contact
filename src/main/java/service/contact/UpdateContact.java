package service.contact;

import dao.ContactDao;
import entity.Contact;
import util.ReqResUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 更新好友信息，如修改分组
 */
@WebServlet("/UpdateContact")
public class UpdateContact extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ContactDao contactDao;

    @Override
    public void init() throws ServletException {
        contactDao = new ContactDao();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        Contact contact = ReqResUtil.getRequestBodyEntity(request, Contact.class);

        try {
            contactDao.updateContact(contact);
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
