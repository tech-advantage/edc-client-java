/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.io;

import fr.techad.edc.client.model.ContextItem;
import fr.techad.edc.client.model.InvalidUrlException;

import java.io.IOException;
import java.util.Map;

/**
 * IO Utility to read all information of edc. In generally, it's the export.
 */
public interface EdcReader {
    /**
     * Read all context items and associate it in the map.
     * The key of the map is the contextual key and he value is the associated context.
     *
     * @return a map which associate the contextual key with the content item.
     * @throws IOException         if an error is occured during the read
     * @throws InvalidUrlException if the url is malformed
     */
    Map<String, ContextItem> readContext() throws IOException, InvalidUrlException;
}
