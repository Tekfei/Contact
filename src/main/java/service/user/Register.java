package service.user;

import dao.UserDao;
import entity.User;
import net.sf.json.JSONObject;
import util.MailUtils;
import util.ReqResUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

// 3.0以上使用@WebServlet注解，web.xml不再是必须；"/AddTopic": url路径
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserDao userDao;

	@Override
	public void init() throws ServletException {
		userDao = new UserDao();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User user = ReqResUtil.getRequestBodyEntity(request, User.class);
		MailUtils mailUtils = new MailUtils();

		JSONObject result = new JSONObject();

		User possibleUser = userDao.login(user.getMail());
		if (possibleUser != null && possibleUser.getMail() != null) {
			result.put("0", -1);  // 邮箱已经注册过了
			response.getOutputStream().write(result.toString().getBytes(StandardCharsets.UTF_8));
			return;
		}
		try {
			userDao.insertUser(user);
			result.put("0", 1);  // 新增临时用户成功，发送激活邮件成功
//			try { // 新增临时用户成功
//				mailUtils.sentSignUpMail(user.getMail());
//			} catch (Exception e) {
//				e.printStackTrace(); // 新增临时用户成功，发送激活邮件失败
//				try {
//					userDao.deleteUser(user.getMail());
//					result.put("0", -2); // 新增临时用户成功，发送激活邮件失败，删除新增临时用户成功
//				} catch (Exception e1) {
//					e1.printStackTrace();
//					result.put("0", -3); // 新增临时用户成功，发送激活邮件失败，删除新增临时用户失败 // TODO：此时数据库出现不一致性
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("0", 0); // 新增用户失败
		}
		response.getOutputStream().write(result.toString().getBytes(StandardCharsets.UTF_8));
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	public static void main(String[] args) {
		String string ="test";
		System.out.println(string.length());

	}

}
