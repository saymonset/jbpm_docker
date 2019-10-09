package com.rest;

import com.ecological.NpsRresultDTO;
import com.ecological.Solution2DTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kie.server.api.model.instance.ProcessInstance;
import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.QueryServicesClient;
import org.kie.server.client.UserTaskServicesClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by simon on 08/10/19.
 */
/*@RestController
@RequestMapping("/npsurvey")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.GET})*/
public class npssurveybpmn {
    /*@Inject
    private KieServicesClient kieServicesClient;
    @Inject
    private ProcessServicesClient processServicesClient;
    @Inject
    private UserTaskServicesClient userTaskServicesClient;
    @Inject
    private QueryServicesClient queryServicesClient;
    private ProcessInstance processInstance = null;
    private  Long processInstanceId = null;
    private com.Person person = null;
    @Inject
    private ObjectMapper mapper;

    *//**Instancia la primera task of example evaluation*//*
    @RequestMapping(value = "/one",method = RequestMethod.GET)
    public ResponseEntity<?> npssurveybpmnxxx() {

        Map<String, Object> variables = new HashMap<>();
        variables.put("user","wbadmin");
        NpsRresultDTO npsRresultDTO = new NpsRresultDTO();
        npsRresultDTO.setPoint(1);
        variables.put("npsresdto",npsRresultDTO);
        processInstanceId = 5l;
        processInstanceId = processServicesClient.startProcess("npsurvey_1.0.0-SNAPSHOT",
                "com.ecological.npssurveybpmn", variables);
        processInstance = queryServicesClient.findProcessInstanceById(processInstanceId, true);
        *//***Disparamos el catch evento la instancia*//*









        System.out.println("processInstanceId = " + processInstanceId + ", status = " + processInstance.getState());
        return new ResponseEntity<>("Hi ", HttpStatus.CREATED);
    }



    *//**Instancia la primera task of example evaluation*//*
    @RequestMapping(value = "/two",method = RequestMethod.GET)
    public ResponseEntity<?> accioncorrectivanpsbpmn() {

        Map<String, Object> variables = new HashMap<>();
        variables.put("user","wbadmin");
        Solution2DTO solution2DTO = new Solution2DTO();
        solution2DTO.setOk(false);
        variables.put("solution",solution2DTO);
        processInstanceId = 5l;
        processInstanceId = processServicesClient.startProcess("npsurvey_1.0.0-SNAPSHOT",
                "com.ecological.accioncorrectivanpsbpmn", variables);
        processInstance = queryServicesClient.findProcessInstanceById(processInstanceId, true);
        *//***Disparamos el catch evento la instancia*//*

        System.out.println("processInstanceId = " + processInstanceId + ", status = " + processInstance.getState());
        return new ResponseEntity<>("Hi ", HttpStatus.CREATED);
    }*/
}
