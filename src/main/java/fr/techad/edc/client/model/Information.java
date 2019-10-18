/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.model;

import java.util.Set;

/**
 * This component define the information about the documentation (version, label, ...)
 */
public interface Information {

    String getDefaultLanguage();

    void setDefaultLanguage(String defaultLanguage);

    Set<String> getLanguages();

    void setLanguages(Set<String> languages);
}
