package tradehub.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import tradehub.model.Supply;
import tradehub.service.SupplyService;

@RestController
@RequestMapping("/api/supplies")
public class SupplyController {

  @Autowired
  private SupplyService supplyService;
  private static final Logger logger = LoggerFactory.getLogger(SupplyController.class);

  @PostMapping
  public ResponseEntity<Map<String,Object>> createSupply(@RequestBody Supply supply) {
    Map<String,Object> response = new HashMap<>();

    try {
      supplyService.saveSupply(supply);

      response.put("supplyId", "2244"); 
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    catch (Exception e) {
      logger.error("Error creating supply", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping
  public ResponseEntity<List<Supply>> getAllParties() {
    try {
      List<Supply> supplies = supplyService.getAllSupplies();
      return ResponseEntity.ok(supplies);
    }
    catch (Exception e) {
      logger.error("Error retrieveing parties", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
