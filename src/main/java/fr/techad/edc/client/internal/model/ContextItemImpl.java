/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.internal.model;

import fr.techad.edc.client.model.ContextItem;
import fr.techad.edc.client.model.DocumentationItemType;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * TECH ADVANTAGE
 * All right reserved
 * Created by cochon on 19/06/2017.
 */
public class ContextItemImpl extends DocumentationItemImpl implements ContextItem {
    private String description;
    private String mainKey;
    private String subKey;

    public ContextItemImpl() {
        super(DocumentationItemType.CONTEXTUAL);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getMainKey() {
        return mainKey;
    }

    @Override
    public void setMainKey(String mainKey) {
        this.mainKey = mainKey;
    }

    @Override
    public String getSubKey() {
        return subKey;
    }

    @Override
    public void setSubKey(String subKey) {
        this.subKey = subKey;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("description", description)
                .append("mainKey", mainKey)
                .append("subKey", subKey)
                .toString();
    }
}
