package com.swagger.jbpm.http;

import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.annotation.Resource;

/**
 * Created by simon on 30/09/19.
 */
@Resource
public class TestCall {
    private static final Logger logger = LoggerFactory.getLogger(TestCall.class);

    public static void getTaskSummaryList() throws Exception {
        String status = "Reserved";
        String actor = "krisv";

        String addr = "http://localhost:8080/jbpm-console/rest/task/query?status=" + status + "&potentialOwner=" + actor;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet get = new HttpGet(addr);

            String authData = "wbadmin" + ":" + "wbadmin";
            String encoded = new sun.misc.BASE64Encoder().encode(authData.getBytes());
            get.setHeader("Content-Type", "application/json");
            get.setHeader("Authorization", "Basic " + encoded);
            get.setHeader("ACCEPT", "application/xml");

            HttpResponse cgResponse = client.execute(get);
            String content = EntityUtils.toString(cgResponse.getEntity());
            System.out.println(content);
        } catch (Exception e) {
            throw new Exception("Error consuming service.", e);
        }
    }


    public String server ()
         throws Exception {
        String content =null;
                String addr = "http://localhost:8180/kie-server/services/rest/server";
            try {
                HttpClient client = HttpClientBuilder.create().build();
                HttpGet get = new HttpGet(addr);

                String authData = "kieserver" + ":" + "kieserver1!";
                String encoded = new sun.misc.BASE64Encoder().encode(authData.getBytes());
                get.setHeader("Content-Type", "application/json");
                get.setHeader("Authorization", "Basic " + encoded);
                get.setHeader("ACCEPT", "application/json");

                HttpResponse cgResponse = client.execute(get);
                 content = EntityUtils.toString(cgResponse.getEntity());
                System.out.println(content);
            } catch (Exception e) {
                throw new Exception("Error consuming service.", e);
            }
            return content;
    }

}
