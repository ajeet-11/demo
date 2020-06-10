package com.ajeet.demo.core.schedulers;

import com.ajeet.demo.core.services.impl.NodeCreationServiceImpl;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;

@Designate(ocd=FetchNodeValueSchedular.NodeCreationDemo.class)
@Component(service=Runnable.class)
public class FetchNodeValueSchedular implements Runnable{

    NodeCreationServiceImpl creationService;

    @Reference
    private Scheduler scheduler;

    private static final Logger log = LoggerFactory.getLogger(FetchNodeValueSchedular.class);

    @Activate
    @Modified
    public void activate(NodeCreationDemo nCreation) {
        creationService.folder = nCreation.folder();
        creationService.node = nCreation.node();
        creationService.path = nCreation.path();
        //display();
    }

    private void addScheduler(NodeCreationDemo nCreation) {

        //Scheduler option takes the cron expression as a parameter and run accordingly
        ScheduleOptions scheduleOptions = scheduler.EXPR(nCreation.scheduler_expression());

         //Adding some parameters
        //scheduleOptions.name(nCreation.schdulerName());
        //scheduleOptions.canRunConcurrently(false);

        // Scheduling the job
        scheduler.schedule(this, scheduleOptions);

        log.info("Scheduler added");
    }

    @Override
    public void run() {
        try {
            creationService.display();
        } catch (PersistenceException e) {
            e.printStackTrace();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    @ObjectClassDefinition(name = "node-creation demo")
    public @interface NodeCreationDemo{

        @AttributeDefinition(name = "Cron-job expression")
        String scheduler_expression() default "*/30 * * * * ?";

        @AttributeDefinition(name = "Folder", description = "this is folder detail")
        String folder();

        @AttributeDefinition(name = "Node", description = "this is node detail")
        String node();

        @AttributeDefinition(name = "path", description = "this is path detail")
        String path();
    }
}
