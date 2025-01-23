package tradehub.service;

import com.google.cloud.bigquery.TableResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import tradehub.integration.BigQueryClient;
import tradehub.model.Party;

@Service
public class PartyService {

  @Autowired private BigQueryClient bqClient;

  public void saveParty(Party party) {
    String stmt =
        String.format(
            """
            INSERT INTO `tradehub.party` (party_id, name)
            VALUES ('%s', '%s')
            """,
            party.getPartyId(), party.getName());

    bqClient.execute(stmt);
  }

  public List<Party> getAllParties() {
    TableResult result = bqClient.execute("SELECT party_id, name FROM tradehub.party");

    List<Party> parties = new ArrayList<>();
    result
        .iterateAll()
        .forEach(
            row -> {
              String partyId = row.get("party_id").getStringValue();
              String name = row.get("name").getStringValue();

              Party party = new Party(partyId, name);
              parties.add(party);
            });

    return parties;
  }
}
