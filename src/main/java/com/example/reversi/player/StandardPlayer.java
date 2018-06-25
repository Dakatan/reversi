package com.example.reversi.player;

import com.example.reversi.Board;
import com.example.reversi.Stone;

import java.util.SortedSet;
import java.util.TreeSet;

public class StandardPlayer implements Player {

  @Override
  public Position play(Board board, Stone stone) {
    if (board.canPut(stone)) throw new IllegalArgumentException("this board cannot put.");

    SortedSet<DTO> set = new TreeSet<>();
    for (int i = 0; i <= 8; i++) {
      for (int j = 0; j <= 8; j++) {
        int count = board.put(i, j, stone, false);
        if (count <= 0) {
          continue;
        }
        DTO dto = new DTO(i, j, count);
        set.add(dto);
      }
    }

    DTO first = set.first();
    return Position.at(first.getX(), first.getY());
  }

  /**
   * X、Y、ひっくり返すカウント、を格納できるDTOクラス.<br>
   * 大きい順に並べ替えたいので大小関係は逆に見える.
   */
  private static class DTO implements Comparable<DTO> {

    /**
     * X座標.
     */
    private final int x;

    /**
     * Y座標.
     */
    private final int y;

    /**
     * ひっくり返すカウント.
     */
    private final int value;

    /**
     * コンストラクタ.
     *
     * @param x     X軸
     * @param y     Y軸
     * @param value ひっくり返すカウント
     */
    public DTO(int x, int y, int value) {
      if (value <= 0) throw new IllegalArgumentException("value must be over 0.");
      this.x = x;
      this.y = y;
      this.value = value;
    }

    /**
     * X軸の値を返却する.
     *
     * @return X軸
     */
    public int getX() {
      return x;
    }


    /**
     * Y軸の値を返却する.
     *
     * @return Y軸
     */
    public int getY() {
      return y;
    }

    /**
     * ひっくり返すカウントを返却する.
     *
     * @return ひっくり返すカウント
     */
    public int getValue() {
      return value;
    }

    /**
     * 角から1マス隣のマスの場合{@code true}を返却し、それ以外の場合{@code false}を返却する.
     *
     * @return 角から1マス隣かどうか
     */
    public boolean isNearCorner() {
      if ((x == 2 && y == 1) || (x == 2 && y == 2) || (x == 1 && y == 2)) {
        return true;
      }
      if ((x == 2 && y == 8) || (x == 2 && y == 7) || (x == 1 && y == 7)) {
        return true;
      }
      if ((x == 7 && y == 1) || (x == 7 && y == 2) || (x == 8 && y == 2)) {
        return true;
      }
      if ((x == 7 && y == 8) || (x == 7 && y == 7) || (x == 8 && y == 7)) {
        return true;
      }
      return false;
    }

    /**
     * 角のマスの場合{@code true}を返却し、それ以外の場合{@code false}を返却する.
     *
     * @return 角のマスかどうか
     */
    public boolean isCorner() {
      if ((x == 1 && y == 1) || (x == 1 && y == 8) || (x == 8 && y == 1) || (x == 8 && y == 8)) {
        return true;
      }
      return false;
    }

    @Override
    public int compareTo(DTO dto) {
      if (isCorner() && dto.isCorner()) {
        return dto.getValue() - value;
      } else if (isCorner() && !dto.isCorner()) {
        return -1;
      } else if (!isCorner() && dto.isCorner()) {
        return 1;
      } else if (isNearCorner() && dto.isNearCorner()) {
        return dto.getValue() - value;
      } else if (isNearCorner() && !dto.isNearCorner()) {
        return 1;
      } else if (!isNearCorner() && dto.isNearCorner()) {
        return -1;
      } else {
        return dto.getValue() - value;
      }
    }
  }
}
