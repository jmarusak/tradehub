import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SimpleHttpClient {
  public static void main(String[] args) {
    try {
      // Create an HttpClient instance
      HttpClient client = HttpClient.newHttpClient();

      // Define a GET request
      HttpRequest request =
          HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/query")).GET().build();

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
