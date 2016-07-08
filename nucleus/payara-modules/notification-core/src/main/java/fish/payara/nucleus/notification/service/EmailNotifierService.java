/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fish.payara.nucleus.notification.service;

import com.google.common.eventbus.Subscribe;
import fish.payara.nucleus.notification.configuration.EmailNotifier;
import fish.payara.nucleus.notification.configuration.EmailNotifierConfiguration;
import fish.payara.nucleus.notification.configuration.LogNotifier;
import fish.payara.nucleus.notification.configuration.LogNotifierConfiguration;
import fish.payara.nucleus.notification.configuration.NotifierType;
import fish.payara.nucleus.notification.domain.EmailNotificationEvent;
import javax.annotation.PostConstruct;
import org.glassfish.api.StartupRunLevel;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;

/**
 *
 * @author user
 */

@Service(name = "service-email")
@RunLevel(StartupRunLevel.VAL)
public class EmailNotifierService extends BaseNotifierService<EmailNotificationEvent, EmailNotifier, EmailNotifierConfiguration>{
    
    @PostConstruct
    void postConstruct() {
        register(NotifierType.LOG, EmailNotifier.class, EmailNotifierConfiguration.class, this);
    }
    
    @Override
    @Subscribe
    public void handleNotification(EmailNotificationEvent event) {
        System.out.println("THE EMAIL WAS SENT");
    }
    
}
