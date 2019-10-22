package com.rest;

import org.kie.internal.task.api.UserInfo;
import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.api.model.instance.ProcessInstance;
import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.ProcessServicesClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.kie.server.api.model.ReleaseId;
/**
 * Created by simon on 16/08/19.
 *
 * Example http://localhost:8445/q/all
 */
@RestController
@RequestMapping("/q")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.GET})
public class Test {
    @Inject
    private KieServicesClient kieServicesClient;
    @Inject
    private ProcessServicesClient processServicesClient;


    public Test(){}

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public ResponseEntity<?> procesar() {
        disposeAndCreateContainer();
        return new ResponseEntity<>("Hi ", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/newDeploy",method = RequestMethod.GET)
    public ResponseEntity<?> deploy() {
        newDeploy();
        return new ResponseEntity<>("new deploy.. ", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/newInstance",method = RequestMethod.GET)
    public ResponseEntity<?> newInstanciaMethod() {
        newInstancia();
        return new ResponseEntity<>("new instance.. ", HttpStatus.CREATED);
    }


    public  void newDeploy() {
        /**
         * <groupId>uft</groupId>
         <artifactId>chapter02</artifactId>
         <version>1.0</version>
         * */
        String containerId = "saymon_new_container";
        System.out.println("\t######### Deploying container " + containerId);
        KieContainerResource resource = new KieContainerResource(containerId, new ReleaseId("com.myspace", "saymon", "1.0.0"));
        kieServicesClient.createContainer(containerId, resource);
    }

    public void newInstancia(){
        Map<String, Object> variables = new HashMap<>();
        Long processInstanceId = null;
                processInstanceId = processServicesClient.startProcess("sami_1.0.0-SNAPSHOT",
                "com.packt.masteringjbpm6.pizzadelivery", variables);

        System.out.println("processInstanceId = " + processInstanceId);
        //myDesignedProcess
    }

    public  void disposeAndCreateContainer() {
        System.out.println("== Disposing and creating containers ==");

        // Retrieve list of KIE containers
        List<KieContainerResource> kieContainers = kieServicesClient.listContainers().getResult().getContainers();
        if (kieContainers.size() == 0) {
            System.out.println("No containers available...");
            return;
        }

        // Dispose KIE container
        KieContainerResource container = kieContainers.get(0);
        String containerId = container.getContainerId();
        ServiceResponse<Void> responseDispose = kieServicesClient.disposeContainer(containerId);
        if (responseDispose.getType() == ServiceResponse.ResponseType.FAILURE) {
            System.out.println("Error disposing " + containerId + ". Message: ");
            System.out.println(responseDispose.getMsg());
            return;
        }
        System.out.println("Success Disposing container " + containerId);
        System.out.println("Trying to recreate the container...");

        // Re-create KIE container
        ServiceResponse<KieContainerResource> createResponse = kieServicesClient.createContainer(containerId, container);
        if(createResponse.getType() == ServiceResponse.ResponseType.FAILURE) {
            System.out.println("Error creating " + containerId + ". : ");
            System.out.println(responseDispose.getMsg());
            return;
        }
        System.out.println("Container recreated with success!");


        Map<String, Object> variables = new HashMap<>();
        Long processInstanceId = null;

//        processInstanceId = processServicesClient.startProcess("saymonasset_1.0.0-SNAPSHOT",
//                "saymonasset.SaymonBPMN", variables);
//
//        System.out.println("processInstanceId = " + processInstanceId);

//        ProcessInstance processInstance = queryServicesClient.findProcessInstanceById(processInstanceId);
//        TaskSummary task = processInstance.getActiveUserTasks().getItems().get(0);
//        userTaskServicesClient.claimTask(task.getContainerId(), task.getId(), usuarioLogueado);
//        userTaskServicesClient.startTask(task.getContainerId(), task.getId(), usuarioLogueado);
//
//        // Seteo el environment para JBPM. Esto le dice a JBPM, por ejemplo,
//        // a que backend invocar para los servicios REST
//        HashMap<String, Object> taskVars = new HashMap<String, Object>();
//        taskVars.put("env_t", jbpmEnvironment);
//
//        userTaskServicesClient.completeTask(task.getContainerId(), task.getId(), usuarioLogueado, taskVars);
    }

}