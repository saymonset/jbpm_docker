package com.rest;

import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.KieContainerResourceList;
import org.kie.server.api.model.KieServerInfo;
import org.kie.server.api.model.ReleaseId;
import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.ProcessServicesClient;
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
    public void newInstancia(){
        Map<String, Object> variables = new HashMap<>();
        Long processInstanceId = null;

        //Name of container first : saymon_1.0.0-SNAPSHOT
        //Id of jbpm : saymon.sencillo
        processInstanceId = processServicesClient.startProcess("saymon_1.0.0-SNAPSHOT",
                "saymon.sencillo", variables);

        System.out.println("processInstanceId = " + processInstanceId);
        listCapabilities();
        listContainers();


        //import org.kie.server.api.model.instance.TaskSummary;
        List<TaskSummary> t = userTaskServicesClient.findTasksAssignedAsBusinessAdministrator("", 0,0);

        t.stream().forEach((p)->{
            System.out.println(p.getId() + " = " + p.getName() + "," + p.getCreatedBy() + "");
            //String containerId, Long taskId, String userId
            userTaskServicesClient.claimTask("saymon_1.0.0-SNAPSHOT",p.getId(),"simon");
            System.out.println("Claim task");
            userTaskServicesClient.startTask("saymon_1.0.0-SNAPSHOT",p.getId(),"simon");
            System.out.println("Start task");
            Map<String, Object> params = new HashMap<>();
            userTaskServicesClient.completeTask("saymon_1.0.0-SNAPSHOT",p.getId(),"simon", params);
            System.out.println("Finish task");
            //claimTask(String containerId, Long taskId, String userId);
            // void startTask(String containerId, Long taskId, String userId);
        });
/*
        Long taskIdCrearReq = userTaskClient.get

        // Tomo la tarea
        taskService.claim(taskIdCrearReq, "fluxit4");

        // Comienzo la tarea
        taskService.start(taskIdCrearReq, "fluxit4");

        // Completo la tarea
        taskService.complete(taskIdCrearReq, "fluxit4", params);*/
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
    @RequestMapping(value = "/instance",method = RequestMethod.GET)
    public ResponseEntity<?> newInstanciaMethod() {
        newInstancia();
        return new ResponseEntity<>("new instance.. ", HttpStatus.CREATED);
    }

/*    public  void newDeploy() {
        *//**
         *

         <groupId>com.myspace</groupId>
         <artifactId>saymon</artifactId>
         <version>1.0.0-SNAPSHOT</version>
         * *//*
        String containerId = "saymon_1.0.0-SNAPSHOT";
        System.out.println("\t######### Deploying container " + containerId);
        KieContainerResource resource = new KieContainerResource(containerId, new ReleaseId("com.myspace", "saymon", "1.0.0-SNAPSHOT"));
        kieServicesClient.createContainer(containerId, resource);
    }

    @RequestMapping(value = "/newDeploy",method = RequestMethod.GET)
    public ResponseEntity<?> deploy() {
        newDeploy();
        return new ResponseEntity<>("new deploy.. ", HttpStatus.CREATED);
    }*/
}
