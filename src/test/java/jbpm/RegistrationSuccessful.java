package jbpm;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.logging.Logger;

/**
 * Created by simon on 2/15/2019.
 */
public class RegistrationSuccessful {
Logger log = Logger.getLogger(this.getClass().getName());

    @Test
    public void RegistrationSuccessful()
    {
        RestAssured.baseURI ="http://localhost:8080/processdef/";
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName("john");
        authScheme.setPassword("john1");
        RestAssured.authentication = authScheme;
        RequestSpecification request = RestAssured.given();
        Response response = request.get("");

        int statusCode = response.getStatusCode();
        log.info(statusCode  +"");
        Assert.assertEquals(statusCode, 200);

    }

    @Test
    public void RegistrationSuccessfulPost()
    {
        RestAssured.baseURI ="http://localhost:8080/processdef/";
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName("john");
        authScheme.setPassword("john1");
        RestAssured.authentication = authScheme;
        RequestSpecification request = RestAssured.given();
        JSONObject requestParams = new JSONObject();
        requestParams.put("FirstName", "Virender"); // Cast
        requestParams.put("LastName", "Singh");
        requestParams.put("UserName", "sdimpleuser2dd2011");
        requestParams.put("Password", "password1");

        requestParams.put("Email",  "sample2ee26d9@gmail.com");
        //request.body(requestParams.toJSONString());
        //Response response = request.post("/register");
        Response response = request.post("");

        int statusCode = response.getStatusCode();
        log.info(statusCode  + " = saymon");
        Assert.assertEquals(statusCode, 200);

    }
}
