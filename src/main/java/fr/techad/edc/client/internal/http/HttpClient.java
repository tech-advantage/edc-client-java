/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.internal.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * TECH ADVANTAGE
 * All right reserved
 * Created by cochon on 19/06/2017.
 */
public class HttpClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClient.class);

    private CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

    public String get(String url) throws IOException {
        String txt;
        HttpGet httpGet = new HttpGet(url);
        try (CloseableHttpResponse response1 = closeableHttpClient.execute(httpGet)) {
            LOGGER.debug("response status: {}", response1.getStatusLine());
            HttpEntity entity1 = response1.getEntity();
            txt = EntityUtils.toString(entity1);
            // do something useful with the response body and ensure it is fully consumed
            EntityUtils.consume(entity1);
        }
        return txt;
    }

}
