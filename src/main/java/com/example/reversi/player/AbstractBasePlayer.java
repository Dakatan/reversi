package com.example.reversi.player;

import com.example.reversi.Board;
import com.example.reversi.Stone;

public abstract class AbstractBasePlayer implements Player {
  protected final Stone stone;
  protected final Board board;

  public AbstractBasePlayer(Stone stone, Board board) {
    this.stone = stone;
    this.board = board;
  }

  @Override
  public void play() {
    if(!board.canPut(stone)) return;
    int[] area = getArea();
    if(area == null || area.length != 2) return;
    int x = area[0] - 1;
    int y = area[1] - 1;
    board.put(x, y, stone);
  }

  protected abstract int[] getArea();
}
