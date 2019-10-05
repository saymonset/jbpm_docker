package com.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.kie.server.api.model.instance.ProcessInstance;
import org.kie.server.api.model.instance.TaskInstance;
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
import java.util.List;
import java.util.Map;

/**
 * Created by simon on 04/10/19.
 */
@RestController
@RequestMapping("/sign")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.GET})
public class SignalRef {
    @Inject
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

    /**Instancia la primera task of example evaluation*/
    @RequestMapping(value = "/one",method = RequestMethod.GET)
    public ResponseEntity<?> onecall() {

        Map<String, Object> variables = new HashMap<>();
        variables.put("user","wbadmin");

        processInstanceId = 5l;
     /*   processInstanceId = processServicesClient.startProcess("ofeclipse_1.0.0-SNAPSHOT",
                "com.mybpmn", variables);
*/
        /***Obtenemos la instancia*/
        processInstance = queryServicesClient.findProcessInstanceById(processInstanceId, true);
        processServicesClient.signalProcessInstance("ofeclipse_1.0.0-SNAPSHOT",processInstance.getId(),"reactsign", variables);
     /**   processServicesClient.signalProcessInstance(JbpmConstants.CONTAINER_ID.getValue(), processInstanceId,
                JbpmConstants.ASSIGN_PROJECT_TO_BUDGET_SIGNAL.getValue(), params);*/
        Map<String,Object> vars = processInstance.getVariables();

        vars.forEach((k,v)->{
            System.out.println("k = " + k + ", v = " + v);



        });





        System.out.println("processInstanceId = " + processInstanceId + ", status = " + processInstance.getState());
        return new ResponseEntity<>("Hi ", HttpStatus.CREATED);
    }
}
