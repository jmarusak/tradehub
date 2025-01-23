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
import tradehub.model.Demand;
import tradehub.service.DemandService;

@RestController
@RequestMapping("/api/demands")
public class DemandController {
  private static final Logger logger = LoggerFactory.getLogger(DemandController.class);

  @Autowired private DemandService demandService;

  @PostMapping
  public ResponseEntity<Map<String, Object>> createDemand(@RequestBody Demand demand) {
    Map<String, Object> response = new HashMap<>();

    try {
      demandService.saveDemand(demand);

      response.put("demandId", "OK");
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    } catch (Exception e) {
      logger.error("Error creating demand", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping
  public ResponseEntity<List<Demand>> getAllDemands() {
    try {
      List<Demand> demands = demandService.getAllSupplies();
      return ResponseEntity.ok(demands);
    } catch (Exception e) {
      logger.error("Error retrieving demands", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
