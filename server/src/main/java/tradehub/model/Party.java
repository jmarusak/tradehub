package tradehub.model;

public class Party {
  private String partyId;
  private String name;
  private String email;

  public Party(String partyId, String name, String email) {
    this.partyId = partyId;
    this.name = name;
    this.email = email;
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
