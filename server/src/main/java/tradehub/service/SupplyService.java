package tradehub.service;

import com.google.cloud.bigquery.TableResult;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tradehub.integration.BigQueryClient;
import tradehub.model.Supply;

@Service
public class SupplyService {

  @Autowired private BigQueryClient bqClient;

  public void saveSupply(Supply supply) {
    supply.setSupplyId(UUID.randomUUID().toString());

    String stmt =
        String.format(
            """
            INSERT INTO `tradehub.supply` (supply_id, party_id, title, price, description, embedding)
            VALUES ('%s', '%s', '%s', %f, '%s', %s)
            """,
            supply.getSupplyId(),
            supply.getPartyId(),
            supply.getTitle(),
            supply.getPrice(),
            supply.getDescription(),
            supply.getEmbedding().toString());

    bqClient.execute(stmt);
  }

  public List<Supply> getAllSupplies() {
    TableResult result = bqClient.execute("SELECT * FROM tradehub.supply");

    List<Supply> supplies = new ArrayList<>();
    result
        .iterateAll()
        .forEach(
            row -> {
              String supplyId = row.get("supply_id").getStringValue();
              String partyId = row.get("party_id").getStringValue();
              String title = row.get("title").getStringValue();
              Double price = row.get("price").getDoubleValue();
              String description = row.get("description").getStringValue();

              Supply supply = new Supply(supplyId, partyId, title, price, description, null);
              supplies.add(supply);
            });

    return supplies;
  }
}
