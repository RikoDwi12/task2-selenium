package com.apiautomation.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteResponse {
    // {
    // "message": "Object with id = 6, has been deleted."
    // }
    @JsonProperty("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
