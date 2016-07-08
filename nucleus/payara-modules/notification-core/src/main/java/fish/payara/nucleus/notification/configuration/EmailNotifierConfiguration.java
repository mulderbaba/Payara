/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fish.payara.nucleus.notification.configuration;

import org.jvnet.hk2.config.Attribute;
import org.jvnet.hk2.config.Configured;

/**
 *
 * @author user
 */
@Configured
public interface EmailNotifierConfiguration extends NotifierConfiguration{
    
    @Attribute
    String getUsername();
    void setUsername(String username);
    
    @Attribute
    String getPassword();
    void setPassword(String password);
    
    @Attribute
    String getHost();
    void setHost(String host);
    
    @Attribute
    String getPort();
    void setPort(String port);
    
}
