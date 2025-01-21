package tradehub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.cloud.bigquery.TableResult;

import tradehub.model.Party;
import tradehub.datastore.BigQueryClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class PartyService {

  @Autowired
  private BigQueryClient bqClient;

  public List<Party> getAllParties() {
    TableResult result = bqClient.execute("SELECT * FROM tradehub.party");

    List<Party> parties = new ArrayList<>();
    result.iterateAll().forEach(row -> {
      String partyId = row.get("party_id").getStringValue();
      String name = row.get("name").getStringValue();
      String email = row.get("email").getStringValue();

      Party party = new Party(partyId, name, email);
      parties.add(party);
    });

    return parties;
  }
}
