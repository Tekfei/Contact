package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.MailUtils;

import net.sf.json.JSONObject;
import dao.FeedBackDao;

@WebServlet("/FeedBack")
public class FeedBack extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private FeedBackDao feedBackDao;

    @Override
    public void init() throws ServletException {
    	feedBackDao = new FeedBackDao();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        BufferedReader br = new BufferedReader(new InputStreamReader(
                request.getInputStream(), StandardCharsets.UTF_8));
        
        String str;
        StringBuilder builder = new StringBuilder();
        while ((str = br.readLine()) != null) {
            builder.append(str);
        }
        br.close();
        
        JSONObject json = JSONObject.fromObject(builder.toString());
        int userId = json.getInt("userId");
        String issue = json.getString("issue");
        JSONObject result = new JSONObject();
        try {
            feedBackDao.insertIssue(userId, issue);
            result.put("0", 1);
        } catch (SQLException e) {
            e.printStackTrace();
            result.put("0",0);
        }finally{
        	response.getOutputStream().write(result.toString().getBytes(StandardCharsets.UTF_8));
    		MailUtils ma = new MailUtils();
    		try {
    			ma.sentIssueMail("2392737027@qq.com",issue);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
