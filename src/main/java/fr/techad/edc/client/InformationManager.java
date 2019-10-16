package fr.techad.edc.client;

import fr.techad.edc.client.model.Information;
import fr.techad.edc.client.model.InvalidUrlException;

import java.io.IOException;
import java.util.Map;

public interface InformationManager {

    /**
     * Load the information for every publication, from the info.json files
     *
     * @throws IOException if an IO Exception occurred while reading the file
     * @throws InvalidUrlException if the file url was not valid
     */
    void loadInformation() throws IOException, InvalidUrlException;

    /**
     * Force the reload of the publications information on the next read
     *
     */
    void forceReload();

    /**
     * Return the information for each present publication
     *
     * @return a map with publication id as key and information as value
     * @throws IOException if an error occurred while getting the file
     * @throws InvalidUrlException if the url was not valid
     */
    Map<String, Information> getPublicationInformation();
}
