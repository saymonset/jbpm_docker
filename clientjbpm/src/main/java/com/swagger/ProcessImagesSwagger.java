package com.swagger;

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
public class ProcessImagesSwagger {
    @Inject
    private ProcessImages processImages;


    @ApiOperation(value = "Process image: Obtenemos la imagen del bpmn actual del proceso" )
    @RequestMapping(method = RequestMethod.GET, value="/image/{containerId}/{processInstanceId}")
    public Document ImageContainerProcessInstance( @ApiParam(value = "Nombre del contenedor", required=true, defaultValue="clientehotel_1.0.0-SNAPSHOT") @PathVariable("containerId") String containerId,
                                                 @ApiParam(value = "Id de la instancia del proceso", required=true, defaultValue="5") @PathVariable("processInstanceId") int processInstanceId ) {
        Document document = null;
        try {
            document = processImages.ImageContainerProcessInstance ( containerId,  processInstanceId);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return document;
    }
}