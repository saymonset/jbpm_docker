package org.jbpm.spring.boot;

import org.jbpm.services.api.DeploymentService;
import org.jbpm.services.api.ProcessService;
import org.jbpm.services.api.RuntimeDataService;
import org.jbpm.services.api.model.DeployedUnit;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.TaskSummary;
import org.kie.internal.runtime.manager.context.ProcessInstanceIdContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by simon on 2/23/2019.
 */
@RestController
@RequestMapping("/quickstarts")
public class Quickstarts {
    Logger log = Logger.getLogger(this.getClass().getName());
    @Autowired
    private DeploymentService deploymentService;
    @Autowired
    private RuntimeManager runtimeManager;
    @Inject
    TaskService taskService;
    @Inject
    ProcessService processService;
    @Inject
    RuntimeDataService runtimeDataService;


    @RequestMapping("/")
    public Collection<String> index() {
        Collection<DeployedUnit> deployed = deploymentService.getDeployedUnits();
        Collection<String> units = new ArrayList<String>();

        for (DeployedUnit dUnit : deployed) {
            units.add(dUnit.getDeploymentUnit().getIdentifier());
        }

        return units;
    }

    /*
     <groupId>uft</groupId>
  <artifactId>chapter02</artifactId>
  <version>1.0</version>*/




    @RequestMapping(value="/deploy", method= RequestMethod.GET)
    public String quickstarts() {


        String outcome = "Deployment  deployed successfully";


        log.info("-------------Cuando sea en la misma transaccion para los procesos y las task--------------------");
        //Debe estar en el mismo contexto cuando el taskService viene de runtime.getTaskService()
        {
            RuntimeEngine runtime = runtimeManager.getRuntimeEngine(ProcessInstanceIdContext.get()); // null context
            KieSession ksession = runtime.getKieSession();

            TaskService taskService = runtime.getTaskService();





            // start a new process instance
            uft.chapter02.Person person = new uft.chapter02.Person();
            person.setName("Sami and Saymon... Por person");
            ksession.setGlobal("person", person);
            Map<String, Object> params = new HashMap<String, Object>();

            // params.put("person", person);
            params.put("name", "Sami and Saymon...");
            params.put("HelloService", new uft.chapter02.HelloService());

            ProcessInstance processInstance =
                    ksession.startProcess("quickstarts", params);


          /*  GetProcessInstanceVariableCommand command = new GetProcessInstanceVariableCommand();
            command.setProcessInstanceId(processInstance.getId());
            command.setVariableId("person");

            Object var = ksession.execute(command);
*/
            System.out.println("Process started ...");
            System.out.println("Process instance completed");

            runtimeManager.disposeRuntimeEngine(runtime);
        }



        return outcome;
    }


    @RequestMapping(value="/undeploy", method=RequestMethod.POST)
    public String undeploy(@RequestParam("id")String id) {
        String outcome = "";
        DeployedUnit deployed = deploymentService.getDeployedUnit(id);
        if (deployed != null) {
            deploymentService.undeploy(deployed.getDeploymentUnit());
            outcome = "Deployment " + id + " undeployed successfully";
        } else {
            outcome = "No deployment " + id + " found";
        }
        return outcome;
    }
}
