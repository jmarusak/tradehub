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
import tradehub.model.Supply;
import tradehub.service.SupplyService;

@RestController
@RequestMapping("/api/supply")
public class SupplyController {
  private static final Logger logger = LoggerFactory.getLogger(SupplyController.class);

  @Autowired private SupplyService supplyService;

  @PostMapping
  public ResponseEntity<Map<String, Object>> createSupply(@RequestBody Supply supply) {
    Map<String, Object> response = new HashMap<>();

    try {
      supplyService.saveSupply(supply);

      response.put("supplyId", "OK");
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    } catch (Exception e) {
      logger.error("Error creating supply", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping
  public ResponseEntity<List<Supply>> getAllSupplies() {
    try {
      List<Supply> supplies = supplyService.getAllSupplies();
      return ResponseEntity.ok(supplies);
    } catch (Exception e) {
      logger.error("Error retrieving supplies", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
