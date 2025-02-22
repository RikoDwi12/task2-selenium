package taskrestApi;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.apiautomation.model.DeleteResponse;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class validationDelete {
        @Test
        public void deleteProduct() {
                // POJO untuk response
                DeleteResponse deleteResponse;

                // Set base URI untuk RestAssured
                RestAssured.baseURI = "https://api.restful-api.dev";
                RequestSpecification requestSpecification = RestAssured.given();

                // Mengirim permintaan DELETE
                Response response = requestSpecification
                                .log()
                                .all()
                                .pathParam("path", "objects")
                                .pathParam("idProduct", "ff808181932badb60195186cb5924f17")
                                .contentType("application/json")
                                .when()
                                .delete("{path}/{idProduct}");

                // Menampilkan response untuk debugging
                System.out.println("Delete product response: " + response.asPrettyString());
                System.out.println("Status Code: " + response.getStatusCode());

                // Memastikan status code yang diterima adalah 200
                Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");

                // Mem-parsing response JSON ke POJO DeleteResponse
                deleteResponse = response.as(DeleteResponse.class);

                // Melakukan assertion untuk memverifikasi pesan dari respons
                Assert.assertNotNull(deleteResponse.getMessage(), "Message should not be null");

                // Verifikasi bahwa pesan berisi informasi penghapusan objek dengan ID
                Assert.assertTrue(deleteResponse.getMessage().contains("has been deleted"),
                                "Response message should indicate successful deletion");

                // Verifikasi bahwa ID objek yang dihapus terdapat dalam pesan (contoh ID 6)
                Assert.assertTrue(deleteResponse.getMessage().contains("id = ff808181932badb60195186cb5924f17"),
                                "Response message should contain the deleted object ID (id = ff808181932badb60195186cb5924f17)");
        }

}
