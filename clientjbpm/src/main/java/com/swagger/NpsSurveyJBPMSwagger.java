package com.swagger;

import com.ecological.NpsRresultDTO;
import com.swagger.jbpm.http.NpsSurveyBPMN;
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
 * Created by simon on 09/10/19.
 */
@RestController
@Service
public class NpsSurveyJBPMSwagger {
    @Inject
    private NpsSurveyBPMN npssurveybpmn;


    @ApiOperation(value = "Instanciamos el jbpm NPS" )
    @RequestMapping(method = RequestMethod.GET, value="/npsurvey/{containerId}/{idDefinitionJbpm}/{userTask}/{point}")
    public NpsRresultDTO NpsSurveyJBPM(@ApiParam(value = "Nombre del contenedor", required=true, defaultValue="npsurvey_1.0.0-SNAPSHOT") @PathVariable("containerId") String containerId,
                                                  @ApiParam(value = "Id de la definicion del proceso", required=true, defaultValue="com.ecological.npssurveybpmn") @PathVariable("idDefinitionJbpm") String idDefinitionJbpm,
                                  @ApiParam(value = "Usuario que recibira la tarea", required=true, defaultValue="wbadmin") @PathVariable("userTask") String userTask ,
                                  @ApiParam(value = "Puntaje de la encuesta", required=true, defaultValue="1") @PathVariable("point") int point ) {
        NpsRresultDTO npsRresultDTO = null;
        try {
            npsRresultDTO =  npssurveybpmn. NpsSurveyJBPM (containerId, idDefinitionJbpm,  userTask,  point );
        } catch (Exception e) {
            e.printStackTrace();
        }


        return npsRresultDTO;
    }
}