package service.contact;

import com.alibaba.fastjson.JSON;
import dao.ContactDao;
import entity.Contact;
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
 * 查询用户接收到的好友申请列表
 */
@WebServlet("/QueryContactApplyByContactId")
public class QueryContactApplyByContactId extends HttpServlet {
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

        List<Contact> contactList = contactDao.queryContactApplyByContactId(userId);
        String jsonStr = JSON.toJSONString(contactList);
        response.getOutputStream().write(jsonStr.getBytes(StandardCharsets.UTF_8));
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

}
