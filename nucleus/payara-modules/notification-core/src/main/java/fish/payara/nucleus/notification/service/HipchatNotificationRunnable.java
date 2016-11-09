/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2016 Payara Foundation and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://github.com/payara/Payara/blob/master/LICENSE.txt
 * See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * The Payara Foundation designates this particular file as subject to the "Classpath"
 * exception as provided by the Payara Foundation in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package fish.payara.nucleus.notification.service;

import fish.payara.nucleus.notification.domain.execoptions.HipchatNotifierConfigurationExecutionOptions;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author mertcaliskan
 */
public class HipchatNotificationRunnable implements Runnable {

    private Logger logger = Logger.getLogger(HipchatNotifierService.class.getCanonicalName());

    private static final String HTTP_PROTOCOL = "POST";
    private static final String ACCEPT_TYPE = "text/plain";
    private static final String ENDPOINT = "https://api.hipchat.com";
    private static final String RESOURCE = "/v2/room/{0}/notification?auth_token={1}";

    private HipchatNotifierConfigurationExecutionOptions executionOptions;
    private String userMessage;
    private String message;

    HipchatNotificationRunnable(HipchatNotifierConfigurationExecutionOptions executionOptions,
                                String userMessage,
                                String message) {

        this.executionOptions = executionOptions;
        this.userMessage = userMessage;
        this.message = message;
    }

    @Override
    public void run() {
        String urlStr = MessageFormat.format(RESOURCE, executionOptions.getRoomName(), executionOptions.getToken());
        try {
            URL url = new URL(concatenateEndpoint(urlStr));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(HTTP_PROTOCOL);
            conn.setRequestProperty("Content-Type", ACCEPT_TYPE);
            conn.connect();

            try(OutputStream outputStream = conn.getOutputStream()) {
                outputStream.write((userMessage + " - " + message).getBytes());
                outputStream.flush();

                if (conn.getResponseCode() != 204) {
                    logger.log(Level.SEVERE,
                            "Error occurred while connecting HipChat. Check your room name and token. http response code",
                            conn.getResponseCode());
                }
            }

        }
        catch (MalformedURLException e) {
            logger.log(Level.SEVERE, "Error occurred while accessing URL: " + concatenateEndpoint(urlStr), e);
        }
        catch (ProtocolException e) {
            logger.log(Level.SEVERE, "Specified URL is not accepting protocol defined: " + HTTP_PROTOCOL, e);
        }
        catch (IOException e) {
            logger.log(Level.SEVERE, "IO Error while accessing URL: " + concatenateEndpoint(urlStr), e);
        }
    }


    private String concatenateEndpoint(String urlStr) {
        return ENDPOINT + urlStr;
    }

}