package resources;

import java.util.HashMap;
import java.util.Map;

public class DataRequest {
    //Berisi data test
    /*
     * Mapping berisi 2 komponen
     * 1. Key
     * 2. Value
     */
    public Map<String, String> addItemCollection() {
        Map<String, String> dataCollection = new HashMap<>();

        dataCollection.put("addItem", "{\n" +
                "   \"name\": \"Apple MacBook Pro 16 Untuk Testing Post\",\n" +
                "   \"data\": {\n" +
                "      \"year\": 2099,\n" +
                "      \"price\": 40000,\n" +
                "      \"CPU model\": \"Intel Core i9 Untuk Testing Post\",\n" +
                "      \"Hard disk size\": \"1 TB Post\"\n" +
                "   }\n" +
                "}");

        dataCollection.put("addItem2", "{\n" +
                "   \"name\": \"Apple MacBook Air 15 Untuk Testing Post\",\n" +
                "   \"data\": {\n" +
                "      \"year\": 2025,\n" +
                "      \"price\": 30000,\n" +
                "      \"CPU model\": \"Intel Core i7 Untuk Testing Post\",\n" +
                "      \"Hard disk size\": \"512 GB Post\"\n" +
                "   }\n" +
                "}");

        return dataCollection;
    }
}
