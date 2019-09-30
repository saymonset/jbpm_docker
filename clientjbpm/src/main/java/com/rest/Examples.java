package com.rest;


import com.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kie.api.runtime.query.QueryContext;
import org.kie.internal.KieInternalServices;
import org.kie.internal.process.CorrelationKey;
import org.kie.remote.client.api.RemoteRestRuntimeEngineBuilder;
import org.kie.remote.client.api.RemoteRuntimeEngineFactory;
import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.KieContainerResourceList;
import org.kie.server.api.model.KieServerInfo;
import org.kie.server.api.model.ReleaseId;
import org.kie.server.api.model.definition.QueryDefinition;
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
import org.kie.api.KieServices;
import org.kie.api.logger.KieRuntimeLogger;
/**
 *
 * Para refrescar el jbpmn ultimo... debes desplegarlo en buiness-central deploy
 * Created by simon on 08/09/19.
 */
@RestController
@RequestMapping("/example")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.GET})
public class Examples {
    @Inject
    private KieServicesClient kieServicesClient;
    @Inject
    private ProcessServicesClient processServicesClient;
    @Inject
    private UserTaskServicesClient userTaskServicesClient;
    @Inject
    private QueryServicesClient queryServicesClient;
    private   ProcessInstance processInstance = null;
    private  Long processInstanceId = null;
    private com.Person person = null;
    @Inject
    private ObjectMapper mapper;

    /**Instancia la primera task of example evaluation*/
    @RequestMapping(value = "/one",method = RequestMethod.GET)
    public ResponseEntity<?> onecall() {

        Map<String, Object> variables = new HashMap<>();
        variables.put("user","wbadmin");
        variables.put("name", "Internet buscando");

        processInstanceId = 12l;
        processInstanceId = processServicesClient.startProcess("evaluation_1.0.0-SNAPSHOT",
                "saymonfirstestsami", variables);

        /***Obtenemos la instancia*/
        processInstance = queryServicesClient.findProcessInstanceById(processInstanceId, true);

        Map<String,Object> vars = processInstance.getVariables();

        vars.forEach((k,v)->{
            System.out.println("k = " + k + ", v = " + v);
            

            
        });

        /**Obtenemos las tareas de la instancia**/
        List<TaskSummary> tasks = processInstance.getActiveUserTasks().getItems();
        if (null != tasks){
            tasks.forEach((task)->{

                /**Imprimimos el status*/

                TaskInstance tall = userTaskServicesClient.findTaskById(task.getId());
                Map<String, Object>  variablesI =  tall.getInputData();
                Map<String, Object>  variablesO =  tall.getOutputData();
                System.out.println(task.getStatus() + " = status, " + task.getId() + " = " + task.getName() + ",owner=" + task.getActualOwner() + "");

                Map<String, Object> mapa = userTaskServicesClient.getTaskInputContentByTaskId(task.getContainerId(), task.getId());


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

                Map<String, Object>  variables1 = new HashMap<>();
                variables1.put("name_", "Salidajajajajjasalida..");
                /**completamos la tarea en el contexto*/
                userTaskServicesClient.completeTask(task.getContainerId(), task.getId(), "wbadmin",  variables1);

                Map<String, Object> mapa2 = userTaskServicesClient.getTaskInputContentByTaskId(task.getContainerId(), task.getId());
                Map<String, Object> mapa3 = userTaskServicesClient.getTaskOutputContentByTaskId(task.getContainerId(), task.getId());

                /***Obtenemos la instancia  actualizqada*/
                processInstance = queryServicesClient.findProcessInstanceById(processInstanceId);

                /**Obtenemos las tareas de la instancia actualizada**/
                //  task = processInstance.getActiveUserTasks().getItems().get(0);
                //    System.out.println(task.getStatus() + " = status, " + task.getId() + " = " + task.getName() + ",owner=" + task.getActualOwner() + "");
            });
        }





        System.out.println("processInstanceId = " + processInstanceId + ", status = " + processInstance.getState());
        return new ResponseEntity<>("Hi ", HttpStatus.CREATED);
    }

    /**Instancia la primera task of example evaluation*/
    @RequestMapping(value = "/two",method = RequestMethod.GET)
    public ResponseEntity<?> twocall() {

        Map<String, Object> variables = new HashMap<>();
        variables.put("user","wbadmin");
        person = new com.Person();
        person.setAge(41);
        person.setIsAdult(false);
        person.setName("ChuoChuo");
        variables.put("person",person );

        processInstanceId = 17l;
        processInstanceId = processServicesClient.startProcess("procesos2_1.0.0-SNAPSHOT",
                "PersonBPMN", variables);

        /***Obtenemos la instancia*/
         processInstance = queryServicesClient.findProcessInstanceById(processInstanceId, true);

       Map<String,Object> vars = processInstance.getVariables();

       vars.forEach((k,v)->{
           System.out.println("k = " + k + ", v = " + v);

         /*  if ("person".equalsIgnoreCase(k) ){
               Person p = (Person)v;
               System.out.println("p.getName() = " + p.getName());
           }*/
       });

        /**Obtenemos las tareas de la instancia**/
        List<TaskSummary> tasks = processInstance.getActiveUserTasks().getItems();
        if (null != tasks){
            tasks.forEach((task)->{

                /**Imprimimos el status*/

                TaskInstance tall = userTaskServicesClient.findTaskById(task.getId());
                Map<String, Object>  variablesI =  tall.getInputData();
                Map<String, Object>  variablesO =  tall.getOutputData();
                System.out.println(task.getStatus() + " = status, " + task.getId() + " = " + task.getName() + ",owner=" + task.getActualOwner() + "");

                Map<String, Object> mapa = userTaskServicesClient.getTaskInputContentByTaskId(task.getContainerId(), task.getId());


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
                Map<String,Object> vars1 = processInstance.getVariables();
              Map<String, Object>  variables1 = new HashMap<>();
                person = new com.Person();
                person.setAge(44);
                person.setIsAdult(false);
                person.setName("Sami");
                variables1.put("person",person );
                /**completamos la tarea en el contexto*/
                userTaskServicesClient.completeTask(task.getContainerId(), task.getId(), "wbadmin",  variables1);

                Map<String, Object> mapa2 = userTaskServicesClient.getTaskInputContentByTaskId(task.getContainerId(), task.getId());
                Map<String, Object> mapa3 = userTaskServicesClient.getTaskOutputContentByTaskId(task.getContainerId(), task.getId());

                /***Obtenemos la instancia  actualizqada*/
                processInstance = queryServicesClient.findProcessInstanceById(processInstanceId);
                Map<String,Object> vars2 = processInstance.getVariables();

                /**Obtenemos las tareas de la instancia actualizada**/
              //  task = processInstance.getActiveUserTasks().getItems().get(0);
              System.out.println(task.getStatus() + " = status, " + task.getId() + " = " + task.getName() + ",owner=" + task.getActualOwner() + "");
            });
        }





        System.out.println("processInstanceId = " + processInstanceId + ", status = " + processInstance.getState());
        return new ResponseEntity<>("Hi ", HttpStatus.CREATED);
    }
}
