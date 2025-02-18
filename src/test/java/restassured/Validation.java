package restassured;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.apiautomation.model.getResponseById;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Validation {

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

        // Cetak response untuk debugging
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
        Assert.assertEquals(responseObject.dataItem.price, 1849.99, 0.01); // Toleransi untuk double
        Assert.assertEquals(responseObject.dataItem.cpuModel, "Intel Core i9");
        Assert.assertEquals(responseObject.dataItem.hardDiskSize, "1 TB");
    }
}
