package com.example.reversi;

public class BitBoard implements Board {

  private long black = 0x0000000810000000L;
  private long white = 0x0000001008000000L;

  @Override
  public int put(int x, int y, Stone stone) {
    return put(x, y, stone, true);
  }

  @Override
  public int put(int x, int y, Stone stone, boolean isReverse) {
    long move = convert(x, y);
    long rev;
    long reversedBlack;
    long reversedWhite;
    int result;
    if(stone == Stone.BLACK) {
      rev = rev(black, white, move);
      reversedBlack = black ^ move | rev;
      reversedWhite = white ^ rev;
      result = Long.bitCount(reversedBlack ^ black);
    } else {
      rev = rev(white, black, move);
      reversedBlack = black ^ rev;
      reversedWhite = white ^ move | rev;
      result = Long.bitCount(reversedWhite ^ white);
    }
    if(result == 1) {
      result = 0;
    }
    if(isReverse && result > 0) {
      white = reversedWhite;
      black = reversedBlack;
    }
    return result;
  }

  @Override
  public boolean isFinish() {
    return !canPut(Stone.BLACK) && !canPut(Stone.WHITE);
  }

  @Override
  public Stone get(int x, int y) {
    long move = convert(x, y);
    if((move & black) != 0) return Stone.BLACK;
    if((move & white) != 0) return Stone.WHITE;
    return Stone.NONE;
  }

  @Override
  public int count(Stone stone) {
    if(stone == Stone.BLACK) {
      return Long.bitCount(black);
    } else {
      return Long.bitCount(white);
    }
  }

  @Override
  public boolean canPut(Stone stone) {
    long me;
    long you;
    if(stone == Stone.BLACK) {
      me = black;
      you = white;
    } else if(stone == Stone.WHITE) {
      me = white;
      you = black;
    } else {
      return false;
    }

    long vertical = you & 0x7e7e7e7e7e7e7e7eL;
    long parallel = you & 0x00FFFFFFFFFFFF00L;
    long diagonal = you & 0x007e7e7e7e7e7e00L;

    long blank = ~(black | white);

    long tmp;
    long result = 0;

    tmp     = vertical & (me  << 1);
    tmp    |= vertical & (tmp << 1);
    tmp    |= vertical & (tmp << 1);
    tmp    |= vertical & (tmp << 1);
    tmp    |= vertical & (tmp << 1);
    tmp    |= vertical & (tmp << 1);
    result |= blank    & (tmp << 1);

    tmp     = vertical & (me  >>> 1);
    tmp    |= vertical & (tmp >>> 1);
    tmp    |= vertical & (tmp >>> 1);
    tmp    |= vertical & (tmp >>> 1);
    tmp    |= vertical & (tmp >>> 1);
    tmp    |= vertical & (tmp >>> 1);
    result |= blank    & (tmp >>> 1);

    tmp     = parallel & (me  << 8);
    tmp    |= parallel & (tmp << 8);
    tmp    |= parallel & (tmp << 8);
    tmp    |= parallel & (tmp << 8);
    tmp    |= parallel & (tmp << 8);
    tmp    |= parallel & (tmp << 8);
    result |= blank    & (tmp << 8);

    tmp     = parallel & (me  >>> 8);
    tmp    |= parallel & (tmp >>> 8);
    tmp    |= parallel & (tmp >>> 8);
    tmp    |= parallel & (tmp >>> 8);
    tmp    |= parallel & (tmp >>> 8);
    tmp    |= parallel & (tmp >>> 8);
    result |= blank    & (tmp >>> 8);

    tmp     = diagonal & (me  << 7);
    tmp    |= diagonal & (tmp << 7);
    tmp    |= diagonal & (tmp << 7);
    tmp    |= diagonal & (tmp << 7);
    tmp    |= diagonal & (tmp << 7);
    tmp    |= diagonal & (tmp << 7);
    result |= blank    & (tmp << 7);

    tmp     = diagonal & (me  << 9);
    tmp    |= diagonal & (tmp << 9);
    tmp    |= diagonal & (tmp << 9);
    tmp    |= diagonal & (tmp << 9);
    tmp    |= diagonal & (tmp << 9);
    tmp    |= diagonal & (tmp << 9);
    result |= blank    & (tmp << 9);

    tmp     = diagonal & (me  >>> 9);
    tmp    |= diagonal & (tmp >>> 9);
    tmp    |= diagonal & (tmp >>> 9);
    tmp    |= diagonal & (tmp >>> 9);
    tmp    |= diagonal & (tmp >>> 9);
    tmp    |= diagonal & (tmp >>> 9);
    result |= blank    & (tmp >>> 9);

    tmp     = diagonal & (me  >>> 7);
    tmp    |= diagonal & (tmp >>> 7);
    tmp    |= diagonal & (tmp >>> 7);
    tmp    |= diagonal & (tmp >>> 7);
    tmp    |= diagonal & (tmp >>> 7);
    tmp    |= diagonal & (tmp >>> 7);
    result |= blank    & (tmp >>> 7);

    return Long.bitCount(result) != 0;
  }

  @Override
  public Board clone() {
    Board clone;
    try {
      clone = (Board) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new InternalError(e);
    }
    return clone;
  }

  @Override
  public void print() {
    for(int i = 1; i <= 8; i++) {
      for(int j = 1; j <= 8; j++) {
        System.out.print(get(j, i).asString());
      }
      System.out.println();
    }
    System.out.println();
  }

  public long convert(int x, int y) {
    long mask = 0x8000000000000000L;
    mask = mask >>> (x - 1);
    mask = mask >>> ((y - 1) * 8);
    return mask;
  }

  private long transfer(int type, long mask) {
    switch (type) {
      case 0: return  (mask << 8) & 0xffffffffffffff00L; // 上
      case 1: return  (mask << 7) & 0x7f7f7f7f7f7f7f00L; // 右上
      case 2: return (mask >>> 1) & 0x7f7f7f7f7f7f7f7fL; // 右
      case 3: return (mask >>> 9) & 0x007f7f7f7f7f7f7fL; // 右下
      case 4: return (mask >>> 8) & 0x00ffffffffffffffL; // 下
      case 5: return (mask >>> 7) & 0x00fefefefefefefeL; // 左下
      case 6: return  (mask << 1) & 0xfefefefefefefefeL; // 左
      case 7: return  (mask << 9) & 0xfefefefefefefe00L; // 左上
      default: return 0;
    }
  }

  private long rev(long player1, long player2, long move) {
    long rev = 0;
    if(((player1 | player2) & move) != 0) return rev;
    for(int i = 0; i < 8; i++) {
      long mask = transfer(i, move);
      long memo = 0;
      while (mask != 0 && (mask & player2) != 0) {
        memo |= mask;
        mask = transfer(i, mask);
      }
      if((mask & player1) != 0) {
        rev |= memo;
      }
    }
    return rev;
  }
}
