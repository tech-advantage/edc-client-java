/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.model;

/**
 * Used it wwhen a internal error is detected on url definition and will generate a malformed url.
 */
public class InvalidUrlException extends Exception {

    /**
     * Constructor
     *
     * @param message the message to set
     */
    public InvalidUrlException(String message) {
        super(message);
    }
}
