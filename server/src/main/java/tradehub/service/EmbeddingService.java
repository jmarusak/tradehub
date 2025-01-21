package tradehub.service;

import org.springframework.stereotype.Service;

import tradehub.model.Embedding;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmbeddingService {

  public Embedding getEmbedding(String text) {

    List<Float> vector = List.of(1.34f, 2.22f, 0.34f); 
    return new Embedding(vector);
  }
}
