package taskrestApi;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class task {

    public static void main(String[] args) {
        // tinggal un comment yang mau di jalankan

        // getAllObject();
        // getById();
        // addProduct();
        // updateProduct();
        // deleteProduct();
    }

    // Get All Object
    public static void getAllObject() {

        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                .given();

        // Response response = requestSpecification.log().all().get("objects");

        Response response2 = requestSpecification
                .log()
                .all()
                .when()
                .get("objects");

        System.out.println("Hasilnya adalah " + response2.asPrettyString());
        // System.out.println("Hasilnya adalah " + response.asPrettyString());
    }

    // get by id
    public static void getById() {

        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                .given();

        Response response = requestSpecification
                .log()
                .all()
                .pathParam("idProduct", 6)
                .pathParam("path", "objects")
                .when()
                .get("{path}/{idProduct}");

        System.out.println("hasil by id here" + response.asPrettyString());
    }

    // add product
    public static void addProduct() {

        String body = "{"
                + "\"name\": \"TestingManual\","
                + "\"data\": {"
                + "\"year\": 9999,"
                + "\"price\": 99999,"
                + "\"CPU model\": \"CPU testintg\","
                + "\"Hard disk size\": \"Hardisk testing\""
                + "}"
                + "}";

        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                .given();

        Response response = requestSpecification
                .log()
                .all()
                .pathParam("path", "objects")
                .body(body)
                .contentType("application/json")
                .when()
                .post("{path}");

        // Menampilkan response dan status code
        System.out.println("Response add : " + response.asPrettyString());
        System.out.println("Status Code: " + response.getStatusCode());
    }

    // update product
    public static void updateProduct() {
        String body = "{"
                + "\"name\": \"TestingManualPut\","
                + "\"data\": {"
                + "\"year\": 9999,"
                + "\"price\": 99999,"
                + "\"CPU model\": \"CPU testintg\","
                + "\"Hard disk size\": \"Hardisk testing\""
                + "}"
                + "}";

        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                .given();

        Response response = requestSpecification
                .log()
                .all()
                .pathParam("path", "objects")
                .pathParam("idProduct", "ff808181932badb60195035f777a2514")
                .body(body)
                .contentType("application/json")
                .when()
                .put("{path}/{idProduct}");

        System.out.println("update product" + response.asPrettyString());
        System.out.println("Status Code " + response.getStatusCode());
    }

    public static void deleteProduct() {
        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                .given();

        Response response = requestSpecification
                .log()
                .all()
                .pathParam("path", "objects")
                .pathParam("idProduct", "ff808181932badb60195035f777a2514")
                .contentType("application/json")
                .when()
                .delete("{path}/{idProduct}");

        System.out.println("delete product" + response.asPrettyString());
        System.out.println("Status Code " + response.getStatusCode());
    }

}
