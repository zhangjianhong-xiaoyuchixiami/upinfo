package org.qydata.tools.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * Created by jonhn on 2017/4/13.
 */
public class HttpClientUtil {

    /**
     * 发送get请求
     * @param url       链接地址
     * @param charset   字符编码，若为null则默认utf-8
     * @param params   请求参数
     * @return
     */
    public static String doGet(String url, Map<String, Object> params, String charset){
        String apiUrl = url;
        StringBuffer param = new StringBuffer();
        int i = 0;
        for (String key : params.keySet()) {
            if (i == 0)
                param.append("?");
            else
                param.append("&");
            param.append(key).append("=").append(params.get(key));
            i++;
        }
        apiUrl += param;
        if(null == charset){
            charset = "utf-8";
        }
        String result = null;
        int statusCode = 500;
        try {
            //DefaultHttpClient client = new DefaultHttpClient();
            //HttpClient httpClient = WebClientDevWrapper.wrapClient(client);
            HttpClient httpClient = new SSLClient();
            httpClient.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, false);
            HttpGet httpGet = new HttpGet(apiUrl);
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) ...");
            HttpResponse response = httpClient.execute(httpGet);
            statusCode = response.getStatusLine().getStatusCode();
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                    System.out.println("**************************************");
                    System.out.println("https请求状态码statusCode="+statusCode);
                    System.out.println("https请求结果result="+result);
                    System.out.println("**************************************");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String doPost(String baseUrl, String param) throws Exception {
        X509TrustManager trustManager = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        SSLContext sslcontext = SSLContext.getInstance("SSL");
        sslcontext.init(null, new TrustManager[] { trustManager }, null);
        SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
        sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        DefaultHttpClient httpclient = new DefaultHttpClient();
        httpclient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", sf, 443));
        HttpPost httpPost = new HttpPost(baseUrl);
        System.out.println("executing request " + httpPost.getURI());
        String result = "";
        httpPost.setHeader("Content-type", "application/json");

        StringEntity reqEntity;
        // 将请求参数封装成HttpEntity
        reqEntity = new StringEntity(param, Charset.forName("UTF-8").toString());
        httpPost.setEntity(reqEntity);
        BufferedHttpEntity bhe = new BufferedHttpEntity(reqEntity);
        httpPost.setEntity(bhe);
        HttpResponse response = httpclient.execute(httpPost);
        HttpEntity resEntity = response.getEntity();
        InputStreamReader reader = new InputStreamReader(resEntity.getContent());
        char[] buff = new char[1024];
        int length = 0;
        while ((length = reader.read(buff)) != -1) {
            result += new String(buff, 0, length);
        }
        httpclient.getConnectionManager().shutdown();
        System.out.println("--------------------------------------");
        System.out.println("Response Code = " + response.getStatusLine().getStatusCode());
        System.out.println("Response content: " + result);
        System.out.println("--------------------------------------");
        return result;
    }

    public static String httpclientGet(String url, Map<String, Object> params, String charset) throws IOException {

        String apiUrl = url;
        StringBuffer param = new StringBuffer();
        int i = 0;
        for (String key : params.keySet()) {
            if (i == 0)
                param.append("?");
            else
                param.append("&");
            param.append(key).append("=").append(params.get(key));
            i++;
        }
        apiUrl += param;
        if(null == charset){
            charset = "utf-8";
        }

        // 创建HttpClient对象
        DefaultHttpClient client = new DefaultHttpClient();


        // 创建GET请求（在构造器中传入URL字符串即可）
        HttpGet get = new HttpGet(apiUrl);

        // 调用HttpClient对象的execute方法获得响应
        HttpResponse response = client.execute(get);

        // 调用HttpResponse对象的getEntity方法得到响应实体
        HttpEntity httpEntity = response.getEntity();

        // 使用EntityUtils工具类得到响应的字符串表示
        String result = EntityUtils.toString(httpEntity,"utf-8");
        return result;
    }

}
