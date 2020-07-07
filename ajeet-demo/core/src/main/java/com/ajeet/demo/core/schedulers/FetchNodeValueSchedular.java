package com.ajeet.demo.core.schedulers;

import com.ajeet.demo.core.services.NodeCreationService;
import com.ajeet.demo.core.services.impl.NodeCreationServiceImpl;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.PersistenceException;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import javax.servlet.ServletException;
import java.io.FileNotFoundException;
import java.io.IOException;

@Designate(ocd=FetchNodeValueSchedular.SchedularConfig.class)
@Component(service=Runnable.class)
public class FetchNodeValueSchedular implements Runnable{

    @Reference
    NodeCreationService creationService;

    private String scheduler_expression;
    private Boolean scheduler_concurrent;
    private Boolean serviceEnabled;

    @Activate
    @Modified
    public void activate(SchedularConfig nCreation) throws ServletException, IOException, LoginException {
        this.scheduler_expression = nCreation.scheduler_expression();
        this.scheduler_concurrent = nCreation.scheduler_concurrent();
        this.serviceEnabled = nCreation.serviceEnabled();
        run();
    }

    @Override
    public void run() {
        try {
            creationService.display();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @ObjectClassDefinition(name="node scheduled task")
    public static @interface SchedularConfig {
        @AttributeDefinition(name = "Cron-job expression")
        String scheduler_expression() default "0 0/1 * 1/1 * ? *";

        /* Concurrent execution of the job, which might happen if the previous execution has not yet finished when a new one is scheduled,
         can be prevented by setting the scheduler.concurrent property to false:
         */
        @AttributeDefinition(name = "Concurrent task",
                description = "Whether or not to schedule this task concurrently")
        boolean scheduler_concurrent() default false;

        //its used as a check condition if serviceEnable is true then run method will run otherwise not run
        @AttributeDefinition(name = "Enabled",
                description = "Enable Scheduler")
        boolean serviceEnabled() default false;
    }
}
