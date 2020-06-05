package com.ajeet.demo.core.services.impl;

import com.ajeet.demo.core.models.DataInformation;
import com.ajeet.demo.core.services.NodeCreationService;
import com.google.gson.Gson;
import org.apache.sling.api.resource.*;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import javax.servlet.ServletException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Component(service = NodeCreationService.class, immediate = true)
@Designate(ocd = NodeCreationServiceImpl.NodeCreationDemo.class)
public class NodeCreationServiceImpl implements NodeCreationService {

   private String folder;
   private String node;
   private String path;

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Activate
    @Modified
    public void activate(NodeCreationDemo nCreation) throws ServletException, IOException, LoginException {
        this.folder = nCreation.folder();
        this.node = nCreation.node();
        this.path = nCreation.path();

       // System.out.println(this.folder+this.node+this.path);
       // NodeCreationServiceImpl impl = new NodeCreationServiceImpl();
       // System.out.println(this.folder+this.node+this.path);
        display();
    }

    public void display() throws PersistenceException, LoginException, FileNotFoundException {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put(ResourceResolverFactory.SUBSERVICE, "getResourceResolver");
        ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(param);
       // System.out.println("we are in display method with OutMap");
        Resource pathResource = resourceResolver.getResource(path);
        // if folder pre available then delete the folder
        Resource check = pathResource.getChild(folder);
        if (check != null) {
            resourceResolver.delete(check);
        }

      //  System.out.println("pathResource="+pathResource.getPath());

        Gson gson = new Gson();
       String jsonfilePath =  "C:\\Users\\Argildx\\Downloads\\MockData1.txt"; // json file location
        DataInformation [] dataInformation = gson.fromJson(new FileReader(jsonfilePath), DataInformation[].class);
        if(dataInformation.length>0){
            Resource folderResource = ResourceUtil.getOrCreateResource(resourceResolver,pathResource.getPath()+"/"+folder,"sling:folder","sling:folder",false);
            Resource nodeResource = ResourceUtil.getOrCreateResource(resourceResolver,folderResource.getPath()+"/"+node,"nt:unstructured","nt:unstructured",false);

            // iterate dataInformation array to create node on department basis
            Map<String, List<Map<String, String>>> outMap = new HashMap<String, List<Map<String, String>>>();


              for(DataInformation data : dataInformation){
                  List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("firstName:", data.getFirstName());
                        map.put("lastName:", data.getLastName());
                        map.put("department:", data.getDepartment());
                        map.put("Experience:", data.getExperience());
                        list.add(map);
                        //if
                        if(outMap.containsKey(data.getDepartment())){

                            List<Map<String, String>> list1 = outMap.get(data.getDepartment());
                            list1.addAll(list);
                            outMap.put(data.getDepartment(), list1);
                         //   System.out.println("if================"+data.getDepartment()+"===="+list1.size());
                        }else{
                          //  System.out.println("else================"+data.getDepartment()+"==="+list.size());
                            outMap.put(data.getDepartment(), list);

                        }


               // outMap.put(dataInformation[k].getDepartment(), list);
            }

            Map<String , String>  m1 = new HashMap<String ,String>();
            // using for-each loop for iteration over Map.entrySet()
            for (Map.Entry<String, List<Map<String, String>>> entry : outMap.entrySet()){
              //  System.out.println("Key = " + entry.getKey());

                List<Map<String, String>> val = entry.getValue();
                String json = gson.toJson(val);
              //  System.out.println("json = " + json);
                m1.put(entry.getKey(), json);
        }
        ModifiableValueMap childMap = nodeResource.adaptTo(ModifiableValueMap.class);
            childMap.putAll(m1);
        }

        resourceResolver.commit();
        resourceResolver.close();


    }

    @ObjectClassDefinition(name = "node creation demo")
    public @interface NodeCreationDemo{
        @AttributeDefinition(name = "Folder", description = "this is folder detail")
        String folder();
        @AttributeDefinition(name = "Node", description = "this is node detail")
        String node();
        @AttributeDefinition(name = "path", description = "this is path detail")
        String path();
    }

}
