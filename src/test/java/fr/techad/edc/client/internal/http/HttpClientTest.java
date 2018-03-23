/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.internal.http;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * TECH ADVANTAGE
 * All right reserved
 * Created by cochon on 19/06/2017.
 */
public class HttpClientTest {

    private HttpClient httpClient;

    @Before
    public void setup() {
        httpClient = new HttpClient();
    }

    @Test
    public void shouldGetAFile() throws IOException, Error4xxException {
        String txt = httpClient.get("https://demo.easydoccontents.com/doc/edchelp/context.json");
        Assert.assertFalse(txt.isEmpty());

    }

}