package tradehub.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tradehub.integration.VertexAiClient;
import tradehub.model.Embedding;

@Service
public class EmbeddingService {

  @Autowired private VertexAiClient vertexAiClient;

  public Embedding createEmbedding(String text) {
    List<Float> vector = vertexAiClient.predict(text);
    return new Embedding(vector);
  }
}
