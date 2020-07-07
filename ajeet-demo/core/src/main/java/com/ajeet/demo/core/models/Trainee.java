package com.ajeet.demo.core.models;

import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.jcr.base.util.AccessControlUtil;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.settings.SlingSettingsService;
import org.osgi.service.component.annotations.Reference;

import javax.annotation.PostConstruct;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Trainee {

  /*  private String name = "AJ";
    private Float stipend=8000f;*/

    @SlingObject
    private SlingHttpServletRequest request;

    String id;

  //  Employee employee;
    List list = new ArrayList();

    @Reference
    private SlingSettingsService slingSettingsService;


    @PostConstruct
    public void activate() throws RepositoryException, PersistenceException {
      //  if (this.slingSettingsService.getRunModes().contains("publish")){

            ResourceResolver resourceResolver = request.getResourceResolver();
            Session session = resourceResolver.adaptTo(Session.class);
            id = session.getUserID();

            UserManager um = AccessControlUtil.getUserManager(session);
            Authorizable authorizable = um.getAuthorizable("ajeet_cug");

            if (authorizable.isGroup()){
                Group cug = (Group) authorizable;
                cug.addMember(um.getAuthorizable(id));

                session.save();
                resourceResolver.commit();
            }
       // }


       /* Iterator iterator = cug.getMembers();
        while (iterator.hasNext()){
            list.add(iterator.next());
        }*/


        //employee = resource.adaptTo(Employee.class);
        //employee = new Employee();
    }

    public String getId(){
        return id;
    }
    public List getList(){
        return list;
    }

    /*public String getName() {
        return name;
    }
    public Float getStipend() {
        return stipend;
    }
    public Employee getEmployee(){
        return employee;
    }*/
}
