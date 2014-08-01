package de.mpii.ternarytree;

public class Match {
  public int tokenOffset;
  public int tokenCount;
  public int value;
  
  public Match(int tokenOffset, int tokenCount, int value) {
      this.tokenOffset = tokenOffset;
      this.tokenCount = tokenCount;
      this.value = value;
  }
}
