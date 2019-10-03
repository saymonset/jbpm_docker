package com.rest;

/**
 * Created by simon on 03/10/19.
 */

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/upload")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.GET})
public class UploadMavenArtifact {
    private static final Logger LOG = LoggerFactory.getLogger(UploadMavenArtifact.class);


    @RequestMapping(value = "/jar",method = RequestMethod.GET)
    public ResponseEntity<?> newInstanciaMethod() {
        //Maven coordinates
        String groupId = "org.mastertheboss.kieserver";
        String artifactId = "hello-kie-server";
        String version = "1.0";


        //File to upload
        File file = new File("/tmp/"+artifactId+"-"+version+".jar");


        //Server properties
        String protocol = "http";
        String hostname = "localhost";
        Integer port = 8445;
        String username = "wbadmin";
        String password = "wbadmin";


        //Create the HttpEntity (body of our POST)
        FileBody fileBody = new FileBody(file);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addPart("upfile", fileBody);
        HttpEntity entity = builder.build();


        //Calculate the endpoint from the maven coordinates
        String resource = "/business-central/maven2/" + groupId.replace('.', '/') + "/" + artifactId +"/" + version + "/" + artifactId + "-" + version + ".jar";

        LOG.info("POST " + hostname + ":" + port + resource);

        //Set up HttpClient to use Basic pre-emptive authentication with the provided credentials
        HttpHost target = new HttpHost(hostname, port, protocol);
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(target.getHostName(), target.getPort()),
                new UsernamePasswordCredentials(username,password));
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider).build();
        HttpPost httpPost = new HttpPost(resource);
        httpPost.setEntity(entity);
        AuthCache authCache = new BasicAuthCache();
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(target, basicAuth);
        HttpClientContext localContext = HttpClientContext.create();
        localContext.setAuthCache(authCache);

        try {
            //Perform the HTTP POST
            CloseableHttpResponse response = httpclient.execute(target, httpPost, localContext);
            LOG.info(response.toString());
            //Now check your artifact repository!
        } catch (ClientProtocolException e) {
            LOG.error("Protocol Error", e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            LOG.error("IOException while getting response", e);
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>("new instance.. ", HttpStatus.CREATED);
    }
    public static void main(String[] args) {


    }
}
