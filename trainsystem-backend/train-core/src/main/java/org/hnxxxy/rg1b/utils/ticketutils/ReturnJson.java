package org.hnxxxy.rg1b.utils.ticketutils;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class ReturnJson {

    public static String returnJson(String urlString) throws Exception {
        IgnoreSSL.ignoreSSL();//忽略证书   调用自 class IgnoreSSL{}
        URL url= new URL(urlString);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        //请求时注意添加cookie，不然访问网站失败，cookie可在该网页中使用开发者工具中查看
        con.setRequestProperty("Cookie",
                "_uab_collina=167798465194905226232757guidesStatus=off; highContrastMode=defaltMode; cursorStatus=off; RAIL_EXPIRATION=1678307982054; RAIL_DEVICEID=CvX8v3RGLbdXhgr80LEvvG0HZk4Jsj9dDNzbgHS6nm1Culzjwu_08lkb335MsxTP3qd-M4ppph-2J5hSg-6MOXGK2zIqeYGIL2moi8MZhHOwIPH8TYltu0_5-XvhaM-raMv2WKZx2y2jxyoTW79p5RNyFP_WjV7P; _jc_save_fromStation=%u5317%u4EAC%2CBJP; _jc_save_toStation=%u4E0A%u6D77%2CSHH; _jc_save_toDate=2023-03-05; _jc_save_wfdc_flag=dc; _jc_save_fromDate=2023-03-05; fo=u02fo7wzjoq6qevzMrbHSeTYCjE-SJBqHMpR7_GPPfybi6qP-Ii73h-KuavzX6u_O28HD_xCjtcC-ElHJq6LvgjbThnJVO8wsd3qSDWE6SJPbV7pQWzL792rnwiT4jzOXlEScHGotkdKZygW5okRAwcFqf7Q62UehHB35WsGKMJU4LQPrN-o30SMnzk");
        /*System.out.println("url:"+con.getURL());*/
        InputStreamReader input = new InputStreamReader(con.getInputStream(), "utf-8");
        BufferedReader bufferedReader = new BufferedReader(input);
        StringBuffer stringBuffer = new StringBuffer();
        String tempStr = "";
        while ((tempStr = bufferedReader.readLine()) != null) {
            stringBuffer.append(tempStr);
        }
        return stringBuffer.toString();
    }

    public static String returnJsonNoSetCookie(String urlString) throws Exception {
        IgnoreSSL.ignoreSSL();//忽略证书   调用自 class IgnoreSSL{}
        URL url= new URL(urlString);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        con.setRequestProperty("User-Agent", "Mozilla/4.76");
        InputStreamReader input = new InputStreamReader(con.getInputStream(), "utf-8");
        BufferedReader bufferedReader = new BufferedReader(input);
        StringBuffer stringBuffer = new StringBuffer();
        String tempStr = "";
        while ((tempStr = bufferedReader.readLine()) != null) {
            stringBuffer.append(tempStr);
        }
        return stringBuffer.toString();
    }
}

