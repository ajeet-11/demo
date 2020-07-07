package com.ajeet.demo.core.services.impl;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.apache.sling.api.resource.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.HashMap;
import java.util.Map;

@Component(service = WorkflowProcess.class, property = {"process.label = Get Args from Workflow"})
public class GetArgsFromWorkflow implements WorkflowProcess {

    private String node;
    private String path;

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {
      //  MetaDataMap map = workItem.getWorkflow().getWorkflowData().getMetaDataMap();
        String str = metaDataMap.get("PROCESS_ARGS",String.class);
        if (str != null && !str.isEmpty() && str.contains(",")) {
            String[] strArray = str.split(",");
            if (strArray[0].startsWith("/")) {
                path = strArray[0];
                node = strArray[1];
            }else{
                node = strArray[0];
                path = strArray[1];
            }
        }
      //  System.out.println("node = "+node+", path = "+path);
        try {
            display();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }

    public void display() throws LoginException, PersistenceException {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put(ResourceResolverFactory.SUBSERVICE, "getResourceResolver");
        ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(param);

        Resource pathResource=resourceResolver.getResource(path);
        if(pathResource==null){
            pathResource = ResourceUtil.getOrCreateResource(resourceResolver,path,"sling:folder","sling:folder",false);
        }

      //  Resource pathResource1 = resourceResolver.getResource(pathResource.getPath());
        System.out.println(pathResource);
        // if folder pre available then delete the folder
        Resource check = pathResource.getChild(node);
        System.out.println(check);
        if (check != null) {
            resourceResolver.delete(check);
        }
        Resource nodeResource = ResourceUtil.getOrCreateResource(resourceResolver,pathResource.getPath()+"/"+node,"nt:unstructured","nt:unstructured",false);

        ModifiableValueMap childMap = nodeResource.adaptTo(ModifiableValueMap.class);
        childMap.put("published", "true");

        System.out.println(nodeResource);
        resourceResolver.commit();
        resourceResolver.close();
    }
}
