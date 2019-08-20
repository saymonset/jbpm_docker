package org.jbpm.spring.boot;

import org.jbpm.process.instance.command.GetProcessInstanceVariableCommand;
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
import uft.chapter02.HelloService;
import uft.chapter02.Person;

import javax.inject.Inject;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by simon on 2/20/2019.
 */
@RestController
@RequestMapping("/evaluation")
public class Evaluation  {
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


    @RequestMapping(value="/deploy", method= RequestMethod.GET)
    public String deploy() {


        String outcome = "Deployment  deployed successfully";


        log.info("-------------Cuando sea en la misma transaccion para los procesos y las task--------------------");
        //Debe estar en el mismo contexto cuando el taskService viene de runtime.getTaskService()
        {
            RuntimeEngine runtime = runtimeManager.getRuntimeEngine(ProcessInstanceIdContext.get()); // null context
            KieSession ksession = runtime.getKieSession();

            TaskService taskService = runtime.getTaskService();

            // start a new process instance
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("employee", "john");
            params.put("reason", "Yearly performance evaluation");
            ProcessInstance processInstance =
                    ksession.startProcess("com.sample.evaluation", params);
            System.out.println("Process started ...");

            // complete Self Evaluation
            List<TaskSummary> tasks = taskService.getTasksAssignedAsPotentialOwner("john", "en-UK");


            TaskSummary task = tasks.get(0);
            System.out.println("'john' completing task " + task.getName() + ": " + task.getDescription());
            taskService.start(task.getId(), "john");
            Map<String, Object> results = new HashMap<String, Object>();
            results.put("performance", "exceeding");
            taskService.complete(task.getId(), "john", results);

            //II Parte Node , dos tasks

            // john from grupo HR que es solo declarado en el bpmn2
            tasks = taskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
            for (TaskSummary t:tasks){
                System.out.println(t.getName() + " = t.getName(), " + t.getSubject() + " = t.getSubject(), " + t.getActualOwner() + " = t.getActualOwner(), t.getStatus() = " + t.getStatus());
            }
            task = tasks.get(0);
            System.out.println("'john' completing task " + task.getName() + ": " + task.getDescription());
            taskService.claim(task.getId(), "john");
            taskService.start(task.getId(), "john");
            results = new HashMap<String, Object>();
            results.put("performance", "acceptable");
            taskService.complete(task.getId(), "john", results);


            // mary from grupo PM que es solo declarado en el bpmn2
            tasks = taskService.getTasksAssignedAsPotentialOwner("mary", "en-UK");
            task = tasks.get(0);
            System.out.println("'mary' completing task " + task.getName() + ": " + task.getDescription());
            taskService.claim(task.getId(), "mary");
            taskService.start(task.getId(), "mary");
            results = new HashMap<String, Object>();
            results.put("performance", "outstanding");
            taskService.complete(task.getId(), "mary", results);

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
