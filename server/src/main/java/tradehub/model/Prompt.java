package tradehub.model;

public class Prompt {
  private String text;

  public Prompt(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
