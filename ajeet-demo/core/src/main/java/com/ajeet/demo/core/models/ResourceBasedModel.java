package com.ajeet.demo.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Model(adaptables = Resource.class)
public class ResourceBasedModel {

    @Inject
    String name;
    @Inject
    String email;

    public String getName() {
        return name;
    }

    public String getEmail(){
        return email;
    }

}
