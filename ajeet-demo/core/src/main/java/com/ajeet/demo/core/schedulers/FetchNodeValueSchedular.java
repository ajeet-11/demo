package com.ajeet.demo.core.schedulers;

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

import java.io.FileNotFoundException;

@Designate(ocd=FetchNodeValueSchedular.SchedularConfig.class)
@Component(service=Runnable.class)
public class FetchNodeValueSchedular implements Runnable{

    @Reference
    NodeCreationServiceImpl creationService;

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
    @ObjectClassDefinition(name="node scheduled task")
    public static @interface SchedularConfig {
        @AttributeDefinition(name = "Cron-job expression")
        String scheduler_expression() default "0 0/1 * 1/1 * ? *";

        @AttributeDefinition(name = "Concurrent task",
                description = "Whether or not to schedule this task concurrently")
        boolean scheduler_concurrent() default true;

        @AttributeDefinition(name = "Enabled",
                description = "Enable Scheduler")
        boolean serviceEnabled() default true;
    }
}
