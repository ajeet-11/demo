
package com.ajeet.demo.core.models;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.settings.SlingSettingsService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import java.util.*;

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

@Model(adaptables = SlingHttpServletRequest.class)
public class TagCompModel {

    @Inject
    @Via("resource")
    private String tags;

    @SlingObject
    private SlingHttpServletRequest request;

    @OSGiService
    QueryBuilder queryBuilder;

    private List list = new ArrayList();

    @PostConstruct
    protected void init() throws RepositoryException {

        Session session = request.getResourceResolver().adaptTo(Session.class);
        Map<String,String> predicateMap = new HashMap<String, String>();

        TagManager tagManager = request.getResourceResolver().adaptTo(TagManager.class);
        Tag[] tag = tagManager.getNamespaces();
        for(int i=0;i<tag.length;i++){
            Iterator<Tag> it = tag[i].listAllSubTags();
            if(it.hasNext()){
                Tag tag1 = it.next();
                if(tag1.getName().equals(tags)){
                    //id = tag1.getTagID();

                    predicateMap.put("path","/content");
                    predicateMap.put("type","jcr:content");
                    predicateMap.put("property","cq:tags");
                    predicateMap.put("property.0_value",tag1.getTagID());

                    Query query = queryBuilder.createQuery(PredicateGroup.create(predicateMap), session);
                    SearchResult searchResult=query.getResult();
                    for(Hit hit : searchResult.getHits()) {
                        String pageName = request.getResourceResolver().getResource(hit.getPath()).getParent().getName(); //.getValueMap().get("subjectName", String.class);
                        list.add(pageName);
                    }
                    break;
                }

            }
        }

    }
    public List getList(){
        return list;
    }
}
