package org.jbpm.spring.boot.config;

import org.jbpm.kie.services.impl.KModuleDeploymentUnit;
import org.jbpm.services.api.DefinitionService;
import org.jbpm.services.api.DeploymentService;
import org.jbpm.services.api.model.DeployedUnit;
import org.jbpm.services.api.model.DeploymentUnit;
import org.kie.api.runtime.manager.RuntimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

/**
 * Created by simon on 2/16/2019.
 */
@Configuration
public class JbpmConfiguration {

    Logger log = Logger.getLogger(this.getClass().getName());
    @Autowired
    private DeploymentService deploymentService;

    private DeploymentUnit deploymentUnit ;



    @Bean(name = "runtimeManager")
    public RuntimeManager runtimeManager() {
        deploymentUnit = new KModuleDeploymentUnit("uft", "chapter02", "1.0");
// deploy
        deploymentService.deploy(deploymentUnit);
/* retrieve deployed unit*/
       DeployedUnit deployed = deploymentService.getDeployedUnit(deploymentUnit.getIdentifier());
// get runtime manager
        RuntimeManager manager = deployed.getRuntimeManager();
        return manager;
    }



}
