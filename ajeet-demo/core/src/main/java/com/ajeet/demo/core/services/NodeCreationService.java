package com.ajeet.demo.core.services;

import org.apache.sling.api.resource.LoginException;

import java.io.IOException;

public interface NodeCreationService {
  /*  String getFolder();
    String getNode();
    String getPath();*/
    void display() throws IOException, LoginException;

}
