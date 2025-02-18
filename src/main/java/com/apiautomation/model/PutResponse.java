package com.apiautomation.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PutResponse {
    // {
    // "id": "7",
    // "name": "Apple MacBook Pro 16",
    // "data": {
    // "year": 2019,
    // "price": 2049.99,
    // "CPU model": "Intel Core i9",
    // "Hard disk size": "1 TB",
    // "color": "silver"
    // },
    // "updatedAt": "2022-12-25T21:08:41.986Z"
    // }
    @JsonProperty("name")
    public String name;

    @JsonProperty("updatedAt")
    public String updatedAt;

    @JsonProperty("id")
    public String id;

    @JsonProperty("data")
    public DataItem dataItem;

    public static class DataItem {
        @JsonProperty("year")
        public int year;

        @JsonProperty("price")
        public int price;

        @JsonProperty("CPU model")
        public String cpuModel;

        @JsonProperty("Hard disk size")
        public String hardDiskSize;

        @JsonProperty("color")
        public String color;
    }
}
