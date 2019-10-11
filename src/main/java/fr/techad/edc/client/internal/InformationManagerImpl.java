package fr.techad.edc.client.internal;

import com.google.common.collect.Maps;
import fr.techad.edc.client.InformationManager;
import fr.techad.edc.client.io.EdcReader;
import fr.techad.edc.client.model.Information;
import fr.techad.edc.client.model.InvalidUrlException;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Map;

public class InformationManagerImpl implements InformationManager {
    private EdcReader reader;

    private Map<String, Information> information = Maps.newHashMap();

    @Inject
    public InformationManagerImpl(EdcReader reader) {
        this.reader = reader;
    }

    @Override
    public Map<String, Information> getPublicationInformation() throws IOException, InvalidUrlException {
        if (this.information == null || this.information.isEmpty()) {
            this.information = this.reader.readInfo();
        }
        return this.information;
    }

    @Override
    public Information findByPublicationId(String publicationId) throws IOException, InvalidUrlException {
        if (StringUtils.isEmpty(publicationId)) {
            return null;
        }
        return this.getPublicationInformation().entrySet().stream()
                .filter(e -> publicationId.equals(e.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }
}
