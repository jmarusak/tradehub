package tradehub.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import tradehub.model.Supply;
import tradehub.service.SupplyService;

@RestController
@RequestMapping("/api/supplies")
public class SupplyController {

  @Autowired
  private SupplyService supplyService;
  private static final Logger logger = LoggerFactory.getLogger(SupplyController.class);

  @PostMapping
  public ResponseEntity<Void> createSupply(@RequestBody Supply supply) {
    try {
      Supply mysupply = new Supply("1", "2", "banana", 23.22, "banana bread", null);
      supplyService.saveSupply(mysupply);
      return ResponseEntity.status(HttpStatus.CREATED).build();
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
