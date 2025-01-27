package tradehub.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tradehub.model.Party;
import tradehub.service.PartyService;

@RestController
@RequestMapping("/api/party")
public class PartyController {
  private static final Logger logger = LoggerFactory.getLogger(PartyController.class);

  @Autowired private PartyService partyService;

  @PostMapping
  public ResponseEntity<Map<String, Object>> createParty(@RequestBody Party party) {
    Map<String, Object> response = new HashMap<>();

    try {
      partyService.saveParty(party);

      response.put("partyId", "OK");
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    } catch (Exception e) {
      logger.error("Error creating party", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping
  public ResponseEntity<List<Party>> getAllParties() {
    try {
      List<Party> parties = partyService.getAllParties();
      return ResponseEntity.ok(parties);
    } catch (Exception e) {
      logger.error("Error retrieving parties", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
