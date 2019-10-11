package fr.techad.edc.client;

import fr.techad.edc.client.model.Information;
import fr.techad.edc.client.model.InvalidUrlException;

import java.io.IOException;
import java.util.Map;

public interface InformationManager {

    /**
     * Return the information for each present publication
     *
     * @return a map with publication id as key and information as value
     * @throws IOException if an error occurred while getting the file
     * @throws InvalidUrlException if the url was not valid
     */
    Map<String, Information> getPublicationInformation() throws IOException, InvalidUrlException;

    /**
     * Return the information corresponding to the publication id
     *
     * @param publicationId the identifier of the publication
     * @return the information of the publication
     * @throws IOException if an error occurred while getting the file
     * @throws InvalidUrlException if the url was not valid
     */
    Information findByPublicationId(String publicationId) throws IOException, InvalidUrlException;
}
