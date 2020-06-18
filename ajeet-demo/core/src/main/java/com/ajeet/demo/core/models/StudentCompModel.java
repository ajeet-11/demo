package com.ajeet.demo.core.models;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.*;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class StudentCompModel {

    @Inject
    @Via("resource")
    private String firstName;
    @Inject
    @Via("resource")
    private String lastName;
    @Inject
    @Via("resource")
    private String contact;
    @Inject
    @Via("resource")
    private String email;
    @Inject
    @Via("resource")
    private String clas;
    @Inject
    @Via("resource")
    private String rollNo;
    @Inject
    @Via("resource")
    private String dob;
    @Inject
    @Via("resource")
    private String path;

    @SlingObject
    private SlingHttpServletRequest request;
    @OSGiService
    QueryBuilder queryBuilder;

    List list = new ArrayList();

    @PostConstruct
    public void activate() throws RepositoryException {
        Session session = request.getResourceResolver().adaptTo(Session.class);
        Map<String,String> predicateMap = new HashMap<String, String>();
        Resource res = request.getResourceResolver().getResource(path);
        String resourceType=null;
        if(res!=null){
            resourceType = "ajeetDemo/components/content/subjectComp";
        }
        if((res!=null) && (resourceType!=null && !resourceType.isEmpty())){
            predicateMap.put("path",path);
            predicateMap.put("type","jcr:content");
            predicateMap.put("property","sling:resourceType");
            predicateMap.put("property.0_value",resourceType);

            Query query = queryBuilder.createQuery(PredicateGroup.create(predicateMap), session);
            SearchResult searchResult=query.getResult();
            for(Hit hit : searchResult.getHits()) {
                String subjectName = request.getResourceResolver().getResource(hit.getPath()).getValueMap().get("subjectName", String.class);
                list.add(subjectName);
            }
            list.remove(res.getName());
        }
    }


    public String getFirstName(){
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public String getClas() {
        return clas;
    }

    public String getRollNo() {
        return rollNo;
    }

    public String getDob() {
        return dob;
    }

    public List getList() {
        return list;
    }
}
