package service.theme;

import com.alibaba.fastjson.JSON;
import dao.ThemeDao;
import entity.Theme;
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
 * 用id查询Theme实体
 */
@WebServlet("/QueryAllTheme")
public class QueryAllTheme extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ThemeDao ThemeDao;

    @Override
    public void init() throws ServletException {
        ThemeDao = new ThemeDao();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        List<Theme> themeList = ThemeDao.queryAllTheme();
        String jsonStr = JSON.toJSONString(themeList);
        response.getOutputStream().write(jsonStr.getBytes(StandardCharsets.UTF_8));
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

}
