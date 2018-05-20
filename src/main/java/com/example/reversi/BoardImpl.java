package com.example.reversi;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * {@linkplain Board}の実装クラス.<br />
 * 再帰的にひっくり返し処理を行うがリバーシの仕様上たかだか8回の再帰ですむため問題ないとしている.
 */
public class BoardImpl implements Board {

  /** X軸の長さ. */
  private static final int WIDTH = 8;

  /** Y軸の長さ. */
  private static final int HEIGHT = 8;

  /** ボードの実態. */
  private final Stone[][] board = new Stone[HEIGHT][WIDTH];

  /**
   * コンストラクタ.
   */
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

  /**
   * コンストラクタ.<br>
   * ボードを引数にとりそのボードの状態と同じ状態にする.<br>
   * ディープコピーを行うため内部の状態は共有しない.
   * @param newBoard ボード
   */
  public BoardImpl(Board newBoard) {
    if(newBoard == null) throw new IllegalArgumentException("board must not be null.");
    for(int i = 0; i < HEIGHT; i++) {
      for(int j = 0; j < WIDTH; j++) {
        this.board[i][j] = newBoard.get(j + 1, i + 1);
      }
    }
  }

  @Override
  public int put(int x, int y, Stone stone) {
    return innerPut(x - 1, y - 1, stone, true);
  }

  @Override
  public boolean canPut(Stone stone) {
    if(stone == null) return false;
    Set<Coordinate> targets = getInnerCanPutArea(stone);
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
  public Set<int[]> getCanPutArea(Stone stone) {
    Set<int[]> result = new HashSet<>();
    for(Coordinate coordinate : getInnerCanPutArea(stone)) {
      result.add(new int[]{coordinate.getX() + 1, coordinate.getY() + 1});
    }
    return result;
  }

  @Override
  public Stone get(int x, int y) {
    if(isOutOfRange(x - 1, y - 1)) return null;
    return board[y - 1][x - 1];
  }

  @Override
  public void print() {
    for(int i = 0; i < HEIGHT; i++) {
      for(int j = 0; j < WIDTH; j++) {
        System.out.print(board[i][j].asString());
      }
      System.out.println();
    }
    System.out.println();
    System.out.println();
  }

  /**
   * X軸とY軸の値からそのマスはレンジ外かどうか判定する.<br />
   * レンジ外の場合は{@code true}.<br />
   * レンジ内の場合は{@code false}.
   * @param x X軸の値
   * @param y Y軸の値
   * @return レンジの内外かどうかの判定値
   */
  private boolean isOutOfRange(int x, int y) {
    return x < 0 || y < 0 || x >= WIDTH || y >= HEIGHT;
  }

  /**
   * 内部的に石を置く.<br>
   * おいた後いくつの石が更新されたかどうか返す.<br />
   * つまりひっくり返した数+1されるためひっくり返せる場合は少なくとも2以上の値を返す.<br />
   * 何もひっくり返すものがなく置けなかった場合は0を返す.<br />
   * レンジ外に石を置こうとした場合も同様である.<br />
   * {@code isReverse}が{@code true}の場合はひっくり返すが、
   * {@code false}の場合はひっくり返さない.<br />
   * {@code false}の場合はひっくり返さないが置いた場合いくつ更新されるかを返す.
   * @param x X軸の値
   * @param y Y軸の値
   * @param stone 置く石
   * @param isReverse ひっくり返すかどうかのフラグ
   * @return 更新した石の数
   */
  private int innerPut(int x, int y, Stone stone, boolean isReverse) {
    if(isOutOfRange(x, y)) return 0;
    if(board[y][x] != Stone.NONE) return 0;

    int count = 0;
    for(int i = -1; i <= 1; i++) {
      for(int j = -1; j <= 1; j++) {
        if(i == 0 && j == 0) continue;
        int tmp = reverse(x + i, y + j, i, j, stone, isReverse);
        if(tmp > 0) count += tmp;
      }
    }

    if(count > 0) {
      if(isReverse) board[y][x] = stone;
      count++;
    }
    return count;
  }

  /**
   * 再帰的にひっくり返す.<br />
   * {@linkplain BoardImpl#innerPut(int, int, Stone, boolean)}とは異なり、
   * X軸のベクトルとY軸のベクトルを渡しその一方通行にのみひっくり返す.<br />
   * X軸とY軸のベクトルの値は-1~1の間である.<br />
   * 最終的にひっくり返した個数を返す.<br />
   * {@code isReverse}が{@code true}のときはひっくり返すが{@code false}のときはひっくり返さない.<br />
   * つまり{@code isReverse}が{@code false}のときは石を置いた場合いくつひっくり返すかが返ってくる.<br />
   * ただしひっくり返せない場合は-1を返し同じ色の石が隣接している(0個ひっくり返す)場合は0を返す.
   * @param x X軸の値
   * @param y Y軸の値
   * @param vx X軸のベクトル
   * @param vy Y軸のベクトル
   * @param stone 置く石
   * @param isReverse ひっくり返すかどうかのフラグ
   * @return 更新した石の数
   */
  private int reverse(int x, int y, int vx, int vy, Stone stone, boolean isReverse) {
    if(isOutOfRange(x, y)) return -1;
    if(vx == 0 && vy == 0) return -1;
    if(board[y][x] == Stone.NONE) return -1;
    if(board[y][x] == stone) return 0;
    int depth = reverse(x + vx, y + vy, vx, vy, stone, isReverse);
    if(depth > -1) {
      if(isReverse) board[y][x] = stone;
      return depth + 1;
    }
    return depth;
  }

  /**
   * 石の色を指定しどの座標に置くことができるかの一覧を返す.<br />
   * どこにも置けない場合は空の{@linkplain java.util.Set}を返す.
   * @param stone 置く石
   * @return 置くことのできる場所の一覧
   */
  private Set<Coordinate> getInnerCanPutArea(Stone stone) {
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

  /**
   * X軸とY軸を保存する座標クラス.<br />
   * {@linkplain Object#hashCode()}と{@linkplain Object#equals(Object)}をオーバーライドしているため、
   * X軸とY軸が同じであれば同一とみなす.
   */
  private static class Coordinate {

    /** X軸. */
    private final int x;

    /** Y軸. */
    private final int y;

    /**
     * コンストラクタ.
     * @param x X軸の値
     * @param y Y軸の値
     */
    public Coordinate(int x, int y) {
      this.x = x;
      this.y = y;
    }

    /**
     * X軸の値を取得する.
     * @return X軸の値
     */
    public int getX() {
      return x;
    }

    /**
     * Y軸の値を取得する.
     * @return Y軸の値
     */
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
