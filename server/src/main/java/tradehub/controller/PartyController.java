package tradehub.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import tradehub.model.Party;
import tradehub.service.PartyService;

@RestController
@RequestMapping("/api/parties")
public class PartyController {

  @Autowired
  private PartyService partyService;
  private static final Logger logger = LoggerFactory.getLogger(PartyController.class);

  @GetMapping
  public ResponseEntity<List<Party>> getAllParties() {
    try {
      List<Party> parties = partyService.getAllParties();
      return ResponseEntity.ok(parties);
    }
    catch (Exception e) {
      logger.error("Error retrieveing parties", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
