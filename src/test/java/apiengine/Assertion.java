package apiengine;

import org.testng.Assert;

import com.apiautomation.model.PostResponse;
import com.apiautomation.model.getResponseById;
import com.apiautomation.model.request.RequestItem;

public class Assertion {

    public void assertAddProduct(PostResponse postResponse, RequestItem requestItem) {
        Assert.assertEquals(postResponse.name, requestItem.name);
        Assert.assertEquals(postResponse.dataItem.price, requestItem.dataItem.price);
        Assert.assertEquals(postResponse.dataItem.year, requestItem.dataItem.year);
        Assert.assertEquals(postResponse.dataItem.cpuModel, requestItem.dataItem.cpuModel);
        Assert.assertEquals(postResponse.dataItem.hardDiskSize, requestItem.dataItem.hardDiskSize);
    }

    public void assertAddNewProduct(PostResponse postResponse) {
        Assert.assertEquals(postResponse.name, "Apple MacBook Pro 16 Untuk Testing Post");
        Assert.assertNotNull(postResponse.createdAt);
        Assert.assertNotNull(postResponse.id);
        Assert.assertEquals(postResponse.dataItem.year, 2099);
        Assert.assertEquals(postResponse.dataItem.price, 40000);
        Assert.assertEquals(postResponse.dataItem.cpuModel, "Intel Core i9 Untuk Testing Post");
        Assert.assertEquals(postResponse.dataItem.hardDiskSize, "1 TB Post");
    }

    public void assertGetSingelProduct(getResponseById getResponseById) {
        Assert.assertNotNull(getResponseById, "getResponseById is null!");
        Assert.assertEquals(getResponseById.id, "7", "ID tidak sesuai!");
        Assert.assertEquals(getResponseById.name, "Apple MacBook Pro 16", "Nama produk tidak sesuai!");

        // Validasi Data Item
        Assert.assertNotNull(getResponseById.dataItem, "Data item is null!");
        Assert.assertEquals(getResponseById.dataItem.year, 2019, "Tahun tidak sesuai!");
        Assert.assertEquals(getResponseById.dataItem.price, 1849.99, "Harga tidak sesuai!");
        Assert.assertEquals(getResponseById.dataItem.cpuModel, "Intel Core i9", "Model CPU tidak sesuai!");
        Assert.assertEquals(getResponseById.dataItem.hardDiskSize, "1 TB", "Ukuran hard disk tidak sesuai!");
    }
}
