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
import java.util.HashMap;
import java.util.Map;

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
        System.out.println(this.folder+this.node+this.path);
        display();
    }

    public void display() throws PersistenceException, LoginException, FileNotFoundException {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put(ResourceResolverFactory.SUBSERVICE, "getResourceResolver");
        ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(param);
        System.out.println("we are in display method");
        Resource pathResource = resourceResolver.getResource(path);

      //  System.out.println("pathResource="+pathResource.getPath());

        Gson gson = new Gson();
       String jsonfilePath =  "C:\\Users\\Argildx\\Downloads\\MockData1.txt"; // json file location
        DataInformation [] dataInformation = gson.fromJson(new FileReader(jsonfilePath), DataInformation[].class);
        if(dataInformation.length>0){
            Resource folderResource = ResourceUtil.getOrCreateResource(resourceResolver,pathResource.getPath()+"/"+folder,"sling:folder","sling:folder",false);
       // iterate dataInformation array to create node on department basis
            Resource nodeResource = ResourceUtil.getOrCreateResource(resourceResolver,folderResource.getPath()+"/"+node,"nt:unstructured","nt:unstructured",false);
            ModifiableValueMap childMap = nodeResource.adaptTo(ModifiableValueMap.class);
            childMap.put("department",dataInformation[0].getDepartment());
        }





     //   parent = ResourceUtil.getOrCreateResource(resolver,"/content/"+childName,"nt:unstructured","nt:unstructured",false);

     //   ResourceResolver resolver = resource.getResourceResolver();
      //  Resource parent = resolver.getResource(path);
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
