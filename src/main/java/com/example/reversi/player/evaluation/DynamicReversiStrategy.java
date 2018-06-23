package com.example.reversi.player.evaluation;

import com.example.reversi.Board;
import com.example.reversi.Stone;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

public class DynamicReversiStrategy implements ReversiStrategy {

  @Override
  public int[][] getScoreBoard(Board board, Stone stone) {
    int[][] scoreBoard = {
          { 320, -20,  20,  5,  5,  20, -20,  320},
          { -20, -40,  -5, -5, -5,  -5, -40,  -20},
          {  20,  -5,  15,  3,  3,  15,  -5,   20},
          {   5,  -5,   3,  3,  3,   3,  -5,    5},
          {   5,  -5,   3,  3,  3,   3,  -5,    5},
          {  20,  -5,  15,  3,  3,  15,  -5,   20},
          { -20, -40,  -5, -5, -5,  -5, -40,  -20},
          { 320, -20,  20,  5,  5,  20, -20,  320}
    };

    for(Direction direction : Direction.values()) {
      Queue<PositionSupport> queue = new ArrayDeque<>();
      queue.offer(new PositionSupport(direction.sx(), direction.sy(), scoreBoard[direction.sy()][direction.sx()]));
      while (queue.size() != 0) {
        PositionSupport position = queue.poll();

        if(board.get(position.x() + 1, position.y() + 1) != stone) continue;
        scoreBoard[position.y()][position.x()] = position.value();

        PositionSupport position1 = new PositionSupport(position.x(), position.y() + direction.vy(), 50);
        PositionSupport position2 = new PositionSupport(position.x() + direction.vx(), position.y(), 50);

        if(!queue.contains(position1) && direction.inRange(position1.x(), position1.y())) queue.offer(position1);
        if(!queue.contains(position2) && direction.inRange(position2.x(), position2.y())) queue.offer(position2);
      }
    }
    return scoreBoard;
  }

  private static class PositionSupport {
    private final int x;
    private final int y;
    private final int value;

    private PositionSupport(int x, int y, int value) {
      this.x = x;
      this.y = y;
      this.value = value;
    }

    private int x() {
      return x;
    }

    private int y() {
      return y;
    }

    private int value() {
      return value;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof PositionSupport)) return false;
      PositionSupport positionSupport = (PositionSupport) o;
      return x == positionSupport.x &&
              y == positionSupport.y;
    }

    @Override
    public int hashCode() {
      return Objects.hash(x, y);
    }
  }

  private enum Direction {
    AREA1(1, 1, 0, 0) {
      @Override
      public boolean inRange(int x, int y) {
        return x < 4 && y < 4;
      }
    }, AREA2(1, -1, 0 ,7) {
      @Override
      public boolean inRange(int x, int y) {
        return x < 4 && y > 3;
      }
    }, AREA3(-1, 1, 7, 0) {
      @Override
      public boolean inRange(int x, int y) {
        return x > 3 && y < 4;
      }
    }, AREA4(-1, -1, 7, 7) {
      @Override
      public boolean inRange(int x, int y) {
        return x > 3 && y > 3;
      }
    };

    private final int vx;
    private final int vy;
    private final int sx;
    private final int sy;

    Direction(int vx, int vy, int sx, int sy) {
      this.vx = vx;
      this.vy = vy;
      this.sx = sx;
      this.sy = sy;
    }

    public int vx() {
      return vx;
    }

    public int vy() {
      return vy;
    }

    public int sx() {
      return sx;
    }

    public int sy() {
      return sy;
    }

    public abstract boolean inRange(int x, int y);
  }
}