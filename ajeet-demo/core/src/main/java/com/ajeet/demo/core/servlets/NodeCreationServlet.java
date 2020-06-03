package com.ajeet.demo.core.servlets;

import com.ajeet.demo.core.services.NodeCreationService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class,
        property = {"sling.servlet.methods="+ HttpConstants.METHOD_GET,
                    "sling.servlet.paths=/bin/nodeCreation"})
public class NodeCreationServlet extends SlingSafeMethodsServlet {

    @Reference
    NodeCreationService nodeCreationService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
     /*   String folder = nodeCreationService.getFolder();
        String node = nodeCreationService.getNode();
        String path = nodeCreationService.getPath();

        if (folder != null || !folder.isEmpty() && node != null || !node.isEmpty() && path != null
                || !path.isEmpty()) {
            Resource parent = request.getResourceResolver().getResource(path);
            String childName = ResourceUtil.createUniqueChildName(parent,folder);
            Resource child = ResourceUtil.getOrCreateResource(request.getResourceResolver(),parent.getPath()+"/"+childName,"nt:folder","nt:foler",false);
            String superChildName = ResourceUtil.createUniqueChildName(child,folder);
            Resource superChild = ResourceUtil.getOrCreateResource(request.getResourceResolver(),child.getName()+"/"+superChildName, "nt:unstructured","nt:unstructured",false);

            request.getResourceResolver().commit();
            request.getResourceResolver().close();
        }*/
    }
}
