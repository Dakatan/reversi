package com.example.reversi;

public enum Stone {
  WHITE("○"), BLACK("●"), NONE("・");

  private String string;

  Stone(String string) {
    this.string = string;
  }

  public String asString() {
    return string;
  }
}
