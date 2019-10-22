package com.swagger;

import java.util.ArrayList;
import java.util.List;

import com.dto.MamaDTO;
import com.swagger.jbpm.http.TestCall;
import com.swagger.jbpm.http.ProcessImages;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;

import javax.inject.Inject;


/**
 * Created by simon on 30/09/19.
 */
@RestController
@Service
public class TestCallSwagger {
    @Inject
    private TestCall kieServerContainer;



    @ApiOperation(value = "Process mama" )
    @RequestMapping(method = RequestMethod.GET, value="/mama/{containerId}/{idDefinitionJbpm}/{userTask}/{nameMama}")
    public MamaDTO NpsSurveyJBPM(@ApiParam(value = "Nombre del contenedor", required=true, defaultValue="com.myspace:test:1.0.0-SNAPSHOT") @PathVariable("containerId") String containerId,
                                       @ApiParam(value = "Id de la definicion del proceso", required=true, defaultValue="com.testbpmn") @PathVariable("idDefinitionJbpm") String idDefinitionJbpm,
                                       @ApiParam(value = "Usuario que recibira la tarea", required=true, defaultValue="simon") @PathVariable("userTask") String userTask ,
                                       @ApiParam(value = "Puntaje de la encuesta", required=true, defaultValue="Teresa") @PathVariable("nameMama") String nameMama ) {
        MamaDTO npsRresultDTO = null;
        try {
            npsRresultDTO =  kieServerContainer.Mama(containerId, idDefinitionJbpm,  userTask,  nameMama );
        } catch (Exception e) {
            e.printStackTrace();
        }


        return npsRresultDTO;
    }



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