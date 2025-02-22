package com.apiautomation.constants;

public class Constants {

    public static final String BASE_URL = "https://api.restful-api.dev";

    String env, BASE_URL1;

    public void setBaseUrl() {
        if (env == "production") {
            BASE_URL1 = "https://api.restful-api.dev";
        } else {
            BASE_URL1 = "https://api.restful-api-staging.dev";
        }
    }

}
