/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.internal.model;

import fr.techad.edc.client.internal.model.ClientConfigurationImpl;
import fr.techad.edc.client.model.ClientConfiguration;
import fr.techad.edc.client.model.InvalidUrlException;
import org.junit.Assert;
import org.junit.Test;

/**
 * TECH ADVANTAGE
 * All right reserved
 * Created by cochon on 20/06/2017.
 */
public class ClientConfigurationImplTest {

    @Test
    public void shouldReturnTheDocumentationUrlAndAddSlash() throws InvalidUrlException {
        ClientConfiguration clientConfiguration = new ClientConfigurationImpl();
        clientConfiguration.setServerUrl("http://localhost");
        clientConfiguration.setDocumentationContext("mydoc");
        Assert.assertEquals("http://localhost/mydoc", clientConfiguration.getDocumentationUrl());
    }

    @Test
    public void shouldReturnTheDocumentationUrl() throws InvalidUrlException {
        ClientConfiguration clientConfiguration = new ClientConfigurationImpl();
        clientConfiguration.setServerUrl("http://localhost/");
        clientConfiguration.setDocumentationContext("mydoc");
        Assert.assertEquals("http://localhost/mydoc", clientConfiguration.getDocumentationUrl());
    }

    @Test
    public void shouldReturnTheDocumentationUrlWithDefaultDocumentationContext() throws InvalidUrlException {
        ClientConfiguration clientConfiguration = new ClientConfigurationImpl();
        clientConfiguration.setServerUrl("http://localhost/");
        Assert.assertEquals("http://localhost/doc", clientConfiguration.getDocumentationUrl());
    }

     @Test
    public void shouldReturnTheWebHelpUrlAndAddSlash() throws InvalidUrlException {
        ClientConfiguration clientConfiguration = new ClientConfigurationImpl();
        clientConfiguration.setServerUrl("http://localhost");
        clientConfiguration.setWebHelpContext("my-help");
        Assert.assertEquals("http://localhost/my-help", clientConfiguration.getWebHelpUrl());
    }

    @Test
    public void shouldReturnTheWebHelpUrl() throws InvalidUrlException {
        ClientConfiguration clientConfiguration = new ClientConfigurationImpl();
        clientConfiguration.setServerUrl("http://localhost/");
        clientConfiguration.setWebHelpContext("my-help");
        Assert.assertEquals("http://localhost/my-help", clientConfiguration.getWebHelpUrl());
    }

    @Test
    public void shouldReturnTheWebHelpUrlWithDefaultWebHelpContext() throws InvalidUrlException {
        ClientConfiguration clientConfiguration = new ClientConfigurationImpl();
        clientConfiguration.setServerUrl("http://localhost/");
        Assert.assertEquals("http://localhost/help", clientConfiguration.getWebHelpUrl());
    }

    @Test
    public void shouldGetServerUrl() throws InvalidUrlException {
        ClientConfiguration clientConfiguration = new ClientConfigurationImpl();
        clientConfiguration.setServerUrl("http://localhost:8080");
        Assert.assertEquals("http://localhost:8080", clientConfiguration.getServerUrl());
    }

    @Test(expected = InvalidUrlException.class)
    public void shouldThrowExceptionWhenTheServerUrlIsNull() throws InvalidUrlException {
        ClientConfiguration clientConfiguration = new ClientConfigurationImpl();
        clientConfiguration.setDocumentationContext("mydoc");
        clientConfiguration.getDocumentationUrl();
    }


}