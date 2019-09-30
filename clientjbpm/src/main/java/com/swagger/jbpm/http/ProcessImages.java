package com.swagger.jbpm.http;

import com.util.Tools;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.w3c.dom.Document;

import javax.inject.Inject;

/**
 * Created by simon on 30/09/19.
 */
@Component("processImages")
public class ProcessImages {
    private static final Logger Log = LoggerFactory.getLogger(ProcessImages.class);
    @Inject
    Tools tools;

    @Value("${baseURI}")
    private String baseURI;
    @Value("${jbpm.user}")
    private String user;
    @Value("${jbpm.password}")
    private String password;

    /**Obtenemos el jbpm imagen del proceso actual*/
    public Document ImageContainerProcessInstance (String containerId, int processInstanceId)
            throws Exception {
        Document out = null;
        StringBuilder str = new StringBuilder(baseURI + "/server/containers/");
        str.append(containerId).append("/images/processes/instances/").append(processInstanceId);
        str.append("?svgCompletedColor=%23C0C0C0&svgCompletedBorderColor=%23030303&svgActiveBorderColor=%23FF0000");
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet get = new HttpGet(str.toString());
            String authData = user + ":" + password;
            String encoded = new sun.misc.BASE64Encoder().encode(authData.getBytes());
            get.setHeader("Content-Type", "application/json");
            get.setHeader("Authorization", "Basic " + encoded);
            get.setHeader("ACCEPT", "application/svg+xml");
            HttpResponse cgResponse = client.execute(get);
            out = tools.getDomElement(EntityUtils.toString(cgResponse.getEntity()));
        } catch (Exception e) {
            throw new Exception("Error consuming service.", e);
        }
        return out;
    }






}
