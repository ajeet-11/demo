package com.ajeet.demo.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;

@Model(adaptables = Resource.class)
public class Trainee {

    private String name = "AJ";
    private Float stipend=8000f;
    @SlingObject
    Resource resource;

    Employee employee;

    @PostConstruct
    public void activate(){
        employee = resource.adaptTo(Employee.class);
        //employee = new Employee();
    }

    public String getName() {
        return name;
    }

    public Float getStipend() {
        return stipend;
    }

    public Employee getEmployee(){
        return employee;
    }
}
