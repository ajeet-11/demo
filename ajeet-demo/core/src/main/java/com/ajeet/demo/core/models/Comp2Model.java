package com.ajeet.demo.core.models;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.iterators.TransformIterator;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.jcr.Session;
import java.util.*;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class})
public class Comp2Model {

    @SlingObject
    private Resource resource;
    @SlingObject
    private SlingHttpServletRequest request;



    private static final Logger log = LoggerFactory.getLogger(Comp2Model.class);

    @PostConstruct
    public void activate() {

        try {

            final ResourceResolver resolver = resource.getResourceResolver();

            Map<String, List<String>> map = new HashMap<>();

            String resourcePath = "/content/bfh/adbcd/a/jcr:content/root/responsivegrid";
            if(resourcePath!=null)
            {
                Iterator<Resource> it= resource.getResourceResolver().getResource(resourcePath).listChildren();
                while(it.hasNext()) {

                    List<String> list = new ArrayList<>();


                    Resource item = (Resource) it.next();

                    String firstName = item.getValueMap().get("firstName", String.class);
                    String lastName = item.getValueMap().get("lastName", String.class);
                    String contact = item.getValueMap().get("contact", String.class);

                    String address = item.getValueMap().get("address", String.class);
                    String age = item.getValueMap().get("age", String.class);
                    String instance = item.getValueMap().get("instance", String.class);

                    if (firstName!=null || lastName!=null || contact!=null || address!=null
                            || age!=null ||instance!=null)
                    {
                      //  list.clear();
                        list.add(firstName);
                        list.add(lastName);
                        list.add(contact);

                        list.add(address);
                        list.add(age);
                        list.add(instance);

                       // if(!map.containsKey(instance))
                            map.put(instance, list);

                    }
                }
            }

            @SuppressWarnings({ "unchecked", "rawtypes" })
            DataSource ds = new SimpleDataSource(new TransformIterator(new TreeSet<String>(map.keySet()).iterator(), new Transformer() {

                @Override
                public Object transform(Object o) {

                    String dropValue = (String) o;
                    ValueMap vm = new ValueMapDecorator(new HashMap<>());

                    vm.put("text", dropValue);
                    vm.put("value", map.get(dropValue));

                    return new ValueMapResource(resolver, new ResourceMetadata(), "nt:unstructured", vm);
                };
            }));
            this.request.setAttribute(DataSource.class.getName(), ds);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public String getList() {
        String response = "";
      String str =  resource.getValueMap().get("instances", String.class);
        if(str!=null || !str.isEmpty()){
            String [] data = str.substring(1,str.length()-1).split(",");
            for(int i =0;i<data.length;i++){
                if(i==0){
                    response+="firstName = "+data[i]+", ";
                }else  if(i==1){
                    response+="lastName = "+data[i]+", ";
                }else  if(i==2){
                    response+="contact = "+data[i]+", ";
                }else  if(i==3){
                    response+="address = "+data[i]+", ";
                }else  if(i==4){
                    response+="age = "+data[i]+", ";
                }else  if(i==5){
                    response+="instance = "+data[i];
                }
            }
        }
        return response;
    }
}
