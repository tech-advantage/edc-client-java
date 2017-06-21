/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.internal.http;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * TECH ADVANTAGE
 * All right reserved
 * Created by cochon on 19/06/2017.
 */
public class HttpClientTest {

    public HttpClient httpClient;

    @Before
    public void setup() {
        httpClient = new HttpClient();
    }

    @Test
    public void shouldGetAFile() throws IOException {
        String txt = httpClient.get("https://beta.easydoccontents.com/doc/context.json");

    }

}