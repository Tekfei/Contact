package util;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ReqResUtil {

    public static <T> T getRequestBodyEntity(HttpServletRequest request, Class<T> clazz) throws IOException {
        return JSON.parseObject(getRequestBodyString(request), clazz);
    }

    public static String getRequestBodyString(HttpServletRequest request) throws IOException{
        request.setCharacterEncoding("UTF-8");
        BufferedReader br = new BufferedReader(new InputStreamReader(
                request.getInputStream(), StandardCharsets.UTF_8));
        String str;
        StringBuilder builder = new StringBuilder();
        while ((str = br.readLine()) != null) {
            builder.append(str);
        }
        br.close();
        return builder.toString();
    }

    public static void writeResponseBooleanResult(HttpServletResponse response, boolean result) throws IOException{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        JSONObject jsonResult = new JSONObject();
        jsonResult.put("0", result? 1: 0);
        response.getOutputStream().write(jsonResult.toString().getBytes(StandardCharsets.UTF_8));
    }

}
