package com.qa.Test;

import com.qa.Client.RestClient;
import com.qa.base.TestBase;
import com.qa.util.TestUtil;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

public class GetAPITest  extends TestBase {
    TestBase testbase;
    String serviceurl;
    String apiurl;
    String url;
    RestClient restClient;
    CloseableHttpResponse closableResponse;

    @BeforeMethod
    public void setup() throws IOException {
        testbase = new TestBase();
        serviceurl = prop.getProperty("URL");
        apiurl = prop.getProperty("serviceURL");
        url = serviceurl + apiurl;
    }

    @Test(priority=1)
    public void getAPITestwithoutHeader() throws IOException {
        restClient = new RestClient();
        closableResponse = restClient.get(url);
        // a.status code
        int statuscode = closableResponse.getStatusLine().getStatusCode();
        System.out.println("Status code is:" + statuscode);
        Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "status code is not 200");
        //b.json string
        String responseString = EntityUtils.toString(closableResponse.getEntity(), "UTF-8");
        // single value assertion
        // per page
        JSONObject jsonobj = new JSONObject(responseString);
        System.out.println("Response JSON from is:" + jsonobj);
        String valueofperpage = TestUtil.getValueByJPath(jsonobj, "/per_page"); // u need to use  / to get single value
        System.out.println("per page value is:" + valueofperpage);
        Assert.assertTrue(true);
        //total:
        String totalValue = TestUtil.getValueByJPath(jsonobj, "/total");
        System.out.println("value of total is-->"+ totalValue);
        Assert.assertEquals(Integer.parseInt(totalValue), 12);

        //get the value from JSON ARRAY:
        String lastName = TestUtil.getValueByJPath(jsonobj, "/data[0]/last_name");
        String id = TestUtil.getValueByJPath(jsonobj, "/data[0]/id");
        String avatar = TestUtil.getValueByJPath(jsonobj, "/data[0]/avatar");
        String firstName = TestUtil.getValueByJPath(jsonobj, "/data[0]/first_name");

        System.out.println(lastName);
        System.out.println(id);
        System.out.println(avatar);
        System.out.println(firstName);
      // All Headers
        Header[] headerarray = closableResponse.getAllHeaders();
        HashMap<String, String> allHeaders = new HashMap<String, String>();
        for (Header header : headerarray) {
            allHeaders.put(header.getName(), header.getValue());
        }
        System.out.println("Headers array is : " + allHeaders);
    }

    @Test
    public void getAPITestwithHeader() throws IOException {
        restClient = new RestClient();
        HashMap<String,String> hashmap = new HashMap<String, String>();
        hashmap.put("Content-Type", "application/json"); // If your using xml need to write application/xml
//        hashmap.put("username", "test@amazon.com");
//        hashmap.put("password", "test213");
//        hashmap.put("Auth Token", "12345");
        closableResponse = restClient.get(url);
        // a.status code
        int statuscode = closableResponse.getStatusLine().getStatusCode();
        System.out.println("Status code is:" + statuscode);
        Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "status code is not 200");

        //b.json string
        String responseString = EntityUtils.toString(closableResponse.getEntity(), "UTF-8");
        JSONObject jsonobj = new JSONObject(responseString);
        System.out.println("Response JSON from is:" + jsonobj);
        //single value assertion:
        //per_page:
        String perPageValue = TestUtil.getValueByJPath(jsonobj, "/per_page");
        System.out.println("value of per page is-->"+ perPageValue);
        Assert.assertEquals(Integer.parseInt(perPageValue), 6);

        //total:
        String totalValue = TestUtil.getValueByJPath(jsonobj, "/total");
        System.out.println("value of total is-->"+ totalValue);
        Assert.assertEquals(Integer.parseInt(totalValue), 12);

        //get the value from JSON ARRAY:
        String lastName = TestUtil.getValueByJPath(jsonobj, "/data[0]/last_name");
        String id = TestUtil.getValueByJPath(jsonobj, "/data[0]/id");
        String avatar = TestUtil.getValueByJPath(jsonobj, "/data[0]/avatar");
        String firstName = TestUtil.getValueByJPath(jsonobj, "/data[0]/first_name");

        System.out.println(lastName);
        System.out.println(id);
        System.out.println(avatar);
        System.out.println(firstName);

        // c.All Headers
        Header[] headerarray = closableResponse.getAllHeaders();
        HashMap<String, String> allHeaders = new HashMap<String, String>();
        for (Header header : headerarray) {
            allHeaders.put(header.getName(), header.getValue());
        }
        System.out.println("Headers array is : " + allHeaders);
    }
}