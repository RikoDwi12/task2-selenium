package taskrestApi;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.apiautomation.model.GetResponseQuery;
import com.apiautomation.model.getAllResponse;
import com.apiautomation.model.getResponseById;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class validationGet {
    @Test
    public void GetProductById() {
        RestAssured.baseURI = "https://api.restful-api.dev";

        RequestSpecification requestSpecification = RestAssured.given();

        Response response = requestSpecification
                .log()
                .all()
                .pathParam("idProduct", 7)
                .when()
                .get("/objects/{idProduct}");

        System.out.println("Response by ID: " + response.asPrettyString());
        System.out.println("Response Status Code: " + response.getStatusCode());

        // Pastikan response tidak null
        Assert.assertNotNull(response, "Response is null!");

        // Parsing JSON ke POJO
        getResponseById responseObject = response.as(getResponseById.class);

        // Pastikan responseObject tidak null sebelum mengakses propertinya
        Assert.assertNotNull(responseObject, "Response object is null!");
        Assert.assertNotNull(responseObject.dataItem, "DataItem is null!");

        // Validasi data dari API response
        Assert.assertEquals(responseObject.name, "Apple MacBook Pro 16");
        Assert.assertEquals(responseObject.dataItem.year, 2019);
        Assert.assertEquals(responseObject.dataItem.price, 1849.99, 0.01);
        Assert.assertEquals(responseObject.dataItem.cpuModel, "Intel Core i9");
        Assert.assertEquals(responseObject.dataItem.hardDiskSize, "1 TB");
    }

    // TEST GET ALL
    @Test
    public void getAllObject() {
        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured.given();

        Response responseGetAll = requestSpecification
                .log()
                .all()
                .when()
                .get("objects");

        System.out.println("Hasilnya adalah " + responseGetAll.asPrettyString());
        System.out.println("Response Status Code: " + responseGetAll.getStatusCode());

        Assert.assertEquals(responseGetAll.getStatusCode(), 200, "Status code tidak sesuai!");

        List<getAllResponse> responseObjects = Arrays.asList(responseGetAll.as(getAllResponse[].class));

        Assert.assertFalse(responseObjects.isEmpty(), "Response list is empty!");

        for (getAllResponse obj : responseObjects) {
            System.out.println("Memeriksa objek dengan ID: " + obj.id);

            // Pastikan id dan name tidak null
            Assert.assertNotNull(obj.id, "ID tidak boleh null!");
            Assert.assertNotNull(obj.name, "Nama tidak boleh null!");

            // Jika `data` tidak null, validasi isi `data`
            if (obj.data != null) {
                for (Map.Entry<String, Object> entry : obj.data.entrySet()) {
                    System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
                    Assert.assertNotNull(entry.getValue(), "Value untuk key " + entry.getKey() + " tidak boleh null!");
                }
            }
        }
    }

    // TEST GET BY QUERY PARAMs
    @Test
    public void getQueryParam() {
        RestAssured.baseURI = "https://api.restful-api.dev";

        RequestSpecification requestSpecification = RestAssured.given();

        // Multiple IDs passed as query parameters
        Response responseGetQueryParam = requestSpecification
                .log()
                .all()
                .queryParam("id", 3)
                .queryParam("id", 5)
                .queryParam("id", 10)
                .when()
                .get("objects");

        System.out.println("Response: " + responseGetQueryParam.asPrettyString());
        System.out.println("Response Status Code: " + responseGetQueryParam.getStatusCode());

        Assert.assertEquals(responseGetQueryParam.getStatusCode(), 200);

        // Parse response to POJO
        GetResponseQuery[] responseObjects = responseGetQueryParam.as(GetResponseQuery[].class);

        // Assert that response is not null
        Assert.assertNotNull(responseObjects, "Response should not be null!");

        // Find the object with ID "3"
        GetResponseQuery objectWithId3 = Arrays.stream(responseObjects)
                .filter(obj -> obj.getId().equals("3"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Object with ID 3 not found"));

        // Assert the object with ID 3 is found and has the correct name
        Assert.assertEquals(objectWithId3.getId(), "3", "First ID should be 3");
        Assert.assertEquals(objectWithId3.getName(), "Apple iPhone 12 Pro Max", "Name mismatch");

        Assert.assertTrue(objectWithId3.getDataItem().containsKey("color"),
                "Color property should be present in object with ID 3");

        // object
        GetResponseQuery objectWithId5 = Arrays.stream(responseObjects)
                .filter(obj -> obj.getId().equals("5"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Object with ID 5 not found"));

        Assert.assertTrue(objectWithId5.getDataItem().containsKey("price"),
                "Price property should be present in object with ID 5");

        GetResponseQuery objectWithId10 = Arrays.stream(responseObjects)
                .filter(obj -> obj.getId().equals("10"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Object with ID 10 not found"));

        Assert.assertTrue(objectWithId10.getDataItem().containsKey("Capacity"),
                "Capacity property should be present in object with ID 10");
    }

}
