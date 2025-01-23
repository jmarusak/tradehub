package tradehub.controller;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tradehub.model.Embedding;
import tradehub.model.Prompt;
import tradehub.service.EmbeddingService;

@RestController
@RequestMapping("/api/embeddings")
public class EmbeddingController {
  private static final Logger logger = LoggerFactory.getLogger(EmbeddingController.class);

  @Autowired private EmbeddingService embeddingService;

  @PostMapping
  public ResponseEntity<Map<String, Object>> createEmbedding(@RequestBody Prompt prompt) {
    Map<String, Object> response = new HashMap<>();

    try {
      Embedding embedding = embeddingService.createEmbedding(prompt.getText());

      response.put("vector", embedding.getVector());
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    } catch (Exception e) {
      logger.error("Error creating supply", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
