package com.rest;
import java.net.MalformedURLException;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientRequestFactory;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.kie.remote.client.jaxb.ClientJaxbSerializationProvider;
import org.kie.services.client.serialization.JaxbSerializationProvider;
import org.kie.services.client.serialization.jaxb.impl.deploy.JaxbDeploymentJobResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by simon on 03/10/19.
 */

@RestController
@RequestMapping("/bpms")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.GET})
public class BPMSRestClient {


    private static final String APP_URL = "http://localhost:8445/business-central";
    private static final String USER = "wbadmin";
    private static final String PASSWORD = "wbadmin";
    private static final  String groupId = "org.mastertheboss.kieserver";
    private static final String artifactId = "hello-kie-server";
    private static final String version = "1.0";
    private static final String DEPLOYMENT_ID = groupId + ":"+artifactId + ":" + version;
    private static final String DEPLOY_COMMAND = "/rest/deployment/" + DEPLOYMENT_ID + "/deploy";


    /**
     * 	private static final String APP_URL = "http://localhost:8080/business-central";
     private static final String USER = "bpmsAdmin";
     private static final String PASSWORD = "password";
     private static final String DEPLOYMENT_ID = "com.test:mavenupload:1.0";
     private static final String DEPLOY_COMMAND = "/rest/deployment/" + DEPLOYMENT_ID + "/deploy";
     * */


    @RequestMapping(value = "/jar",method = RequestMethod.GET)
    public ResponseEntity<?> newInstanciaMethod() {
// Test API: [POST] /deployment/{deploymentId}/deploy
        doDeployCall();
        return new ResponseEntity<>("new instance.. ", HttpStatus.CREATED);
    }


    /*
     * API: [POST] /deployment/ {deploymentId} /deploy
     * https://access.redhat.com/documentation/en-US/Red_Hat_JBoss_BPM_Suite/6.1/html/Development_Guide/chap-Remote_API.html#sect-Deployment_REST_API
     */
    private static void doDeployCall() {

        try {
            ClientRequest clientRequest = getClientRequestFactory().createRequest(APP_URL + DEPLOY_COMMAND);
            clientRequest.accept("application/xml");

            // From doc: https://access.redhat.com/documentation/en-US/Red_Hat_JBoss_BPM_Suite/6.1/html/Development_Guide/chap-Remote_API.html#sect-Deployment_REST_API
            // Deploys the deployment unit which is referenced by the deploymentid
            // and returns a JaxbDeploymentJobResult instance with the status of the request [POST]
            ClientResponse<JaxbDeploymentJobResult> responseObj = (ClientResponse<JaxbDeploymentJobResult>) checkDeployResponse(clientRequest.post());

            // Deserialize to get the returned JaxbDeploymentJobResult object
            JaxbSerializationProvider jaxbProvider = ClientJaxbSerializationProvider.newInstance();
            jaxbProvider.addJaxbClasses(JaxbDeploymentJobResult.class);
            JaxbDeploymentJobResult deploymentResult = (JaxbDeploymentJobResult) jaxbProvider.deserialize(responseObj.getEntity(String.class));

            System.out.println("Deployment Result:"+deploymentResult.getExplanation());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ClientRequestFactory getClientRequestFactory() throws MalformedURLException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        httpClient.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM),new UsernamePasswordCredentials(USER, PASSWORD));
        ClientExecutor clientExecutor = new ApacheHttpClient4Executor(httpClient);
        return new ClientRequestFactory(clientExecutor,ResteasyProviderFactory.getInstance());
    }

    public static ClientResponse<?> checkDeployResponse(ClientResponse<?> responseObj) throws Exception {
        responseObj.resetStream();
        int status = responseObj.getStatus();

        // http status 200 OK / 202 Accepted
        if (status != 200 &&  status != 202) {
            throw new Exception("Response with exception:\n" + responseObj.getEntity(String.class));
        }
        return responseObj;
    }
}
