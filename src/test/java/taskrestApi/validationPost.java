package taskrestApi;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.apiautomation.model.PostResponse;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class validationPost {
    @Test
    public void createObject() {

        PostResponse postResponse;

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

        System.out.println("Response API" + response.asPrettyString());
        System.out.println("Status Code: " + response.getStatusCode());

        JsonPath jsonPath = response.jsonPath();
        postResponse = jsonPath.getObject("", PostResponse.class);

        Assert.assertEquals(postResponse.name, "Apple MacBook Pro 16 Untuk Testing Post");
        Assert.assertNotNull(postResponse.createdAt);
        Assert.assertNotNull(postResponse.id);
        Assert.assertEquals(postResponse.dataItem.year, 2099);
        Assert.assertEquals(postResponse.dataItem.price, 40000);
        Assert.assertEquals(postResponse.dataItem.cpuModel, "Intel Core i9 Untuk Testing Post");
        Assert.assertEquals(postResponse.dataItem.hardDiskSize, "1 TB Post");
    }
}
