package com.orange.score.database.score.model;

import javax.persistence.*;

@Table(name = "T_DOCUMENT")
public class Document {

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select T_DOCUMENT_SEQ.nextval from dual")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "URL")
    private String url;

    /**
     * @return ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return NAME
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
