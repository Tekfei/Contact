package util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import net.sf.json.*;


public class HttpConnecter {

    public static void httpPost(String name,String password){

        HttpURLConnection urlConnection = null;
        URL url;
        try {
            JSONObject json = new JSONObject();
            json.put("name", URLEncoder.encode(name, "UTF-8"));
            json.put("password", URLEncoder.encode(password, "UTF-8"));
            String jsonStr = json.toString();
            url = new URL("http://localhost:8080/contact/Login");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(3000);
            urlConnection.setUseCaches(false);
            urlConnection.setInstanceFollowRedirects(true);
            urlConnection.setReadTimeout(3000);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Confession-Type", "application/json;charset=UTF-8");
            System.out.println(1);
            urlConnection.connect();
            System.out.println(2);
            OutputStream out = urlConnection.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
            bw.write(jsonStr);
            bw.flush();
            out.close();
            bw.close();
            if(urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                InputStream in = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String str;
                StringBuilder s = new StringBuilder();
                while((str = br.readLine())!=null){
                    s.append(str);
                }
                in.close();
                br.close();
                JSONObject jo = JSONObject.fromObject(s.toString());
                boolean result = jo.getBoolean("json");
                if(result){
                	System.out.println(3);
                }else{
                	System.out.println(4);
                }
            }else{
            	System.out.println(5);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }finally{
            assert urlConnection != null;
            urlConnection.disconnect();
        }

    }
}
