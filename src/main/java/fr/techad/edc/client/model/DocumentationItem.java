/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.model;

import java.util.List;

/**
 * This class define a documentation component.
 */
public interface DocumentationItem extends ObjectId {
    /**
     * Return the label
     *
     * @return the label
     */
    String getLabel();

    /**
     * Define the label
     *
     * @param label the label to set
     */
    void setLabel(String label);

    /**
     * Return the url defined the configuration file (read from a documentation definition).
     * It's the real path on the documentation server.
     *
     * @return the url
     */
    String getUrl();

    /**
     * Define the url.
     * It's the real path on the documentation server.
     *
     * @param url the url to set
     */
    void setUrl(String url);

    /**
     * Return the language code of this documentation.
     * <p>
     * The code is defined by 2 digits in lowercase.
     *
     * @return the language code
     */
    String getLanguageCode();

    /**
     * Define the language code of this documentation.
     * <p>
     * The code is defined by 2 digits in lowercase.
     *
     * @param languageCode the language code to set
     */
    void setLanguageCode(String languageCode);

    /**
     * Return the documentation type.
     *
     * @return the documentation type
     */
    DocumentationItemType getDocumentationItemType();

    /**
     * Define the documentation type
     *
     * @param documentationItemType the documentation type to set
     */
    void setDocumentationItemType(DocumentationItemType documentationItemType);

    /**
     * Add a documentation item as article.
     * <p>
     * Only {@link DocumentationItemType#ARTICLE} is accepted.
     *
     * @param article the article to add
     */
    void addArticle(DocumentationItem article);

    /**
     * Return the list of articles.
     * <p>
     * This list is unmodifiable.
     *
     * @return the list of articles
     */
    List<DocumentationItem> getArticles();

    /**
     * Return the number of articles contained by this DocumentationItem
     *
     * @return the number of articles
     */
    int articleSize();

    /**
     * Add a documentation item as link.
     * <p>
     * All is accepted excepted {@link DocumentationItemType}.
     *
     * @param link the link to add
     */
    void addLink(DocumentationItem link);

    /**
     * Return the list of links.
     * <p>
     * This list is unmodifiable.
     *
     * @return the list of links
     */
    List<DocumentationItem> getLinks();

    /**
     * Return the number of links contained by this DocumentationItem
     *
     * @return the number of links
     */
    int linkSize();
}
