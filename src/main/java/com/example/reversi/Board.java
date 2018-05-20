package com.example.reversi;

public interface Board {

  int put(int x, int y, Stone stone);

  boolean canPut(Stone stone);

  int getCount(Stone stone);

  boolean isFinish();

  Stone[][] getRawBoard();
}
