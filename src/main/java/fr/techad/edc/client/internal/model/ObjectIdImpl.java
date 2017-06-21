/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.internal.model;

import fr.techad.edc.client.model.ObjectId;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * TECH ADVANTAGE
 * All right reserved
 * Created by cochon on 19/06/2017.
 */
public class ObjectIdImpl implements ObjectId {

    private Long id;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .toString();
    }
}
