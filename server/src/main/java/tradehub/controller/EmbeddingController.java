package tradehub.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import tradehub.model.Embedding;
import tradehub.service.EmbeddingService;

@RestController
@RequestMapping("/api/embeddings")
public class EmbeddingController {

  @Autowired
  private EmbeddingService embeddingService;
  private static final Logger logger = LoggerFactory.getLogger(EmbeddingController.class);

  @GetMapping
  public ResponseEntity<Embedding> getEmbedding() {
    try {
      Embedding embedding = embeddingService.getEmbedding("banana bread");
      return ResponseEntity.ok(embedding);
    }
    catch (Exception e) {
      logger.error("Error retrieveing embeddings", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
