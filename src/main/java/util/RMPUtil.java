package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import daoentity.ActivityEntity;
import daoentity.ContactEntity;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class RMPUtil {

    private static final String baseUrl = "http://39.106.54.106:8080/Entity/Ueb9402a1ec212/contact/";
    //表名
    public static final String Activity = "Activity";
    public static final String Circle = "Circle";
    public static final String Topic = "Topic";
    public static final String User = "User";
    public static final String Participate = "Participate";
    public static final String Card = "Card";
    public static final String Like = "Like";
    public static final String Comment = "Comment";
    public static final String Contactapply = "Contactapply";
    public static final String Contact = "Contact";
    public static final String Encounter = "Encounter";
    public static final String Theme = "Theme";

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .build();

    public static <T> T get(String url, Class<T> clazz){
        if (StringUtils.isEmpty(url)) {
            return null;
        }

        Request request = new Request.Builder().url(url).build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return JSON.parseObject(response.body().string(), clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    public static <T> List<T> get(String url, Map<String, String> queries, Class<T> clazz){
        if (StringUtils.isEmpty(url)) {
            return new ArrayList<>();
        }

        StringBuffer sb = new StringBuffer(url);
        if (queries != null && queries.keySet().size() > 0) {
            boolean firstFlag = true;
            Iterator iterator = queries.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry<String, String>) iterator.next();
                if (firstFlag) {
                    sb.append("?" + entry.getKey() + "=" + entry.getValue());
                    firstFlag = false;
                } else {
                    sb.append("&" + entry.getKey() + "=" + entry.getValue());
                }
            }
        }
        Request request = new Request.Builder().url(sb.toString()).build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String body = response.body().string();
                String tableName = clazz.getName();
                String[] splitArray = tableName.split("\\.");
                tableName = splitArray[splitArray.length-1];
                tableName = tableName.replace("Entity", "");
                return JSON.parseArray(getJsonArrayFromJsonObject(body, tableName), clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return new ArrayList<>();
    }

    public static String insert(String url, Object object){
        String str = JSON.toJSONString(object);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    public static boolean modify(String url, Object object){
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSON.toJSONString(object));
        Request request = new Request.Builder()
                .url(url)
                .put(requestBody)
                .build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return false;
    }

    public static boolean delete(String url){
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return false;
    }

    /**
     * 得到某个表的基础url
     */
    public static String tableUrl(String tableName, String id){
        StringBuffer sb = new StringBuffer(baseUrl);
        sb.append(tableName).append("/");
        if(id != null){
            sb.append(id);
        }
        return sb.toString();
    }

    public static String tableUrl(String tableName){
        StringBuffer sb = new StringBuffer(baseUrl);
        sb.append(tableName).append("/");
        return sb.toString();
    }

    private static String getJsonArrayFromJsonObject(String jsonArrayStr, String fieldName){
        JSONObject object = JSON.parseObject(jsonArrayStr);
        JSONArray arrayObject = object.getJSONArray(fieldName);
        if(arrayObject == null){
            return "[]";
        }
        return arrayObject.toJSONString();
    }
}
