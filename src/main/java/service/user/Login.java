package service.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.User;
import net.sf.json.JSONObject;
import dao.UserDao;
import net.sf.json.JsonConfig;
import util.DateJsonValueProcessor;
import util.ReqResUtil;

// 3.0以上使用@WebServlet注解，web.xml不再是必须；"/Login": url路径
@WebServlet("/Login")

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
	@Override
	public void init() throws ServletException {
		userDao = new UserDao();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject json = JSONObject.fromObject(ReqResUtil.getRequestBodyString(request));

		String mail = json.getString("mail");
		String password = json.getString("password");

		User user = userDao.login(mail);
		JSONObject jo;
		if(user == null || user.getName()==null){ // 邮箱未注册
			jo = new JSONObject();
			jo.put("access", -1);
			response.getOutputStream().write(jo.toString().getBytes(StandardCharsets.UTF_8));
//		}else if(!user.isActivated()){ // 邮箱未激活
//			jo = new JSONObject();
//			jo.put("access", -2);
//			response.getOutputStream().write(jo.toString().getBytes(StandardCharsets.UTF_8));
		}else if(!password.equals(user.getPassword())){ // 密码不正确
			jo = new JSONObject();
			jo.put("access", 0);
			response.getOutputStream().write(jo.toString().getBytes(StandardCharsets.UTF_8));
		}
		else { // 成功登陆
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor());
			jo = JSONObject.fromObject(user, jsonConfig);
			jo.put("access", 1);
			response.getOutputStream().write(jo.toString().getBytes(StandardCharsets.UTF_8));
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}
