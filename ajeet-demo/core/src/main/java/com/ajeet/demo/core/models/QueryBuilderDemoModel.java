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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class QueryBuilderDemoModel {

    @SlingObject
    private SlingHttpServletRequest request;

    @Inject
    @Via("resource")
    private String path;

    @OSGiService
    QueryBuilder queryBuilder;

    private List list = new ArrayList();

    @PostConstruct
    public void activate() throws RepositoryException {

        Session session = request.getResourceResolver().adaptTo(Session.class);
        PredicateGroup root = PredicateGroup.create(request.getParameterMap());
        Query query = queryBuilder.createQuery(root, session);

        Map<String,String> predicateMap = new HashMap<String, String>();
        Resource res = request.getResourceResolver().getResource(path);
        String resourceType=null;
        if(res!=null){
            resourceType = res.getChild("jcr:content").getValueMap().get("sling:resourceType", String.class);
        }

        if((res!=null) && (resourceType!=null && !resourceType.isEmpty())){
            predicateMap.put("path",path);
            predicateMap.put("type","jcr:content");
            predicateMap.put("property","sling:resourceType");
            predicateMap.put("property.0_value",resourceType);

            Query query1 = queryBuilder.createQuery(PredicateGroup.create(predicateMap), session);
            SearchResult searchResult=query1.getResult();
            for(Hit hit : searchResult.getHits()) {

                String title = request.getResourceResolver().getResource(hit.getPath()).getParent().getName();
                list.add(title);
            }
            list.remove(res.getName());
        }
    }

    public List getList(){
        return list;
    }

}
