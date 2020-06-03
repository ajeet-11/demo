package com.ajeet.demo.core.services.impl;

import com.ajeet.demo.core.services.DepartmentDetailService;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import java.util.*;
import java.util.stream.Collectors;

@Component(service = DepartmentDetailService.class)
@Designate(ocd = DepartmentDetailServiceImpl.displayDepartmentDetail.class)
public class DepartmentDetailServiceImpl implements DepartmentDetailService {

    String department;
    List<String> faculty = new ArrayList<String>();
    Map<String, List<String>> map = new HashMap<String, List<String>>();

    @Activate
    public void activate(displayDepartmentDetail dDetail){
        this.department = dDetail.department();
        this.faculty = Arrays.stream(dDetail.faculty()).collect(Collectors.toList());
        map.put(department, faculty);
    }

    @Modified
    public void modified(displayDepartmentDetail dDetail){
        this.department = dDetail.department();
        this.faculty = Arrays.stream(dDetail.faculty()).collect(Collectors.toList());
        map.put(department, faculty);
    }

    @Override
    public String getDepartment() {
        return department;
    }

    @Override
    public List<String> getFaculty() {
        return faculty;
    }

    @Override
    public Map<String, List<String>> getMap() {
        return map;
    }

    @ObjectClassDefinition(name = "department detail")
    public @interface displayDepartmentDetail{
        @AttributeDefinition(name = "department", description = "this is department detail")
        String department();
        @AttributeDefinition(name = "department", description = "this is faculty detail")
        String[] faculty();
    }

}
