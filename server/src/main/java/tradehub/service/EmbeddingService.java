package tradehub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tradehub.model.Embedding;
import tradehub.integration.VertexAiClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmbeddingService {

  @Autowired
  private VertexAiClient vertexAiClient;

  public Embedding createEmbedding(String text) {

    List<Float> vector = vertexAiClient.predict(text); 
    return new Embedding(vector);
  }
}
