/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.internal.model;

import com.google.common.collect.Lists;
import fr.techad.edc.client.model.DocumentationItem;
import fr.techad.edc.client.model.DocumentationItemType;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collections;
import java.util.List;

/**
 * TECH ADVANTAGE
 * All right reserved
 * Created by cochon on 19/06/2017.
 */
public class DocumentationItemImpl extends ObjectIdImpl implements DocumentationItem {

    private String label;
    private String publicationId;
    private String url;
    private String languageCode;
    private DocumentationItemType documentationItemType;
    private List<DocumentationItem> articles = Lists.newArrayList();
    private List<DocumentationItem> links = Lists.newArrayList();

    public DocumentationItemImpl() {
    }

    public DocumentationItemImpl(DocumentationItemType documentationItemType) {
        this.documentationItemType = documentationItemType;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getPublicationId() {
        return publicationId;
    }

    @Override
    public void setPublicationId(String publicationId) {
        this.publicationId = publicationId;
    }

    @Override
    public String getLanguageCode() {
        return languageCode;
    }

    @Override
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    @Override
    public DocumentationItemType getDocumentationItemType() {
        return documentationItemType;
    }

    @Override
    public void setDocumentationItemType(DocumentationItemType documentationItemType) {
        this.documentationItemType = documentationItemType;
    }

    @Override
    public void addArticle(DocumentationItem article) {
        if (article.getDocumentationItemType() == DocumentationItemType.ARTICLE) {
            articles.add(article);
        }
    }

    @Override
    public List<DocumentationItem> getArticles() {
        return Collections.unmodifiableList(articles);
    }

    @Override
    public int articleSize() {
        return articles.size();
    }

    @Override
    public void addLink(DocumentationItem link) {
        if (link.getDocumentationItemType() != DocumentationItemType.ARTICLE) {
            links.add(link);
        }
    }

    @Override
    public List<DocumentationItem> getLinks() {
        return Collections.unmodifiableList(links);
    }

    @Override
    public int linkSize() {
        return links.size();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("label", label)
                .append("url", url)
                .append("languageCode", languageCode)
                .append("documentationItemType", documentationItemType)
                .append("articles", articles)
                .append("links", links)
                .toString();
    }
}
