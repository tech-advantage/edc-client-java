package fr.techad.edc.client.injector.provider;

import fr.techad.edc.client.internal.model.InformationImpl;
import fr.techad.edc.client.model.Information;

import javax.inject.Provider;

public class InformationProvider implements Provider<Information> {
    @Override
    public Information get() {
        return new InformationImpl();
    }
}
