package com.apiautomation.model;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetResponseQuery {
    @JsonProperty("id")
    private String id; // ID is returned as a String in the response

    @JsonProperty("name")
    private String name;

    @JsonProperty("data")
    private Map<String, Object> dataItem; // A map to handle different keys and values in "data"

    // Constructor
    public GetResponseQuery() {
    }

    public GetResponseQuery(String id, String name, Map<String, Object> dataItem) {
        this.id = id;
        this.name = name;
        this.dataItem = dataItem;
    }

    // Getter and Setter methods
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getDataItem() {
        return dataItem;
    }

    public void setDataItem(Map<String, Object> dataItem) {
        this.dataItem = dataItem;
    }
}
