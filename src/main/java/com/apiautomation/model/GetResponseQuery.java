package com.apiautomation.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetResponseQuery {
    @JsonProperty("id")
    public String id;

    @JsonProperty("name")
    public String name;

    @JsonProperty("data")
    public Map<String, Object> dataItem;

    // Getter dan Setter jika diperlukan
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
