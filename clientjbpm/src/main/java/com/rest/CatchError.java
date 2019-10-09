package com.rest;

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
 * Created by simon on 06/10/19.
 */
@RestController
@RequestMapping("/catch")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.GET})
public class CatchError {
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
        variables.put("flag",true);
        processInstanceId = 5l;
        processInstanceId = processServicesClient.startProcess("Test_1.0.0-SNAPSHOT",
                "src.main.resources.test", variables);
        processInstance = queryServicesClient.findProcessInstanceById(processInstanceId, true);
        /***Disparamos el catch evento la instancia*/

        processServicesClient.signalProcessInstance("Test_1.0.0-SNAPSHOT",processInstance.getId(),"errorsimon", variables);




        /**Obtenemos las tareas de la instancia**/
        TaskSummary task = processInstance.getActiveUserTasks().getItems().get(0);
        /**Imprimimos el status*/
        System.out.println(task.getStatus() + " = status, " + task.getId() + " = " + task.getName() + ",owner=" + task.getActualOwner() + "");

        /**Liberamos la tarea.. alcomienzo siempre va a tener la tarea ya claim.. la liberamos para volverla
         * a claim en el contexto*/
        userTaskServicesClient.releaseTask(task.getContainerId(), task.getId(), "wbadmin");
        /***Obtenemos la instancia  actualizada*/
        processInstance = queryServicesClient.findProcessInstanceById(processInstanceId);
        /**Obtenemos las tareas de la instancia actualizada**/
        task = processInstance.getActiveUserTasks().getItems().get(0);
        System.out.println(task.getStatus() + " = status, " + task.getId() + " = " + task.getName() + ",owner=" + task.getActualOwner() + "");

        /**Claim la tarea en el contexto*/
        userTaskServicesClient.claimTask(task.getContainerId(), task.getId(), "wbadmin");
        /***Obtenemos la instancia  actualizada*/
        processInstance = queryServicesClient.findProcessInstanceById(processInstanceId);
        /**Obtenemos las tareas de la instancia actualizada**/
        task = processInstance.getActiveUserTasks().getItems().get(0);
        System.out.println(task.getStatus() + " = status, " + task.getId() + " = " + task.getName() + ",owner=" + task.getActualOwner() + "");

        /**startTask la tarea en el contexto*/
        userTaskServicesClient.startTask(task.getContainerId(), task.getId(), "wbadmin");
        /***Obtenemos la instancia  actualizqada*/
        processInstance = queryServicesClient.findProcessInstanceById(processInstanceId);
        /**Obtenemos las tareas de la instancia actualizada**/
        task = processInstance.getActiveUserTasks().getItems().get(0);
        System.out.println(task.getStatus() + " = status, " + task.getId() + " = " + task.getName() + ",owner=" + task.getActualOwner() + "");


        /**completamos la tarea en el contexto*/
        userTaskServicesClient.completeTask(task.getContainerId(), task.getId(), "wbadmin",  variables);
        /***Obtenemos la instancia  actualizqada*/
        processInstance = queryServicesClient.findProcessInstanceById(processInstanceId);
       // if (processInstance.getState()){
        //
        //   task = processInstance.getActiveUserTasks().getItems().get(0);
 //       System.out.println(task.getStatus() + " = status, " + task.getId() + " = " + task.getName() + ",owner=" + task//.getActualOwner() + "");
        // }
        /**Obtenemos las tareas de la instancia actualizada**/







        System.out.println("processInstanceId = " + processInstanceId + ", status = " + processInstance.getState());
        return new ResponseEntity<>("Hi ", HttpStatus.CREATED);
    }
}
