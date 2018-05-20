package com.example.reversi;

import java.util.Set;

public interface Board {

  int put(int x, int y, Stone stone);

  boolean canPut(Stone stone);

  int getCount(Stone stone);

  boolean isFinish();

  Set<int[]> getCanPutArea(Stone stone);

  Stone get(int x, int y);

  void print();
}
