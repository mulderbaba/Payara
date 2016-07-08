/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fish.payara.nucleus.notification.domain.execoptions;

import fish.payara.nucleus.notification.configuration.NotifierType;

/**
 *
 * @author user
 */
public class EmailNotifierConfigurationExecutionOptions extends NotifierConfigurationExecutionOptions{

    public EmailNotifierConfigurationExecutionOptions() {
        super(NotifierType.LOG);
    }

    
    
}
