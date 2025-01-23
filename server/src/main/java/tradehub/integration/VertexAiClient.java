package tradehub.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.cloud.aiplatform.v1.EndpointName;
import com.google.cloud.aiplatform.v1.PredictRequest;
import com.google.cloud.aiplatform.v1.PredictResponse;
import com.google.cloud.aiplatform.v1.PredictionServiceClient;

import com.google.protobuf.Struct;
import com.google.protobuf.Value;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.stream.Collectors.toList;

@Component
public class VertexAiClient {
  
  @Autowired
  private PredictionServiceClient predictionClient;

  public List<Float> predict(String text) {
    String project = System.getenv("GOOGLE_CLOUD_PROJECT");
    String endpoint = "us-central1-aiplatform.googleapis.com:443";
    String model = "text-embedding-005";
    String task = "SEMANTIC_SIMILARITY";
    int outputDimensionality = 256;
    
    Matcher matcher = Pattern.compile("^(?<Location>\\w+-\\w+)").matcher(endpoint);
    String location = matcher.matches() ? matcher.group("Location") : "us-central1";

    EndpointName endpointName =
        EndpointName.ofProjectLocationPublisherModelName(project, location, "google", model);
    
    PredictRequest.Builder request =
        PredictRequest.newBuilder().setEndpoint(endpointName.toString());
    request.setParameters(
        Value.newBuilder()
            .setStructValue(
                Struct.newBuilder()
                    .putFields("outputDimensionality", valueOf(outputDimensionality))
                    .build()));

    request.addInstances(
        Value.newBuilder()
            .setStructValue(
                Struct.newBuilder()
                    .putFields("content", valueOf(text))
                    .putFields("task_type", valueOf(task))
                    .build()));
    
    PredictResponse response = predictionClient.predict(request.build());

    List<Float> vector = new ArrayList<Float>();
    for (Value prediction : response.getPredictionsList()) {
      Value embeddings = prediction.getStructValue().getFieldsOrThrow("embeddings");
      Value values = embeddings.getStructValue().getFieldsOrThrow("values");
      vector = values.getListValue().getValuesList().stream()
                .map(Value::getNumberValue)
                .map(Double::floatValue)
                .collect(toList());
    }

    return vector;
  }

  private static Value valueOf(String s) {
    return Value.newBuilder().setStringValue(s).build();
  }

  private static Value valueOf(int n) {
    return Value.newBuilder().setNumberValue(n).build();
  }
}
