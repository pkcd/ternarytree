package de.mpii.ternarytree;

public class Match {

  private int tokenCount;
  private int value;

  public Match(int tokenCount, int value) {
    this.tokenCount = tokenCount;
    this.value = value;
  }

  public int getTokenCount() {
    return tokenCount;
  }

  public void setTokenCount(int tokenCount) {
    this.tokenCount = tokenCount;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }
}
