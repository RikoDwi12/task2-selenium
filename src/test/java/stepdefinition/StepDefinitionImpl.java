package stepdefinition;

import java.util.Map;

import org.testng.Assert;

import com.apiautomation.model.PostResponse;
import com.apiautomation.model.getResponseById;
import com.apiautomation.model.request.RequestItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import apiengine.Assertion;
import apiengine.Endpoints;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import resources.DataRequest;

public class StepDefinitionImpl {

    PostResponse postResponse;
    RequestItem requestItem;
    DataRequest dataRequest;
    String json;
    int idProduct;
    getResponseById getResponseById;
    Endpoints endpoints;
    Response response;
    Assertion assertion;

    @BeforeStep
    public void setup() {
        endpoints = new Endpoints();
        assertion = new Assertion();
    }

    @Given("A list of products are available")
    public void getAllProducts() {
        // Implementation Refactor code to use Endpoints class
        endpoints = new Endpoints();
        response = endpoints.getAllProducts("objects");
        // mendapatkan response dari endpoint
        System.out.println("Response migration " + response.asPrettyString());
    }

    @When("I add new products to etalase")
    public void addNewProduct() {
        // Implementation
        String json = "{\n" + //
                "   \"name\": \"Apple MacBook Pro 16 Untuk Testing Post\",\n" + //
                "   \"data\": {\n" + //
                "      \"year\": 2099,\n" + //
                "      \"price\": 40000,\n" + //
                "      \"CPU model\": \"Intel Core i9 Untuk Testing Post\",\n" + //
                "      \"Hard disk size\": \"1 TB Post\"\n" + //
                "   }\n" + //
                "}";

        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                .given();

        Response response = requestSpecification
                .log()
                .all()
                .pathParam("path", "objects")
                .body(json)
                .contentType("application/json")
                .when()
                .post("{path}");

        endpoints = new Endpoints();
        response = endpoints.addNewProduct("objects", json);

        // Validation
        JsonPath jsonPath = response.jsonPath();
        postResponse = jsonPath.getObject("", PostResponse.class);

        // assertion clean code
        assertion.assertAddNewProduct(postResponse);
        idProduct = 7;

    }

    @When("I add new {string} to etalase")
    public void addNewProducts(String payload) throws JsonMappingException, JsonProcessingException {
        // Implementation
        dataRequest = new DataRequest();

        // System.out.println("Add new product-1" + payload);
        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                .given();

        for (Map.Entry<String, String> entry : dataRequest.addItemCollection().entrySet()) {
            if (entry.getKey().equals(payload)) {
                json = entry.getValue();
                break;
            }
        }

        Response response = requestSpecification
                .log()
                .all()
                .pathParam("path", "objects")
                .body(json)
                .contentType("application/json")
                .when()
                .post("{path}");

        System.out.println("add product New Map" + response.asPrettyString());
        System.out.println("Status Code: " + response.getStatusCode());

        ObjectMapper requestAddItem = new ObjectMapper();
        // melakukan breadown json ke dalam object
        requestItem = requestAddItem.readValue(json, RequestItem.class);

        // Validation
        JsonPath jsonPath = response.jsonPath();
        postResponse = jsonPath.getObject("", PostResponse.class);

        Assert.assertEquals(response.statusCode(), 200);
        // assertion clean code
        assertion.assertAddProduct(postResponse, requestItem);
    }

    @Then("The product is available")

    public void getSingleProduct() {
        // Implementation
        endpoints = new Endpoints();
        response = endpoints.GetById("objects", idProduct = 7);

        System.out.println("Response by ID: " + response.asPrettyString());
        System.out.println("Response Status Code: " + response.getStatusCode());

        // // Validation
        // Assert.assertNotNull(response, "Response is null!");

        // JsonPath jsonPath = response.jsonPath();
        // getResponseById = jsonPath.getObject("", getResponseById.class);
        // // Validasi ID dan Nama
        // Assert.assertNotNull(getResponseById, "getResponseById is null!");
        // Assert.assertEquals(getResponseById.id, "7", "ID tidak sesuai!");
        // Assert.assertEquals(getResponseById.name, "Apple MacBook Pro 16", "Nama
        // produk tidak sesuai!");

        // // Validasi Data Item
        // Assert.assertNotNull(getResponseById.dataItem, "Data item is null!");
        // Assert.assertEquals(getResponseById.dataItem.year, 2019, "Tahun tidak
        // sesuai!");
        // Assert.assertEquals(getResponseById.dataItem.price, 1849.99, "Harga tidak
        // sesuai!");
        // Assert.assertEquals(getResponseById.dataItem.cpuModel, "Intel Core i9",
        // "Model CPU tidak sesuai!");
        // Assert.assertEquals(getResponseById.dataItem.hardDiskSize, "1 TB", "Ukuran
        // hard disk tidak sesuai!");

        // Validasi status code (optional)
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code tidak sesuai!");

        // assertion clean code
        assertion.assertGetSingelProduct(getResponseById);

    }
}
