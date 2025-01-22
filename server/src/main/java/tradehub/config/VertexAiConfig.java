package tradehub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.cloud.aiplatform.v1.PredictionServiceClient;
import com.google.cloud.aiplatform.v1.PredictionServiceSettings;

import java.io.IOException;

@Configuration
public class VertexAiConfig {

  @Bean
  public PredictionServiceClient predictionClient() throws IOException {
    String endpoint = "us-central1-aiplatform.googleapis.com:443";

    PredictionServiceSettings settings =
        PredictionServiceSettings.newBuilder().setEndpoint(endpoint).build();

    PredictionServiceClient predictionClient = PredictionServiceClient.create(settings);
    return predictionClient;
  }
}
