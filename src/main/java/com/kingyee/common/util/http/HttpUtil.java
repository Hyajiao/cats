package com.kingyee.common.util.http;

import me.chanjar.weixin.common.util.http.apache.DefaultApacheHttpClientBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

/**
 * HttpUtil工具类
 * @author ph
 * @date 2018-02-01
 */
public class HttpUtil {

    private static String[] SPIDERS = { "Googlebot", "msnbot", "Baiduspider", "bingbot", "Sogou web spider",
            "Sogou inst spider", "Sogou Pic Spider", "JikeSpider", "Sosospider", "Slurp", "360Spider", "YodaoBot",
            "OutfoxBot", "fast-webcrawler", "lycos_spider", "scooter", "ia_archiver", "MJ12bot", "AhrefsBot"};

    /**
     * 判断是否是爬虫的访问请求
     *
     * @param request
     * @return
     */
    public static boolean isRequestFromSpider(HttpServletRequest request) {
        boolean isSpider = false;
        String userAgent = request.getHeader("User-Agent");
        if (userAgent != null && userAgent.trim().length() > 0) {
            userAgent = userAgent.trim().toLowerCase();
            for (String spider : SPIDERS) {
                if (userAgent.indexOf(spider.toLowerCase()) >= 0) {
                    isSpider = true;
                    break;
                }
            }
        }
        return isSpider;
    }

    /**
     * 取得请求的IP地址
     * @param request
     * @return
     */
    public static String getRemoteIpAddr(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if(isValidIpAddr(ip)){
            return ip.split(",")[0];
        }

        ip = request.getHeader("Proxy-Client-IP");
        if(isValidIpAddr(ip)){
            return ip;
        }

        ip = request.getHeader("WL-Proxy-Client-IP");
        if(isValidIpAddr(ip)){
            return ip;
        }

        ip = request.getHeader("HTTP_CLIENT_IP");
        if(isValidIpAddr(ip)){
            return ip;
        }

        return request.getRemoteAddr();
    }

    /**
     * 判断IP地址是否有效
     * @param ip
     * @return
     */
    private static boolean isValidIpAddr(String ip){
        return ip != null && !ip.isEmpty() && !ip.equalsIgnoreCase("unknown");
    }

    /**
     * 判断请求是否是Ajax
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        if (header != null && "XMLHttpRequest".equals(header)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 取得浏览的base路径
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getBasePath(HttpServletRequest request) throws UnsupportedEncodingException{
        String path = request.getContextPath();
        int port = request.getServerPort();
        String basePath = null;
        if(port == 80){
            basePath = request.getScheme() + "://"
                    + request.getServerName() + path + "/";
        }else{
            basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
        }

        return basePath;
    }

    /**
     * 取得当前请求的完全URL
     * @param request
     * @param encode 是做urlencode
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getFullUrl(HttpServletRequest request, boolean encode) throws UnsupportedEncodingException{
        String orginUrl = request.getRequestURL().toString();
        if(null != request.getQueryString()){
            orginUrl += "?" + request.getQueryString();
        }else{
            Map<String, String[]> parpMap = request.getParameterMap();
            Set<String> keys = parpMap.keySet();
            String value = null;
            StringBuffer querys = new StringBuffer();
            for(String key : keys){
                value = parpMap.get(key)[0];
                if(null != value && !value.isEmpty()){
                    querys.append(key);
                    querys.append("=");
                    querys.append(value);
                    querys.append("&");
                }
            }
            if(querys.length() != 0){
                orginUrl += "?" + querys.substring(0, querys.length()-1).toString();
            }
        }
        try{
            if(encode){
                orginUrl = URLEncoder.encode(orginUrl,"UTF-8");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return orginUrl;
    }


    /**
     * 从网络上下载图片
     * @param url 下载url
     * @param dirPath 文件保存路径
     * @param fileName 要保存的文件名
     */
    public static void downloadPicture(String url, String dirPath, String fileName) throws IOException {
        if (!dirPath.endsWith("/")) {
            dirPath = dirPath + "/" ;
        }
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build();
        httpget.setConfig(requestConfig);

        httpget.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.79 Safari/537.1");
        httpget.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        CloseableHttpResponse resp = null;
        try {
            resp = httpclient.execute(httpget);
            if (HttpStatus.SC_OK == resp.getStatusLine().getStatusCode()) {
                HttpEntity entity = resp.getEntity();
                InputStream in = entity.getContent();
                savePicToDisk(in, dirPath, fileName);
            }
        } finally {
            resp.close();
            httpclient.close();
        }
    }

    /**
     * 将图片写到 硬盘指定目录下
     *
     * @param in 文件输入流
     * @param dirPath 文件保存路径
     * @param fileName 要保存的文件名
     */
    private static void savePicToDisk(InputStream in, String dirPath, String fileName) throws IOException {
        try {
            File dir = new File(dirPath);
            if (dir == null || !dir.exists()) {
                dir.mkdirs();
            }

            //文件真实路径
            String realPath = dirPath.concat(fileName);
            File file = new File(realPath);
            if (file == null || !file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = in.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            fos.close();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static HttpClient getHttpClient() throws Exception {
        DefaultApacheHttpClientBuilder clientBuilder = DefaultApacheHttpClientBuilder.get();
        clientBuilder.setConnectionRequestTimeout(5000);//从连接池获取链接的超时时间(单位ms)
        clientBuilder.setConnectionTimeout(5000);//建立链接的超时时间(单位ms)
        clientBuilder.setSoTimeout(5000);//连接池socket超时时间(单位ms)
        return clientBuilder.build();
    }

    /**
     * 判断是否是微信浏览器的访问请求
     *
     * @param request
     * @return
     */
    public static boolean isWechat(HttpServletRequest request) {
        boolean isSpider = false;
        String userAgent = request.getHeader("User-Agent");
        if (userAgent != null && userAgent.trim().length() > 0) {
            userAgent = userAgent.trim().toLowerCase();
            if (userAgent.indexOf("micromessenger") >= 0) {
                isSpider = true;
            }
        }
        return isSpider;
    }

}
