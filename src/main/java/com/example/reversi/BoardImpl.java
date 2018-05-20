package com.example.reversi;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BoardImpl implements Board {

  private static final int WIDTH = 8;
  private static final int HEIGHT = 8;
  private final Stone[][] board = new Stone[HEIGHT][WIDTH];

  public BoardImpl() {
    for(int i = 0; i < HEIGHT; i++) {
      for(int j = 0; j < WIDTH; j++) {
        board[i][j] = Stone.NONE;
      }
    }
    board[3][3] = Stone.WHITE;
    board[4][4] = Stone.WHITE;
    board[4][3] = Stone.BLACK;
    board[3][4] = Stone.BLACK;
  }

  public BoardImpl(Stone[][] board) {
    if(board.length != HEIGHT) throw new IllegalArgumentException("board height is invalid.");
    for(Stone[] stones : board) {
      if(stones.length != WIDTH) throw new IllegalArgumentException("board width is invalid.");
    }
    for(int i = 0; i < HEIGHT; i++) {
      System.arraycopy(board, 0, this.board, 0, board.length);
    }
  }

  @Override
  public int put(int x, int y, Stone stone) {
    return innerPut(x, y, stone, true);
  }

  @Override
  public boolean canPut(Stone stone) {
    if(stone == null) return false;
    Set<Coordinate> targets = getCanPutZone(stone);
    return targets.size() != 0;
  }

  @Override
  public int getCount(Stone stone) {
    int result = 0;
    for(int i = 0; i < HEIGHT; i++) {
      for(int j = 0; j < WIDTH; j++) {
        if(board[i][j] == stone) result++;
      }
    }
    return result;
  }

  @Override
  public boolean isFinish() {
    return !canPut(Stone.WHITE) && !canPut(Stone.BLACK);
  }

  @Override
  public Stone[][] getRawBoard() {
    Stone[][] cloned = new Stone[HEIGHT][WIDTH];
    for(int i = 0; i < HEIGHT; i++) {
      System.arraycopy(board, 0, cloned, 0, board.length);
    }
    return cloned;
  }

  private boolean isOutOfRange(int x, int y) {
    return x < 0 || y < 0 || x >= WIDTH || y >= HEIGHT;
  }

  private int reverse(int x, int y, int vx, int vy, Stone stone, boolean isReverse) {
    if(isOutOfRange(x, y)) return -1;
    if(board[y][x] == Stone.NONE) return -1;
    if(board[y][x] == stone) return 0;
    int depth = reverse(x + vx, y + vy, vx, vy, stone, isReverse);
    if(depth > -1) {
      if(isReverse) board[y][x] = stone;
      return depth + 1;
    }
    return depth;
  }

  private Set<Coordinate> getCanPutZone(Stone stone) {
    Set<Coordinate> result = new HashSet<>();
    for(int y = 0; y < HEIGHT; y++) {
      for (int x = 0; x < WIDTH; x++) {
        if (board[y][x] != Stone.NONE) continue;
        for (int i = -1; i <= 1; i++) {
          for (int j = -1; j <= 1; j++) {
            if (i == 0 && j == 0) continue;
            if (isOutOfRange(x + i, y + j)) continue;
            if (board[y + j][x + i] == Stone.NONE) continue;

            if (innerPut(x, y, stone, false) > 0) {
              result.add(new Coordinate(x, y));
            }
          }
        }
      }
    }
    return result;
  }

  private int innerPut(int x, int y, Stone stone, boolean isReverse) {
    if(isOutOfRange(x, y)) return 0;
    if(board[y][x] != Stone.NONE) return 0;

    int count = 0;
    for(int i = -1; i <= 1; i++) {
      for(int j = -1; j <= 1; j++) {
        if(i == 0 && j == 0) continue;
        int tmp = reverse(x + i, y + j, i, j, stone, isReverse);
        count += tmp > 0 ? tmp : 0;
      }
    }

    if(count > 0) {
      if(isReverse) board[y][x] = stone;
      count++;
    }
    return count;
  }

  private static class Coordinate {
    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public int getX() {
      return x;
    }

    public int getY() {
      return y;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Coordinate)) return false;
      Coordinate that = (Coordinate) o;
      return getX() == that.getX() &&
              getY() == that.getY();
    }

    @Override
    public int hashCode() {

      return Objects.hash(getX(), getY());
    }
  }
}
