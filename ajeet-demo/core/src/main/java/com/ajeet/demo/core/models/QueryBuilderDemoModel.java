package com.ajeet.demo.core.models;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class})
public class QueryBuilderDemoModel {

    @SlingObject
    private SlingHttpServletRequest request;
    @SlingObject
    Resource resource;
    @OSGiService
    QueryBuilder queryBuilder;
    private List list = new ArrayList();

    @PostConstruct
    public void activate() throws RepositoryException {

        Session session = request.getResourceResolver().adaptTo(Session.class);
        PredicateGroup root = PredicateGroup.create(request.getParameterMap());
        Query query = queryBuilder.createQuery(root, session);
        Map<String,String> predicateMap = new HashMap<String, String>();
        String path = resource.getValueMap().get("path", String.class);
        String resourceType = resource.getResourceResolver().getResource(path).getChild("jcr:content").getValueMap().get("sling:resourceType", String.class);
     //   String resourceType = resource.getValueMap().get("sling:resourceType", String.class);

        System.out.println(resourceType);
        if(path!=null || !path.isEmpty()){
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
            list.remove(0);
        }
    }

    public List getList(){
        return list;
    }
}
