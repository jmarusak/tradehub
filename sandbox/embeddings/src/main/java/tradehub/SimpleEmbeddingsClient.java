package tradehub;

import static java.util.stream.Collectors.toList;

import com.google.cloud.aiplatform.v1.EndpointName;
import com.google.cloud.aiplatform.v1.PredictRequest;
import com.google.cloud.aiplatform.v1.PredictResponse;
import com.google.cloud.aiplatform.v1.PredictionServiceClient;
import com.google.cloud.aiplatform.v1.PredictionServiceSettings;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleEmbeddingsClient {
  public static void main(String[] args) throws IOException {
    String project = System.getenv("GOOGLE_CLOUD_PROJECT");
    System.out.println("Project ID: " + project);

    // https://cloud.google.com/vertex-ai/docs/generative-ai/embeddings/get-text-embeddings
    String endpoint = "us-central1-aiplatform.googleapis.com:443";
    String model = "text-embedding-005";
    List<Float> embedding = predictTextEmbeddings(
        endpoint,
        project,
        model,
        "banana bread",
        "SEMANTIC_SIMILARITY",
        OptionalInt.of(128));

    System.out.println(embedding);
  }

  // Gets text embeddings from a pretrained, foundational model.
  public static List<Float> predictTextEmbeddings(
      String endpoint,
      String project,
      String model,
      String text,
      String task,
      OptionalInt outputDimensionality)
      throws IOException {
    PredictionServiceSettings settings =
        PredictionServiceSettings.newBuilder().setEndpoint(endpoint).build();
    Matcher matcher = Pattern.compile("^(?<Location>\\w+-\\w+)").matcher(endpoint);
    String location = matcher.matches() ? matcher.group("Location") : "us-central1";
    EndpointName endpointName =
        EndpointName.ofProjectLocationPublisherModelName(project, location, "google", model);

    // You can use this prediction service client for multiple requests.
    try (PredictionServiceClient client = PredictionServiceClient.create(settings)) {
      PredictRequest.Builder request =
          PredictRequest.newBuilder().setEndpoint(endpointName.toString());
      if (outputDimensionality.isPresent()) {
        request.setParameters(
            Value.newBuilder()
                .setStructValue(
                    Struct.newBuilder()
                        .putFields("outputDimensionality", valueOf(outputDimensionality.getAsInt()))
                        .build()));
      }

      request.addInstances(
          Value.newBuilder()
              .setStructValue(
                  Struct.newBuilder()
                      .putFields("content", valueOf(text))
                      .putFields("task_type", valueOf(task))
                      .build()));
      
      PredictResponse response = client.predict(request.build());

      List<Float> floats = new ArrayList<Float>();
      for (Value prediction : response.getPredictionsList()) {
        Value embeddings = prediction.getStructValue().getFieldsOrThrow("embeddings");
        Value values = embeddings.getStructValue().getFieldsOrThrow("values");
        floats = values.getListValue().getValuesList().stream()
                  .map(Value::getNumberValue)
                  .map(Double::floatValue)
                  .collect(toList());
      }

      return floats;
    }
  }

  private static Value valueOf(String s) {
    return Value.newBuilder().setStringValue(s).build();
  }

  private static Value valueOf(int n) {
    return Value.newBuilder().setNumberValue(n).build();
  }
}
