/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client;

import fr.techad.edc.client.internal.Constants;
import fr.techad.edc.client.model.InvalidUrlException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * TECH ADVANTAGE
 * All right reserved
 * Created by cochon on 20/06/2017.
 */
public class EdcClientSingletonTest {

    private static final String DEFAULT_URL = "https://demo.easydoccontents.com/help/home";

    @Before
    public void setup() {
        EdcClientSingleton.getInstance().setServerUrl(Constants.SERVER_URL);
        try {
            EdcClientSingleton.getInstance().setWebHelpContextUrl("help");
            EdcClientSingleton.getInstance().setDocumentationContextUrl("doc");
        } catch (InvalidUrlException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldGetUrl() throws IOException, InvalidUrlException {
        String url = EdcClientSingleton.getInstance().getContextWebHelpUrl("fr.techad.edc", "help.center", "en");
        Assert.assertEquals("https://demo.easydoccontents.com/help/context/edchelp/fr.techad.edc/help.center/en/0", url);
    }

    @Test
    public void shouldGetUrlWithForceReload() throws IOException, InvalidUrlException {
        EdcClientSingleton.getInstance().getContextWebHelpUrl("fr.techad.edc", "help.center", "en");
        EdcClientSingleton.getInstance().forceReload();
        String url = EdcClientSingleton.getInstance().getContextWebHelpUrl("fr.techad.edc", "help.center", "en");
        Assert.assertEquals("https://demo.easydoccontents.com/help/context/edchelp/fr.techad.edc/help.center/en/0", url);
    }


    @Test
    public void shouldGetDefaultUrlErrorOnMainKey() throws IOException, InvalidUrlException {
        EdcClientSingleton.getInstance().forceReload();
        String url = EdcClientSingleton.getInstance().getContextWebHelpUrl(null, "help.center", "en");
        Assert.assertEquals(DEFAULT_URL, url);
    }

    @Test
    public void shouldGetNullUrlErrorOnSubKey() throws IOException, InvalidUrlException {
        EdcClientSingleton.getInstance().forceReload();
        String url = EdcClientSingleton.getInstance().getContextWebHelpUrl("fr.techad.edc", null, "en");
        Assert.assertEquals(DEFAULT_URL, url);
    }

    @Test
    public void shouldGetDefaultUrlOnLanguageCode() throws IOException, InvalidUrlException {
        EdcClientSingleton.getInstance().forceReload();
        String url = EdcClientSingleton.getInstance().getContextWebHelpUrl("fr.techad.edc", "help.center", null);
        Assert.assertEquals(DEFAULT_URL, url);
    }

    @Test
    public void shouldGetUrlWithNewWebHelpContext() throws IOException, InvalidUrlException {
        EdcClientSingleton.getInstance().setWebHelpContextUrl("my-help");
        String url = EdcClientSingleton.getInstance().getContextWebHelpUrl("fr.techad.edc", "help.center", "en");
        Assert.assertEquals("https://demo.easydoccontents.com/my-help/context/edchelp/fr.techad.edc/help.center/en/0", url);
    }

    @Test(expected = InvalidUrlException.class)
    public void shouldThrowsExceptionOnNullWebHelpContext() throws IOException, InvalidUrlException {
        EdcClientSingleton.getInstance().setWebHelpContextUrl(null);
    }

    @Test(expected = IOException.class)
    public void shouldThrowsExceptionOnUnknownDocumentationContext() throws IOException, InvalidUrlException {
        EdcClientSingleton.getInstance().setDocumentationContextUrl("my-doc");
        EdcClientSingleton.getInstance().forceReload();
        String url = EdcClientSingleton.getInstance().getContextWebHelpUrl("fr.techad.edc", "help.center", null);
    }

    @Test(expected = InvalidUrlException.class)
    public void shouldThrowsExceptionOnNullServer() throws IOException, InvalidUrlException {
        EdcClientSingleton.getInstance().setServerUrl(null);
        EdcClientSingleton.getInstance().forceReload();
        String url = EdcClientSingleton.getInstance().getContextWebHelpUrl("fr.techad.edc", "help.center", "en");
    }
}