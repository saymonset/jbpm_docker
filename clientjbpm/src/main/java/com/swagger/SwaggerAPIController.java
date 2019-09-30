package com.swagger;

import java.util.ArrayList;
import java.util.List;

import com.swagger.jbpm.KieServerContainer;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;


/**
 * Created by simon on 30/09/19.
 */
@RestController
public class SwaggerAPIController {

    private KieServerContainer kieServerContainer = new KieServerContainer();
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<String> getProducts() {
        List<String> productsList = new ArrayList<>();
        productsList.add("Honey");
        productsList.add("Almond");
        return productsList;
    }

    @RequestMapping(value = "/server", method = RequestMethod.GET)
    public String server() {
        String content = "";
        try {
            content = kieServerContainer.server();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public String createProduct() {
        return "Product is saved successfully";
    }


    @ApiOperation(value = "Optiene La ficha del producto a partir del id.")
    @RequestMapping(method = RequestMethod.GET, value="/sheet/{id}")
    public String getProductSheetResponse( @ApiParam(value = "Un numero cualquiera saymon", required=true, defaultValue="12") @PathVariable("id") int id) {
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