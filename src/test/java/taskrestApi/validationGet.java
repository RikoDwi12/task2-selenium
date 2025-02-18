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
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class validationGet {
        @Test
        public void GetProductById() {
                getResponseById getResponseById;
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
                                        Assert.assertNotNull(entry.getValue(),
                                                        "Value untuk key " + entry.getKey() + " tidak boleh null!");
                                }
                        }
                }
        }

        // TEST GET BY QUERY PARAMs
        @Test
        public void getQueryParam() {
                RestAssured.baseURI = "https://api.restful-api.dev";

                RequestSpecification requestSpecification = RestAssured.given();

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

                GetResponseQuery[] getResponseQuery = responseGetQueryParam.as(GetResponseQuery[].class);

                Assert.assertNotNull(getResponseQuery);
                Assert.assertTrue(getResponseQuery.length > 0);

                Assert.assertEquals("Apple iPhone 12 Pro Max", getResponseQuery[0].getName());

                Assert.assertTrue(getResponseQuery[1].getDataItem().containsKey("price"));
                Assert.assertEquals(689.99, getResponseQuery[1].getDataItem().get("price"));

                Assert.assertTrue(getResponseQuery[2].getDataItem().containsKey("Capacity"));
                Assert.assertEquals("64 GB", getResponseQuery[2].getDataItem().get("Capacity"));

                Assert.assertEquals("3", getResponseQuery[0].getId());
                Assert.assertEquals("5", getResponseQuery[1].getId());
                Assert.assertEquals("10", getResponseQuery[2].getId());
        }

}
