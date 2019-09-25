package com.rest;

import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.KieContainerResourceList;
import org.kie.server.api.model.KieServerInfo;
import org.kie.server.api.model.ReleaseId;
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
import java.util.List;
import java.util.Map;

/**
 *
 * Para refrescar el jbpmn ultimo... debes desplegarlo en buiness-central deploy
 * Created by simon on 08/09/19.
 */
@RestController
@RequestMapping("/task")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.GET})
public class TaskExecute {
    @Inject
    private KieServicesClient kieServicesClient;
    @Inject
    private ProcessServicesClient processServicesClient;
    @Inject
    private UserTaskServicesClient userTaskServicesClient;
    @Inject
    private QueryServicesClient queryServicesClient;


/**Instancia la primera task of example evaluation*/
    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public ResponseEntity<?> query() {
        Map<String, Object> variables = new HashMap<>();

        variables.put("employee","wbadmin");
        variables.put("reason","TODO UN EXITO SAYMOSN!!");
        variables.put("performance","110");
        variables.put("initiator","wbadmin");
        Long processInstanceId = null;


        /**Mapeamos el containr 'evaluation_1.0.0-SNAPSHOT' y el id del jbpm 'evaluation' para obtener el id del
         * proceso instance*/
        processInstanceId = processServicesClient.startProcess("evaluation_1.0.0-SNAPSHOT",
                "evaluation", variables);

        /***Obtenemos la instancia*/
        ProcessInstance processInstance = queryServicesClient.findProcessInstanceById(processInstanceId);
        System.out.println("processInstanceId = " + processInstanceId + ", status = " + processInstance.getState());
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
        /**Obtenemos las tareas de la instancia actualizada**/
        task = processInstance.getActiveUserTasks().getItems().get(0);
        System.out.println(task.getStatus() + " = status, " + task.getId() + " = " + task.getName() + ",owner=" + task.getActualOwner() + "");


        processInstance.getActiveUserTasks().getItems().stream().forEach((taskjob)-> {
            System.out.println(taskjob.getStatus() + " = status, " + taskjob.getId() + " = " + taskjob.getName() + "," + taskjob.getCreatedBy() + "");
        });


        return new ResponseEntity<>("Hi ", HttpStatus.CREATED);
    }



    //Server Capabilities
    public void listCapabilities() {

        KieServerInfo serverInfo = kieServicesClient.getServerInfo().getResult();
        System.out.print("Server capabilities:");

        for (String capability : serverInfo.getCapabilities()) {
            System.out.print(" " + capability);
        }

        System.out.println();
    }

    // Listing Containers
    public void listContainers() {
        KieContainerResourceList containersList = kieServicesClient.listContainers().getResult();
        List<KieContainerResource> kieContainers = containersList.getContainers();
        System.out.println("Available containers: ");
        for (KieContainerResource container : kieContainers) {
            System.out.println("\t" + container.getContainerId() + " (" + container.getReleaseId() + ")");
        }
    }

}
