package com.test;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by simon on 26/09/19.
 */
@Component
public class WorkItemHandlerSimon implements WorkItemHandler {
    @Override
    public void executeWorkItem(WorkItem workItem, WorkItemManager workItemManager) {
           Object param1 = workItem.getParameter("Message");
        System.out.println("param1 = " + param1);

        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("Result","Mas nada friends Saymon..");
        workItemManager.completeWorkItem(workItem.getId(),resultMap);
    }

    @Override
    public void abortWorkItem(WorkItem workItem, WorkItemManager workItemManager) {
       workItemManager.abortWorkItem(workItem
       .getId());
    }
}
