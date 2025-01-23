package tradehub.model;

public class Party {
  private String partyId;
  private String name;

  public Party(String partyId, String name) {
    this.partyId = partyId;
    this.name = name;
  }

  public String getPartyId() {
    return partyId;
  }

  public void setPartyId(String partyId) {
    this.partyId = partyId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
