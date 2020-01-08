package service.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import util.MailUtils;
import dao.UserDao;
import util.ReqResUtil;

@WebServlet("/ForgetPassword")
public class ForgetPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	@Override
	public void init() throws ServletException {
		userDao = new UserDao();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		JSONObject json = JSONObject.fromObject(ReqResUtil.getRequestBodyString(request));
		String mail = json.getString("mail");
		try {
			String pw = userDao.queryPassword(mail);
			MailUtils ma = new MailUtils();
			ma.sentForgetPasswordMail(mail,pw);
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
