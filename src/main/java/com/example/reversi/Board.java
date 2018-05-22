package com.example.reversi;

public interface Board extends Cloneable {

  int put(int x, int y, Stone stone);

  int put(int x, int y, Stone stone, boolean isReverse);

  boolean canPut(Stone stone);

  int count(Stone stone);

  boolean isFinish();

  Stone get(int x, int y);

  void print();

  Board clone();
}
