package com.swagger.jbpm.http;

import com.ecological.NpsRresultDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.Tools;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.kie.server.api.model.instance.ProcessInstance;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.QueryServicesClient;
import org.kie.server.client.UserTaskServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by simon on 09/10/19.
 */
@Component("npssurveybpmn")
public class NpsSurveyBPMN {
    private static final Logger Log = LoggerFactory.getLogger(NpsSurveyBPMN.class);
    @Inject
    Tools tools;

    @Value("${baseURI}")
    private String baseURI;
    @Value("${jbpm.user}")
    private String user;
    @Value("${jbpm.password}")
    private String password;

    @Inject
    private KieServicesClient kieServicesClient;
    @Inject
    private ProcessServicesClient processServicesClient;
    @Inject
    private UserTaskServicesClient userTaskServicesClient;
    @Inject
    private QueryServicesClient queryServicesClient;
    private ProcessInstance processInstance = null;
    private com.Person person = null;
    @Inject
    private ObjectMapper mapper;


    /**Instanciamos el jbpm NPS.. parametros  vienen del swagger*/
    public NpsRresultDTO NpsSurveyJBPM (String containerId, String idDefinitionJbpm, String userTask, int point )
            throws Exception {

        Map<String, Object> variables = new HashMap<>();
        variables.put("user",userTask);
        NpsRresultDTO npsRresultDTO = new NpsRresultDTO();
        npsRresultDTO.setPoint(point);
        variables.put("npsresdto",npsRresultDTO);
        long processInstanceId = 0l;
        processInstanceId = processServicesClient.startProcess(containerId,
                idDefinitionJbpm, variables);
        processInstance = queryServicesClient.findProcessInstanceById(processInstanceId, true);
        npsRresultDTO.setProcessInstance(processInstance);

        return npsRresultDTO;
    }






}
