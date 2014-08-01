package de.mpii.ternarytree;

public class Match {

  private int tokenOffset;
  private int tokenCount;
  private int value;

  public Match(int tokenOffset, int tokenCount, int value) {
    this.tokenOffset = tokenOffset;
    this.tokenCount = tokenCount;
    this.value = value;
  }

  public int getTokenOffset() {
    return tokenOffset;
  }

  public void setTokenOffset(int tokenOffset) {
    this.tokenOffset = tokenOffset;
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
