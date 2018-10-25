package com.example.novak.myexampleapp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

public class HttpsHelper {
    private String host;

    public HttpsHelper(String host) {
        this.host = host;
    }
    public String postFormSend(String link, Map<String, String> parameters) {
        HttpsURLConnection conn=null;
        try {
            URL url = new URL(host+link);
            conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "java-client");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setHostnameVerifier(new HostnameVerifier() {

                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            // SSL setting
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new TrustManager[]{
                    new javax.net.ssl.X509TrustManager() {

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType)
                                throws CertificateException {
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType)
                                throws CertificateException {
                        }
                    }
            }, null);
            conn.setSSLSocketFactory(context.getSocketFactory());
            String param = "username=ww@ww.ww&password=Qwerty1-&grant_type=password";

            //String param = processRequestParameters(parameters);

            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(param);
            wr.flush();
            wr.close();
            // Print response from host
            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = reader.readLine();
//            while ((line = reader.readLine()) != null) {
//                //System.out.printf("%s\n", line);
//            }
            reader.close();
            return line;

        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            if(conn!=null) {
                conn.disconnect();
            }
        }
        return null;
    }

    private String processRequestParameters(Map<String, String> parameters) {
        StringBuilder sb = new StringBuilder();
        for (String parameterName : parameters.keySet()) {
            sb.append(parameterName).append('=').append(urlEncode(parameters.get(parameterName))).append('&');
        }
        return sb.substring(0, sb.length() - 1);
    }
    private String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // This is impossible, UTF-8 is always supported according to the java standard
            throw new RuntimeException(e);
        }
    }
}
