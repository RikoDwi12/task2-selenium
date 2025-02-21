package apiengine;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Endpoints {
    RequestSpecification requestSpecification;

    public Response GetAllObject(String path) { 
    RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

        Response responseGet = requestSpecification
                        .log()
                        .all()
                        .when()
                        .get(path);

        return responseGet;
    }
}
