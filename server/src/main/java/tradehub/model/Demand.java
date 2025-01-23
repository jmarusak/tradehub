package tradehub.model;

import java.util.List;

public class Demand {
  private String demandId;
  private String partyId;
  private String title;
  private Double price;
  private String description;
  private List<Float> embedding;

  public Demand(
      String demandId,
      String partyId,
      String title,
      Double price,
      String description,
      List<Float> embedding) {
    this.demandId = demandId;
    this.partyId = partyId;
    this.title = title;
    this.price = price;
    this.description = description;
    this.embedding = embedding;
  }

  public String getDemandId() {
    return demandId;
  }

  public void setDemandId(String demandId) {
    this.demandId = demandId;
  }

  public String getPartyId() {
    return partyId;
  }

  public void setPartyId(String partyId) {
    this.partyId = partyId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<Float> getEmbedding() {
    return embedding;
  }

  public void setEmbedding(List<Float> embedding) {
    this.embedding = embedding;
  }
}
