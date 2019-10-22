package com.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.task.TaskService;
import org.kie.internal.task.api.UserInfo;
import org.kie.remote.client.api.RemoteRuntimeEngineFactory;
import org.kie.remote.client.internal.command.RemoteConfiguration;
import org.kie.remote.client.internal.command.RemoteRuntimeEngine;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.definition.QueryDefinition;
import org.kie.server.api.model.instance.ProcessInstance;
import org.kie.server.api.model.instance.TaskInstance;
import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.client.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
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
    @Value("${baseURI}")
    private String baseURI;
    @Value("${jbpm.user}")
    private String user;
    @Value("${jbpm.password}")
    private String password;

    /**Instancia la primera task of example evaluation*/
    @RequestMapping(value = "/one",method = RequestMethod.GET)
    public ResponseEntity<?> onecall() {

        Map<String, Object> variables = new HashMap<>();
        variables.put("user","wbadmin");

        processInstanceId = 5l;
        processInstanceId = processServicesClient.startProcess("ofeclipse_1.0.0-SNAPSHOT",
                "com.mybpmn", variables);

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

    @RequestMapping(value = "/two",method = RequestMethod.GET)
    public ResponseEntity<?> twocall() {

        Map<String, Object> variables = new HashMap<>();

        processInstanceId = 5l;
        processInstanceId = processServicesClient.startProcess("Exception_1.0.0-SNAPSHOT",
                "com.myspace.ProcessBPMN", variables);

        /***Obtenemos la instancia*/
        processInstance = queryServicesClient.findProcessInstanceById(processInstanceId, true);
        processServicesClient.signalProcessInstance("Exception_1.0.0-SNAPSHOT",processInstance.getId(),"exception-signal", variables);
        /**   processServicesClient.signalProcessInstance(JbpmConstants.CONTAINER_ID.getValue(), processInstanceId,
         JbpmConstants.ASSIGN_PROJECT_TO_BUDGET_SIGNAL.getValue(), params);*/

        System.out.println("processInstanceId = " + processInstanceId + ", status = " + processInstance.getState());
        return new ResponseEntity<>("Hi ", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/tree",method = RequestMethod.GET)
    public ResponseEntity<?> treecall() {

        Map<String, Object> variables = new HashMap<>();
        variables.put("emloyee", "simon");
        variables.put("reason", "Yearly performance evaluation");
        processInstanceId = 56l;
        processInstanceId = processServicesClient.startProcess("Evaluation_1.0.0-SNAPSHOT",
                "com.evaluationbpmn", variables);


        /***Obtenemos la instancia*/
        processInstance = queryServicesClient.findProcessInstanceById(processInstanceId, true);


        List<QueryDefinition> queryDefs = queryServicesClient.getQueries(0, 10);
        System.out.println(queryDefs);

        QueryDefinition query = new QueryDefinition();
        query.setName("getAllTaskInstancesWithCustomVariables");
        query.setTarget("CUSTOM");
        query.setSource("java:jboss/datasources/JBPMDS");
        query.setExpression("select ti.* " +
                "from AuditTaskImpl ti where ti.actualOwner='simon' and ti.processInstanceId="+processInstanceId);
        queryServicesClient.replaceQuery(query);
        List<TaskInstance> tasks0 = queryServicesClient.query("getAllTaskInstancesWithCustomVariables","UserTasks", 0, 10, TaskInstance.class);

        /***Obtenemos la instancia  actualizqada*/
        processInstance = queryServicesClient.findProcessInstanceById(processInstanceId);
        /**Obtenemos las tareas de la instancia actualizada**/

        /**Obtenemos las tareas de la instancia**/
        TaskSummary task = processInstance.getActiveUserTasks().getItems().get(0);

        List<TaskSummary> tasks = userTaskServicesClient.findTasks("simon", 0, 10);




        /**Imprimimos el status*/
        System.out.println(task.getStatus() + " = status, " + task.getId() + " = " + task.getName() + ",owner=" + task.getActualOwner() + "");

        /**startTask la tarea en el contexto*/
        userTaskServicesClient.startTask(task.getContainerId(), task.getId(), "simon");

        /***Obtenemos la instancia  actualizqada*/
        processInstance = queryServicesClient.findProcessInstanceById(processInstanceId);
        /**Obtenemos las tareas de la instancia actualizada**/
        task = processInstance.getActiveUserTasks().getItems().get(0);
        System.out.println(task.getStatus() + " = status, " + task.getId() + " = " + task.getName() + ",owner=" + task.getActualOwner() + "");
        variables = new HashMap<>();
        variables.put("performance", "Yearly performance evaluation");
        /**completamos la tarea en el contexto*/
        userTaskServicesClient.completeTask(task.getContainerId(), task.getId(), "simon",  variables);



        /***Obtenemos la instancia  actualizqada*/
        processInstance = queryServicesClient.findProcessInstanceById(processInstanceId);
        /**Obtenemos las tareas de la instancia actualizada**/
        task = processInstance.getActiveUserTasks().getItems().get(0);
        System.out.println(task.getStatus() + " = status, " + task.getId() + " = " + task.getName() + ",owner=" + task.getActualOwner() + "");
        /**startTask la tarea en el contexto*/
        userTaskServicesClient.startTask(task.getContainerId(), task.getId(), "simon");
        /***Obtenemos la instancia  actualizqada*/
        processInstance = queryServicesClient.findProcessInstanceById(processInstanceId);
        /**Obtenemos las tareas de la instancia actualizada**/
        task = processInstance.getActiveUserTasks().getItems().get(0);
        System.out.println(task.getStatus() + " = status, " + task.getId() + " = " + task.getName() + ",owner=" + task.getActualOwner() + "");
        variables = new HashMap<>();
        variables.put("performance", "Yearly performance evaluation");
        /**completamos la tarea en el contexto*/
        userTaskServicesClient.completeTask(task.getContainerId(), task.getId(), "simon",  variables);
        /***Obtenemos la instancia  actualizqada*/
        processInstance = queryServicesClient.findProcessInstanceById(processInstanceId);
        /**Obtenemos las tareas de la instancia actualizada**/
        task = processInstance.getActiveUserTasks().getItems().get(0);
        System.out.println(task.getStatus() + " = status, " + task.getId() + " = " + task.getName() + ",owner=" + task.getActualOwner() + "");


        /***Obtenemos la instancia  actualizqada*/
        processInstance = queryServicesClient.findProcessInstanceById(processInstanceId);
        /**Obtenemos las tareas de la instancia actualizada**/
        task = processInstance.getActiveUserTasks().getItems().get(0);
        System.out.println(task.getStatus() + " = status, " + task.getId() + " = " + task.getName() + ",owner=" + task.getActualOwner() + "");
        /**startTask la tarea en el contexto*/
        userTaskServicesClient.startTask(task.getContainerId(), task.getId(), "simon");
        /***Obtenemos la instancia  actualizqada*/
        processInstance = queryServicesClient.findProcessInstanceById(processInstanceId);
        /**Obtenemos las tareas de la instancia actualizada**/
        task = processInstance.getActiveUserTasks().getItems().get(0);
        System.out.println(task.getStatus() + " = status, " + task.getId() + " = " + task.getName() + ",owner=" + task.getActualOwner() + "");
        variables = new HashMap<>();
        variables.put("performance", "Yearly performance evaluation");
        /**completamos la tarea en el contexto*/
        userTaskServicesClient.completeTask(task.getContainerId(), task.getId(), "simon",  variables);
        /***Obtenemos la instancia  actualizqada*/
        processInstance = queryServicesClient.findProcessInstanceById(processInstanceId);
        /**Obtenemos las tareas de la instancia actualizada**/
        task = processInstance.getActiveUserTasks().getItems().get(0);
        System.out.println(task.getStatus() + " = status, " + task.getId() + " = " + task.getName() + ",owner=" + task.getActualOwner() + "");




        /***Obtenemos la instancia  actualizqada*/
        processInstance = queryServicesClient.findProcessInstanceById(processInstanceId);
        /**Obtenemos las tareas de la instancia actualizada**/
        task = processInstance.getActiveUserTasks().getItems().get(0);
        System.out.println(task.getStatus() + " = status, " + task.getId() + " = " + task.getName() + ",owner=" + task.getActualOwner() + "");
        /**startTask la tarea en el contexto*/
        userTaskServicesClient.startTask(task.getContainerId(), task.getId(), "simon");
        /***Obtenemos la instancia  actualizqada*/
        processInstance = queryServicesClient.findProcessInstanceById(processInstanceId);
        /**Obtenemos las tareas de la instancia actualizada**/
        task = processInstance.getActiveUserTasks().getItems().get(0);
        System.out.println(task.getStatus() + " = status, " + task.getId() + " = " + task.getName() + ",owner=" + task.getActualOwner() + "");

        //Map<String, Object> content = userTaskServicesClient.getTaskInputContentByTaskId(task.getId());
      /*  for (Map.Entry<?, ?> entry : content.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }*/

        /**completamos la tarea en el contexto*/
        userTaskServicesClient.completeTask(task.getContainerId(), task.getId(), "simon",  variables);
        return new ResponseEntity<>("Hi ", HttpStatus.CREATED);
    }


    @RequestMapping(value = "/four",method = RequestMethod.GET)
    public ResponseEntity<?> four() {

        Map<String, Object> variables = new HashMap<>();
        variables.put("user", "sami");
        processInstanceId = 3l;
      //  processInstanceId = processServicesClient.startProcess("Evaluation_1.0.0-SNAPSHOT",
               // "com.evaluationbpmn", variables);


        /***Obtenemos la instancia*/
        processInstance = queryServicesClient.findProcessInstanceById(processInstanceId, true);
        List<TaskSummary> tasks = userTaskServicesClient.findTasksOwned("simon", 0, 10);
        List<TaskSummary> tasks2 = userTaskServicesClient.findTasksOwned("sami", 0, 10);

        TaskSummary task = tasks.get(0);

        /**startTask la tarea en el contexto*/
        userTaskServicesClient.completeTask(task.getContainerId(), task.getId(), "simon",null);


        return new ResponseEntity<>("Hi ", HttpStatus.CREATED);
    }


    @RequestMapping(value = "/powner",method = RequestMethod.GET)
    public ResponseEntity<?> potentialOwner() {

        KieServicesConfiguration conf = KieServicesFactory.newRestConfiguration(baseURI, user, password);
        conf.setMarshallingFormat(MarshallingFormat.JAXB);
        KieServicesClient client = KieServicesFactory.newKieServicesClient(conf);
        UserTaskServicesClient userTaskClient = client.getServicesClient(UserTaskServicesClient.class);

        List<TaskSummary> tasks = userTaskClient.findTasksAssignedAsPotentialOwner(null, 0, 10);
        System.out.println("\t######### Tasks: " +tasks);

        return new ResponseEntity<>("Hi ", HttpStatus.CREATED);
    }
}
