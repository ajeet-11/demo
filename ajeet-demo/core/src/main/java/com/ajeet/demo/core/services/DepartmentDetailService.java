package com.ajeet.demo.core.services;

import java.util.List;
import java.util.Map;

public interface DepartmentDetailService {
   // void setDepartment(String department);
    String getDepartment();
   // void setFaculty(List<String> faculty);
    List<String> getFaculty();
    Map<String, List<String>> getMap();

}
