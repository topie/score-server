package com.orange.score.common.tools.plugins;

import com.orange.score.common.utils.Option;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class FormItem implements Serializable {

    private static final long serialVersionUID = 2709882962735133794L;

    private String type;

    private String name;

    private String id;

    private String label;

    private String cls = "col-lg-12";

    private Boolean code = false;

    private List<Option> items;

    private Integer span = 1;

    private Map config;

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getCode() {
        return code;
    }

    public void setCode(Boolean code) {
        this.code = code;
    }

    public Map getConfig() {
        return config;
    }

    public void setConfig(Map config) {
        this.config = config;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }

    public List<Option> getItems() {
        return items;
    }

    public void setItems(List<Option> items) {
        this.items = items;
    }

    public Integer getSpan() {
        return span;
    }

    public void setSpan(Integer span) {
        this.span = span;
    }
}
