package tradehub.model;

import java.util.List;

public class Embedding {
  private List<Float> vector;

  public Embedding(List<Float> vector) {
    this.vector = vector;
  }

  public List<Float> getVector() {
    return vector;
  }

  public void setVector(List<Float> vector) {
    this.vector = vector;
  }
}
