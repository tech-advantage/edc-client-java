/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.injector.provider;

import fr.techad.edc.client.internal.model.DocumentationItemImpl;
import fr.techad.edc.client.model.DocumentationItem;

import javax.inject.Provider;

/**
 * TECH ADVANTAGE
 * All right reserved
 * Created by cochon on 20/06/2017.
 */
public class DocumentationItemProvider implements Provider<DocumentationItem> {
    @Override
    public DocumentationItem get() {
        return new DocumentationItemImpl();
    }
}
