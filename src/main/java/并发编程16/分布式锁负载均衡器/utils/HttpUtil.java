package 并发编程16.分布式锁负载均衡器.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class HttpUtil {

    /**
     * 模拟get请求
     */
    public static String doGet(String geturl) {
        String realUrl = geturl;
        System.out.println(realUrl);
        try {
            URL url = new URL(realUrl);
            URLConnection conn = url.openConnection();
            conn.setUseCaches(false);
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded;charset=UTF-8");
            conn.connect();

            Map<String, List<String>> headers = conn.getHeaderFields();
            System.out.println(headers);
            StringBuffer resultBuffer = new StringBuffer();
            String tempLine = null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }
            reader.close();
            return resultBuffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

}