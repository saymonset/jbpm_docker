package com.swagger.jbpm.http;

import com.dto.MamaDTO;
import com.ecological.NpsRresultDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.Tools;
import org.apache.http.util.EntityUtils;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.*;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.manager.context.EmptyContext;
import org.kie.server.api.model.definition.QueryDefinition;
import org.kie.server.api.model.instance.ProcessInstance;
import org.kie.server.api.model.instance.TaskInstance;
import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.QueryServicesClient;
import org.kie.server.client.UserTaskServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by simon on 30/09/19.
 */
@Component("testCall")
public class TestCall {
    private static final Logger logger = LoggerFactory.getLogger(TestCall.class);
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


     /**Todas las tareas de este actor con su status
      * Todo se hace de amnera manuual.. es para ver como trabaja la peticion HttpClient de apache*/
    public static void getTaskSummaryList() throws Exception {
        String status = "Reserved";
        String actor = "krisv";

        String addr = "http://localhost:8080/jbpm-console/rest/task/query?status=" + status + "&potentialOwner=" + actor;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet get = new HttpGet(addr);

            String authData = "wbadmin" + ":" + "wbadmin";
            String encoded = new sun.misc.BASE64Encoder().encode(authData.getBytes());
            get.setHeader("Content-Type", "application/json");
            get.setHeader("Authorization", "Basic " + encoded);
            get.setHeader("ACCEPT", "application/xml");

            HttpResponse cgResponse = client.execute(get);
            String content = EntityUtils.toString(cgResponse.getEntity());
            System.out.println(content);
        } catch (Exception e) {
            throw new Exception("Error consuming service.", e);
        }
    }


    /**Info del server.. Todo se hace de amnera manuual.. es para ver como trabaja*/
    public String server ()
         throws Exception {
        String content =null;
                String addr = "http://localhost:8180/kie-server/services/rest/server";
            try {
                HttpClient client = HttpClientBuilder.create().build();
                HttpGet get = new HttpGet(addr);

                String authData = "kieserver" + ":" + "kieserver1!";
                String encoded = new sun.misc.BASE64Encoder().encode(authData.getBytes());
                get.setHeader("Content-Type", "application/json");
                get.setHeader("Authorization", "Basic " + encoded);
                get.setHeader("ACCEPT", "application/json");

                HttpResponse cgResponse = client.execute(get);
                 content = EntityUtils.toString(cgResponse.getEntity());
                System.out.println(content);
            } catch (Exception e) {
                throw new Exception("Error consuming service.", e);
            }
            return content;
    }


    /**Instanciamos el jbpm .. parametros  vienen del swagger*/
    public MamaDTO Mama (String containerId, String idDefinitionJbpm, String userTask, String nameMama )
            throws Exception {




        List<MamaDTO> mamaDTOS = null;


        Map<String, Object> variables = new HashMap<>();
        variables.put("user",userTask);
      /*  com.Mama obj = new com.Mama();
        obj.setName(nameMama);
        variables.put("mama",obj);*/
        long processInstanceId = 0l;
     /*   processInstanceId = processServicesClient.startProcess(containerId,
                idDefinitionJbpm, variables);
        processInstance = queryServicesClient.findProcessInstanceById(processInstanceId, true);


        List<TaskSummary> tasks = processInstance.getActiveUserTasks().getItems();
*/
        QueryDefinition query = new QueryDefinition();
        query.setName("getAllTaskInstancesWithCustomVariables");
        query.setTarget("CUSTOM");
        query.setSource("java:jboss/datasources/JBPMDS");
        query.setExpression("select ti.* " +
                "from AuditTaskImpl ti  where ti.processid='"+idDefinitionJbpm+"'");

        queryServicesClient.replaceQuery(query);
        List<TaskInstance> tasks0 = queryServicesClient.query("getAllTaskInstancesWithCustomVariables","UserTasks", 0, 10, TaskInstance.class);
        System.out.println(tasks0);


        tasks0.forEach(task->{
            /**Imprimimos el status*/

           /* TaskInstance tall = userTaskServicesClient.findTaskById(task.getId());
            Map<String, Object>  variablesI =  tall.getInputData();
            Map<String, Object>  variablesO =  tall.getOutputData();*/
            System.out.println(task.getStatus() + " = status, " + task.getId() + " = " + task.getName() + ",owner=" + task.getActualOwner() + "");
            Map<String, Object> mapa = userTaskServicesClient.getTaskInputContentByTaskId(task.getContainerId(), task.getId());
       /*
            Map<String, Object> mapa2 = userTaskServicesClient.getTaskInputContentByTaskId(task.getContainerId(), task.getId());
            Map<String, Object> mapa3 = userTaskServicesClient.getTaskOutputContentByTaskId(task.getContainerId(), task.getId());*/
            System.out.println("stop here");


        });
     /*   List<com.Mama> mamas = new ArrayList<>();
        mamas.add(obj);
        mamaDTOS = mamas.stream().map(com.dto.MamaDTO::new).collect(Collectors.toList());*/;
        //.stream().map(FeatureDTO::new)


        return new MamaDTO();
    }

}
