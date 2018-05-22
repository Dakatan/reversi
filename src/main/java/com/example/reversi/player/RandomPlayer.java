package com.example.reversi.player;

import com.example.reversi.Board;
import com.example.reversi.Stone;

import java.util.Random;

public class RandomPlayer implements Player {

  private Random random = new Random();

  @Override
  public Position play(Board board, Stone stone) {
    if(!board.canPut(stone)) throw new IllegalArgumentException("board cannot put");
    for(;;) {
      int x = random.nextInt(8) + 1;
      int y = random.nextInt(8) + 1;
      if(board.put(x, y, stone, false) > 0) return Position.at(x, y);
    }
  }
}
