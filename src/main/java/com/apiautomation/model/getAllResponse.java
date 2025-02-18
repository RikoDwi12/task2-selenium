package com.apiautomation.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class getAllResponse {
    @JsonProperty("id")
    public String id;

    @JsonProperty("name")
    public String name;

    @JsonProperty("data")
    public Map<String, Object> data;
}
