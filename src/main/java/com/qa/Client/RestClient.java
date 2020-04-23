package com.qa.Client;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RestClient {
    //1.Get Method withoout Headers
    public CloseableHttpResponse get(String url) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url); //http get request
        httpclient.execute(httpget);
        CloseableHttpResponse closableResponse = httpclient.execute(httpget); // hit get url
        return closableResponse;

    }
    // Get method with Headers ,default content type will be their  doent matter u passing or not
   // If not use this method.
    public CloseableHttpResponse get(String url, HashMap<String, String> hashMap) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url); //http get request
        for(Map.Entry<String,String> entry: hashMap.entrySet()) {
            httpget.addHeader(entry.getKey(), entry.getValue());
        }
        CloseableHttpResponse closableResponse = httpclient.execute(httpget); // hit get url
        return closableResponse;
    }
}