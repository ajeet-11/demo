package com.ajeet.demo.core.models;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Model(adaptables = Resource.class)
public class TabShowHideModel {

    private String firstName;
    private String lastName;
    private String contact;
    private String address;
    private String hobby;

    private List<String> bookName = new ArrayList<>();
    private List<String> writtenBy = new ArrayList<>();
    private List<String> gamesName = new ArrayList<>();
    private List<String> developedBy = new ArrayList<>();
    private List<String> movieName = new ArrayList<>();
    private List<String> directedBy = new ArrayList<>();


    @SlingObject
    Resource resource;

    @PostConstruct
    protected void init() {
        firstName = resource.getValueMap().get("firstName",String.class);
        lastName = resource.getValueMap().get("lastName",String.class);
        contact = resource.getValueMap().get("contact",String.class);
        address = resource.getValueMap().get("address",String.class);
        hobby = resource.getValueMap().get("hobby",String.class);

        Resource multi=resource.getChild("multi");
        if(multi!=null)
        {
            Iterator it = multi.listChildren();
            while (it.hasNext()) {
                Resource item = (Resource) it.next();
                if(item.getValueMap().get("bookName", String.class)!=null){
                    bookName.add(item.getValueMap().get("bookName", String.class));
                    writtenBy.add(item.getValueMap().get("writtenBy", String.class));
                }

                if(item.getValueMap().get("gamesName", String.class)!=null){
                    gamesName.add(item.getValueMap().get("gamesName", String.class));
                    developedBy.add(item.getValueMap().get("developedBy", String.class));
                }

                if(item.getValueMap().get("movieName", String.class)!=null){
                    movieName.add(item.getValueMap().get("movieName", String.class));
                    directedBy.add(item.getValueMap().get("directedBy", String.class));
                }

            }
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getContact() {
        return contact;
    }

    public String getAddress() {
        return address;
    }

    public String getHobby() {
        return hobby;
    }

    public List<String> getBookName() {
        return bookName;
    }

    public List<String> getWrittenBy() {
        return writtenBy;
    }

    public List<String> getGamesName() {
        return gamesName;
    }

    public List<String> getDevelopedBy() {
        return developedBy;
    }

    public List<String> getMovieName() {
        return movieName;
    }

    public List<String> getDirectedBy() {
        return directedBy;
    }
}
