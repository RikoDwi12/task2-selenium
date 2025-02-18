package scenario;

import com.apiautomation.model.PostResponse;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RestE2E {

    /*
     * Scenario e2e test
     * 1. Create New Object Hit Api (Add_Object)
     * 2. Verify New Object Hit Api (Singel_Object)
     * 3. Delete Object Hit Api (Delete_Object)
     * 4. Verify new object is deleted (Singel_Object)
     */

    @Test
    public void scenarioE2ETest() {
        // 1. Create New Object (POST)
        String json = "{\n" +
                "   \"name\": \"Apple MacBook Pro 16 Untuk Testing Post\",\n" +
                "   \"data\": {\n" +
                "      \"year\": 2099,\n" +
                "      \"price\": 40000,\n" +
                "      \"CPU model\": \"Intel Core i9 Untuk Testing Post\",\n" +
                "      \"Hard disk size\": \"1 TB Post\"\n" +
                "   }\n" +
                "}";

        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured.given();

        // POST Request to create the object
        Response response = requestSpecification
                .log()
                .all()
                .pathParam("path", "objects")
                .body(json)
                .contentType("application/json")
                .when()
                .post("{path}");

        System.out.println("Response from POST API: " + response.asPrettyString());
        System.out.println("Status Code: " + response.getStatusCode());

        PostResponse postResponse;
        JsonPath jsonPath = response.jsonPath();
        postResponse = jsonPath.getObject("", PostResponse.class);

        // Assertions for POST response
        Assert.assertEquals(postResponse.name, "Apple MacBook Pro 16 Untuk Testing Post");
        Assert.assertNotNull(postResponse.createdAt);
        Assert.assertNotNull(postResponse.id);
        Assert.assertEquals(postResponse.dataItem.year, 2099);
        Assert.assertEquals(postResponse.dataItem.price, 40000);
        Assert.assertEquals(postResponse.dataItem.cpuModel, "Intel Core i9 Untuk Testing Post");
        Assert.assertEquals(postResponse.dataItem.hardDiskSize, "1 TB Post");

        String idObject = postResponse.id;

        // 2. Get the created object by ID (GET)
        Response response2 = RestAssured.given()
                .log()
                .all()
                .pathParam("idProduct", idObject) // Use the ID from the POST response
                .pathParam("path", "objects")
                .when()
                .get("{path}/{idProduct}");

        // Print response from GET request for debugging
        System.out.println("Response from GET API by ID: " + response2.asPrettyString());
        System.out.println("Status Code: " + response2.getStatusCode());

        // Assertions for GET response
        Assert.assertEquals(response2.getStatusCode(), 200, "Status code should be 200");

        PostResponse getObjectResponse = response2.jsonPath().getObject("", PostResponse.class);

        // Validate GET response matches POST response data
        Assert.assertEquals(getObjectResponse.id, postResponse.id, "ID should match");
        Assert.assertEquals(getObjectResponse.name, postResponse.name, "Name should match");
        Assert.assertEquals(getObjectResponse.dataItem.year, postResponse.dataItem.year, "Year should match");
        Assert.assertEquals(getObjectResponse.dataItem.price, postResponse.dataItem.price, "Price should match");
        Assert.assertEquals(getObjectResponse.dataItem.cpuModel, postResponse.dataItem.cpuModel,
                "CPU model should match");
        Assert.assertEquals(getObjectResponse.dataItem.hardDiskSize, postResponse.dataItem.hardDiskSize,
                "Hard disk size should match");

        // 3. Delete Object (DELETE)
        Response responseDelete = RestAssured.given()
                .log()
                .all()
                .pathParam("idProduct", idObject) // Use the ID for DELETE
                .pathParam("path", "objects")
                .when()
                .delete("{path}/{idProduct}");

        System.out.println("Response from DELETE API: " + responseDelete.asPrettyString());
        System.out.println("Status Code: " + responseDelete.getStatusCode());

        // Assertions for DELETE response (Check that the DELETE was successful)
        Assert.assertEquals(responseDelete.getStatusCode(), 200, "Status code should be 200 for DELETE");

        // 4. Verify new object is deleted (GET)
        Response responseAfterDelete = RestAssured.given()
                .log()
                .all()
                .pathParam("idProduct", idObject) // Use the ID to check if it's deleted
                .pathParam("path", "objects")
                .when()
                .get("{path}/{idProduct}");

        System.out.println("Response after DELETE (should be 404): " + responseAfterDelete.asPrettyString());
        System.out.println("Status Code: " + responseAfterDelete.getStatusCode());

        // Assert that the object is no longer available after DELETE (Expecting 404)
        Assert.assertEquals(responseAfterDelete.getStatusCode(), 404,
                "Status code should be 404 after deletion (not found)");
    }
}
