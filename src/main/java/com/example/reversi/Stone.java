package com.example.reversi;

public enum Stone {

  /** 白色の石. */
  WHITE("○"),

  /** 黒色の石. */
  BLACK("●"),

  /** 石が何もおいていない. */
  NONE("・");

  private String string;

  Stone(String string) {
    this.string = string;
  }

  public String asString() {
    return string;
  }

  public Stone reverse() {
    if(this == NONE) return NONE;
    return this == BLACK ? WHITE : BLACK;
  }
}
