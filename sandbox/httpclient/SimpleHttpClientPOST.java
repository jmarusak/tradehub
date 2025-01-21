import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SimpleHttpClientPOST {
    public static void main(String[] args) {
        try {
            // Create an HttpClient instance
            HttpClient client = HttpClient.newHttpClient();

            // Create a supply object (model instance)
            Supply supply = new Supply();
            supply.setSupplyId("");
            supply.setPartyId("3");
            supply.setTitle("wrap");
            supply.setPrice(11.22);
            supply.setDescription("corn wrap");
            supply.setEmbedding(new double[]{1.2, 22.2, 0.4});

            // Convert the supply object to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(supply);

            // Define a POST request
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/supplies"))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(requestBody))
                .build();

            // Send the request and receive a response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Print the response
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
