package taskrestApi;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.apiautomation.model.PutResponse;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class validationPut {
    @Test
    public void updateProduct() {

        PutResponse putResponse;

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

        RequestSpecification requestSpecification = RestAssured.given();

        Response response = requestSpecification
                .log()
                .all()
                .pathParam("path", "objects")
                .pathParam("idProduct", "ff808181932badb60195183ce8174e28")
                .body(body)
                .contentType("application/json")
                .when()
                .put("{path}/{idProduct}");

        System.out.println("Update product response: " + response.asPrettyString());
        System.out.println("Status Code: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), 200);

        JsonPath jsonPath = response.jsonPath();
        putResponse = jsonPath.getObject("", PutResponse.class);

        // Melakukan assertion untuk validasi
        Assert.assertEquals(putResponse.name, "TestingManualPut"); // Pastikan nama yang diterima sesuai
        Assert.assertNotNull(putResponse.updatedAt);
        Assert.assertNotNull(putResponse.id);
        Assert.assertEquals(putResponse.dataItem.year, 9999);
        Assert.assertEquals(putResponse.dataItem.price, 99999);
        Assert.assertEquals(putResponse.dataItem.cpuModel, "CPU testintg");
        Assert.assertEquals(putResponse.dataItem.hardDiskSize, "Hardisk testing");
        Assert.assertNull(putResponse.dataItem.color);
    }

}
