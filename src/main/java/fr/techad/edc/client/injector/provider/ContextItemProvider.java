/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.injector.provider;

import fr.techad.edc.client.internal.model.ContextItemImpl;
import fr.techad.edc.client.model.ContextItem;

import javax.inject.Provider;

/**
 * TECH ADVANTAGE
 * All right reserved
 * Created by cochon on 20/06/2017.
 */
public class ContextItemProvider implements Provider<ContextItem> {
    @Override
    public ContextItem get() {
        return new ContextItemImpl();
    }
}
