package com.example.reversi.player;

import com.example.reversi.Board;
import com.example.reversi.Stone;

public interface Player {
  Position play(Board board, Stone stone);

  class Position {
    private final int x;
    private final int y;

    private Position(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public int x() {
      return x;
    }

    public int y() {
      return y;
    }

    public static Position at(int x, int y) {
      return new Position(x, y);
    }
  }
}
