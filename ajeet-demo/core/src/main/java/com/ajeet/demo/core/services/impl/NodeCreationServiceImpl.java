package com.ajeet.demo.core.services.impl;

import com.ajeet.demo.core.models.DataInformation;
import com.ajeet.demo.core.schedulers.FetchNodeValueSchedular;
import com.ajeet.demo.core.services.NodeCreationService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.sling.api.resource.*;
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
//@Designate(ocd = FetchNodeValueSchedular.NodeCreationDemo.class)
public class NodeCreationServiceImpl implements NodeCreationService {

   public String folder;
   public String node;
   public String path;

    @Reference
    ResourceResolverFactory resourceResolverFactory;

   /* @Activate
    @Modified
    public void activate(FetchNodeValueSchedular.NodeCreationDemo nCreation) throws ServletException, IOException, LoginException {
        this.folder = nCreation.folder();
        this.node = nCreation.node();
        this.path = nCreation.path();
        display();
    }*/

    public void display() throws PersistenceException, LoginException, FileNotFoundException {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put(ResourceResolverFactory.SUBSERVICE, "getResourceResolver");
        ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(param);

        Resource pathResource = resourceResolver.getResource(path);
        // if folder pre available then delete the folder
        Resource check = pathResource.getChild(folder);
        if (check != null) {
            resourceResolver.delete(check);
        }

        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        Map valueMap = new HashMap();
       String jsonfilePath =  "C:\\Users\\Argildx\\Downloads\\MockData1.txt"; // json file location
        try {
            JsonArray jsonArray = jsonParser.parse(new FileReader(jsonfilePath)).getAsJsonArray();
            if (jsonArray.size() > 0) {
                Resource folderResource = ResourceUtil.getOrCreateResource(resourceResolver,this.path+"/"+folder,"sling:folder","sling:folder",false);
                Resource nodeResource = ResourceUtil.getOrCreateResource(resourceResolver,folderResource.getPath()+"/"+node,"nt:unstructured","nt:unstructured",false);
                JsonArray allElementArray = new JsonArray();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                    if (valueMap.containsKey(jsonObject.get("department"))) {
                        String str = gson.toJson(valueMap.get(jsonObject.get("department")));
                        allElementArray = jsonParser.parse(str).getAsJsonArray();
                    }
                    allElementArray.add(jsonObject);
                    valueMap.put(jsonObject.get("department").getAsString(), allElementArray.toString());
                }
                ModifiableValueMap childMap = nodeResource.adaptTo(ModifiableValueMap.class);
                childMap.putAll(valueMap);
                resourceResolver.commit();
                resourceResolver.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }

}
