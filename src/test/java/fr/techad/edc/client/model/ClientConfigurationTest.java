/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * TECH ADVANTAGE
 * All right reserved
 * Created by cochon on 21/06/2017.
 */
public class ClientConfigurationTest {
    ClientConfiguration cConf=null;

    @Before
    public void setup() {
        cConf= new ClientConfiguration() {
            @Override
            public String getServerUrl() {
                return null;
            }

            @Override
            public void setServerUrl(String serverUrl) {

            }

            @Override
            public void setWebHelpContext(String webHelpContext) throws InvalidUrlException {

            }

            @Override
            public void setDocumentationContext(String documentationContext) throws InvalidUrlException {

            }

            @Override
            public String getWebHelpUrl() throws InvalidUrlException {
                return null;
            }

            @Override
            public String getDocumentationUrl() throws InvalidUrlException {
                return null;
            }
        };
    }
    @Test
    public void getWebHelpContext() throws Exception {
        Assert.assertEquals("help", cConf.getWebHelpContext());
    }

    @Test
    public void getDocumentationContext() throws Exception {
        Assert.assertEquals("doc", cConf.getDocumentationContext());
    }



}