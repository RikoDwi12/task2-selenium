package com.apiautomation.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class getResponseById {

    // {
    // "id": "7",
    // "name": "Apple MacBook Pro 16",
    // "data": {
    // "year": 2019,
    // "price": 1849.99,
    // "CPU model": "Intel Core i9",
    // "Hard disk size": "1 TB"
    // }
    // }

    @JsonProperty("id")
    public String id;

    @JsonProperty("name")
    public String name;

    @JsonProperty("data")
    public DataItem dataItem;

    public static class DataItem {
        @JsonProperty("year")
        public int year;

        @JsonProperty("price")
        public Double price; // Ubah ke Double

        @JsonProperty("CPU model")
        public String cpuModel;

        @JsonProperty("Hard disk size")
        public String hardDiskSize;
    }
}
