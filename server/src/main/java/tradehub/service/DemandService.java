package tradehub.service;

import com.google.cloud.bigquery.TableResult;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tradehub.integration.BigQueryClient;
import tradehub.model.Demand;

@Service
public class DemandService {

  @Autowired private BigQueryClient bqClient;

  public void saveDemand(Demand demand) {
    demand.setDemandId(UUID.randomUUID().toString());

    String stmt =
        String.format(
            """
            INSERT INTO `tradehub.demand` (demand_id, party_id, title, price, description, embedding)
            VALUES ('%s', '%s', '%s', %f, '%s', %s)
            """,
            demand.getDemandId(),
            demand.getPartyId(),
            demand.getTitle(),
            demand.getPrice(),
            demand.getDescription(),
            demand.getEmbedding().toString());

    bqClient.execute(stmt);
  }

  public List<Demand> getAllSupplies() {
    TableResult result = bqClient.execute("SELECT * FROM tradehub.demand");

    List<Demand> demands = new ArrayList<>();
    result
        .iterateAll()
        .forEach(
            row -> {
              String demandId = row.get("demand_id").getStringValue();
              String partyId = row.get("party_id").getStringValue();
              String title = row.get("title").getStringValue();
              Double price = row.get("price").getDoubleValue();
              String description = row.get("description").getStringValue();

              Demand demand = new Demand(demandId, partyId, title, price, description, null);
              demands.add(demand);
            });

    return demands;
  }
}
