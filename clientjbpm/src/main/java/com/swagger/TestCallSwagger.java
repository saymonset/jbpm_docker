package com.swagger;

import java.util.ArrayList;
import java.util.List;

import com.swagger.jbpm.http.TestCall;
import com.swagger.jbpm.http.ProcessImages;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by simon on 30/09/19.
 */
@RestController
public class TestCallSwagger {

    private TestCall kieServerContainer = new TestCall();
    private ProcessImages processImages;


    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<String> getProducts() {
        List<String> productsList = new ArrayList<>();
        productsList.add("Honey");
        productsList.add("Almond");
        return productsList;
    }


    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public String createProduct() {
        return "Product is saved successfully";
    }


    @ApiOperation(value = "Optiene La ficha del producto a partir del id.")
    @RequestMapping(method = RequestMethod.GET, value="/server/{id}")
    public String infotest( @ApiParam(value = "Un numero cualquiera saymon", required=true, defaultValue="12") @PathVariable("id") int id) {
        int id0 = id;
        String content = "";
        try {
            content = kieServerContainer.server();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }
}