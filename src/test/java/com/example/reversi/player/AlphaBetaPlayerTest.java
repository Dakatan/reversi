package com.example.reversi.player;

import com.example.reversi.Board;
import com.example.reversi.BoardImpl;
import com.example.reversi.Stone;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class AlphaBetaPlayerTest {
  private Board board;
  private AlphaBetaPlayer player1;
  private AlphaBetaPlayer player2;
  private int black;
  private int white;
  private int draw;

  public void setUp() {
    board = new BoardImpl();
    player1 = new AlphaBetaPlayer(Stone.BLACK, board, 1);
    player2 = new AlphaBetaPlayer(Stone.WHITE, board, 2);
  }

  @Test
  public void test() {
    for (int i = 0; i < 100; i++) {
      setUp();
      test01();
    }
    System.out.println("BLACK: " + black + ", WHITE: " + white + ", DRAW:" + draw);
  }

  public void test01() {
    for(int i = 0; i < 100; i++) {
      if(board.isFinish()) break;
      if(board.canPut(Stone.BLACK)) {
        int[] arg1 = player1.getArea();
        board.put(arg1[0], arg1[1], Stone.BLACK);
      }
      if(board.isFinish()) break;

      if(board.canPut(Stone.WHITE)) {
        int[] arg2 = player2.getArea();
        board.put(arg2[0], arg2[1], Stone.WHITE);
      }
    }
    if(board.getCount(Stone.WHITE) > board.getCount(Stone.BLACK)) {
      white++;
    } else if(board.getCount(Stone.BLACK) > board.getCount(Stone.WHITE)) {
      black++;
    } else {
      draw++;
    }
    System.out.println("BLACK: " + black + ", WHITE: " + white + ", DRAW:" + draw);
  }
}
