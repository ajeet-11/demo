package com.ajeet.demo.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class,
        property = {"sling.servlet.methods="+ HttpConstants.METHOD_GET,
                    "sling.servlet.paths=/bin/fetchNodeValue"})
public class FetchNodeValue extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        String resourcePath = "/var/fold1/node1";
        String department = request.getParameter("department");
        String value = request.getResourceResolver().getResource(resourcePath).getValueMap().get(department,String.class);
        response.getWriter().write(value);//println(value);
        /*  if(departDetailService.getMap().containsKey(department)){
            response.getWriter().println(departDetailService.getMap().get(department));
        }else{
            response.getWriter().println("department does not exist");
        }*/
    }
}
