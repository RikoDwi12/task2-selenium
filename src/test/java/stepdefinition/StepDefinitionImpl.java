package stepdefinition;

import java.util.Map;

import org.testng.Assert;

import com.apiautomation.model.PostResponse;
import com.apiautomation.model.getResponseById;
import com.apiautomation.model.request.RequestItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

@Given("A list of products are available")
    public void getAllProducts(){
        //Implementation
        System.out.println("getAllProducts");
        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

        Response responseGet = requestSpecification
                        .log()
                        .all()
                        .when()
                        .get("objects");

        System.out.println("reponse" + responseGet.asPrettyString());
        System.out.println("status code" + responseGet.getStatusCode());
    }
    
@When("I add new products to etalase")
    public void addNewProduct(){
         //Implementation
         System.out.println("Add new product");
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
        System.out.println("Response add" + response.asPrettyString());
        System.out.println("Status Code: " + response.getStatusCode());


        //Validation
        JsonPath jsonPath = response.jsonPath();
        postResponse = jsonPath.getObject("", PostResponse.class);
        
        Assert.assertEquals(postResponse.name, "Apple MacBook Pro 16 Untuk Testing Post");
        Assert.assertNotNull(postResponse.createdAt);
        Assert.assertNotNull(postResponse.id);
        Assert.assertEquals(postResponse.dataItem.year, 2099);
        Assert.assertEquals(postResponse.dataItem.price, 40000);
        Assert.assertEquals(postResponse.dataItem.cpuModel, "Intel Core i9 Untuk Testing Post");
        Assert.assertEquals(postResponse.dataItem.hardDiskSize, "1 TB Post");

        /*
         * Simulate kalau idproduct nya kita dapat dari responseItem.id,
         * Tapi karena id nya akan selalu sama bakanya kita modify manual
         *  idProduct = responseItem.id;
         */
        idProduct = 1;

    }

@When("I add new {string} to etalase")
    public void addNewProducts(String payload) throws JsonMappingException, JsonProcessingException{
         //Implementation
        dataRequest = new DataRequest();

        // System.out.println("Add new product-1" + payload);
        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();
        
        for(Map.Entry<String, String> entry : dataRequest.addItemCollection().entrySet()){
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

        //Object mapper
        /*
         * Convert JSON to POJO
         */
        ObjectMapper requestAddItem = new ObjectMapper();
        // melakukan breadown json ke dalam object
        requestItem = requestAddItem.readValue(json, RequestItem.class);

        //Validation
        JsonPath jsonPath = response.jsonPath();
        postResponse = jsonPath.getObject("", PostResponse.class);

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(postResponse.name, requestItem.name);
        Assert.assertEquals(postResponse.dataItem.price,requestItem.dataItem.price);
        Assert.assertEquals(postResponse.dataItem.year,requestItem.dataItem.year);
        Assert.assertEquals(postResponse.dataItem.cpuModel,requestItem.dataItem.cpuModel);
        Assert.assertEquals(postResponse.dataItem.hardDiskSize,requestItem.dataItem.hardDiskSize);
    }
     @Then("The product is available")
     
    public void getSingleProduct(){
         //Implementation
         System.out.println("get single product");
          /*
         * 'https://dummyjson.com/products/1'
         */

         RestAssured.baseURI = "https://api.restful-api.dev";
         RequestSpecification requestSpecification = RestAssured
                                                     .given();

                                                     Response response = requestSpecification
                                                     .log()
                                                     .all()
                                                     .pathParam("idProduct", 7)
                                                     .when()
                                                     .get("/objects/{idProduct}");
                     
                                     System.out.println("Response by ID: " + response.asPrettyString());
                                     System.out.println("Response Status Code: " + response.getStatusCode());
        //Validation
        Assert.assertNotNull(response, "Response is null!");

                JsonPath jsonPath = response.jsonPath();
                getResponseById = jsonPath.getObject("", getResponseById.class);
                // Validasi ID dan Nama
                Assert.assertNotNull(getResponseById, "getResponseById is null!");
                Assert.assertEquals(getResponseById.id, "7", "ID tidak sesuai!");
                Assert.assertEquals(getResponseById.name, "Apple MacBook Pro 16", "Nama produk tidak sesuai!");

                // Validasi Data Item
                Assert.assertNotNull(getResponseById.dataItem, "Data item is null!");
                Assert.assertEquals(getResponseById.dataItem.year, 2019, "Tahun tidak sesuai!");
                Assert.assertEquals(getResponseById.dataItem.price, 1849.99, "Harga tidak sesuai!");
                Assert.assertEquals(getResponseById.dataItem.cpuModel, "Intel Core i9", "Model CPU tidak sesuai!");
                Assert.assertEquals(getResponseById.dataItem.hardDiskSize, "1 TB", "Ukuran hard disk tidak sesuai!");

                // Validasi status code (optional)
                Assert.assertEquals(response.getStatusCode(), 200, "Status Code tidak sesuai!");

    }
}
