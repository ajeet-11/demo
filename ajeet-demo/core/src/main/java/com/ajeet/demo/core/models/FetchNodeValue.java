package com.ajeet.demo.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Model(adaptables = Resource.class)
public class FetchNodeValue {

    Set<String> set;

    @SlingObject
    Resource resource;

    @PostConstruct
    public void activate(){
        String resourcePath = "/var/fold1/node1";
         set = resource.getResourceResolver().getResource(resourcePath).getValueMap().keySet();

        if(set!=null && !set.isEmpty()){
            set.remove("sling:resourceType");
            set.remove("jcr:primaryType");
        }
    }

    public Set<String> getSet(){
        return set;
    }
}
