package apiengine;

import com.apiautomation.constants.Constants;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Endpoints {
    RequestSpecification requestSpecification;
    Constants constants;

    public Endpoints() {
        RestAssured.baseURI = Constants.BASE_URL;
        requestSpecification = RestAssured
                .given()
                .log()
                .all();
    }

    public Response getAllProducts(String path) {

        Response responseGet = requestSpecification
                .when()
                .get(path);

        return responseGet;
    }

    public Response addNewProduct(String path, String json) {

        Response responsePost = requestSpecification
                .pathParam("path", "objects")
                .body(json)
                .contentType("application/json")
                .when()
                .post("{path}");

        return responsePost;
    }

    public Response addNewProudctMap(String path, String json) {
        Response response = requestSpecification
                .pathParam("path", "objects")
                .body(json)
                .contentType("application/json")
                .when()
                .post("{path}");

        return response;
    }

    public Response GetById(String path, int idProduct) {

        Response responseGetById = requestSpecification
                .pathParam("path", path)
                .pathParam("idProduct", idProduct)
                .when()
                .get("/{path}/{idProduct}");

        return responseGetById;
    }
}
