package com.ajeet.demo.core.services.impl;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.osgi.service.component.annotations.Component;

import java.util.Collection;
import java.util.Set;

@Component(service = WorkflowProcess.class, property = {"process.label = Sample Workflow"})
public class WorkflowDemo implements WorkflowProcess {
    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {
      //  MetaDataMap map = workItem.getWorkflow().getWorkflowData().getMetaDataMap();
        String str = metaDataMap.get("PROCESS_ARGS",String.class);
       // System.out.println("the argument is "+str+" .......");
    }
}
