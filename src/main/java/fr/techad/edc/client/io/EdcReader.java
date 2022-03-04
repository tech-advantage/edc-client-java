/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.io;

import fr.techad.edc.client.model.ContextItem;
import fr.techad.edc.client.model.I18NContent;
import fr.techad.edc.client.model.Information;
import fr.techad.edc.client.model.InvalidUrlException;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

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

    /**
     * Read the export information, for every publication, from the info.json files
     * The key of the returned map is the publication id and the value the associated information object
     *
     * @return a map containing the keys and label translations associated
     * @throws IOException if an error occurred during the read
     * @throws InvalidUrlException if the url is malformed
     */
    Map<String, Information> readInfo() throws IOException, InvalidUrlException;

    /**
     * Read the translated popover label for the given language code
     *
     * @param languageCodes
     * @return an object
     * @throws IOException
     * @throws InvalidUrlException
     */
    I18NContent readLabel(Set<String> languageCodes) throws IOException, InvalidUrlException;
}
