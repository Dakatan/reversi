package com.example.reversi.player;

import com.example.reversi.BitBoard;
import com.example.reversi.Board;
import com.example.reversi.Stone;
import com.example.reversi.player.evaluation.DynamicReversiStrategy;
import org.junit.Test;

public class PlayerTest {
  private Board board;
  private Player player1;
  private Player player2;
  private int black;
  private int white;
  private int draw;

  public void setUp() {
    board = new BitBoard();
//    board = new BoardImpl();
    player1 = new AlphaBetaPlayer(5);
//    player1 = new RandomPlayer();
    player2 = new AlphaBetaPlayer(5, new DynamicReversiStrategy());
//    player2 = new RandomPlayer();
//    player2 = new RandomPlayer();
//    player2 = new AlphaBetaPlayer(0);
  }

  @Test
  public void test() {
    long now = System.currentTimeMillis();
    for (int i = 0; i < 100; i++) {
      setUp();
      test01();
    }
    System.out.println(System.currentTimeMillis() - now);
    System.out.println("BLACK: " + black + ", WHITE: " + white + ", DRAW:" + draw);
  }

  public void test01() {
//    board.print();
    for(int i = 0; i < 100; i++) {
      if(board.isFinish()) break;
      if(board.canPut(Stone.BLACK)) {
        Player.Position pos = player1.play(board, Stone.BLACK);
        board.put(pos.x(), pos.y(), Stone.BLACK);
//        board.print();
      }
      if(board.isFinish()) break;

      if(board.canPut(Stone.WHITE)) {
        Player.Position pos = player2.play(board, Stone.WHITE);
        board.put(pos.x(), pos.y(), Stone.WHITE);
//        board.print();
      }
    }
    if(board.count(Stone.WHITE) > board.count(Stone.BLACK)) {
      white++;
    } else if(board.count(Stone.BLACK) > board.count(Stone.WHITE)) {
      black++;
    } else {
      draw++;
    }
    System.out.println("BLACK: " + black + ", WHITE: " + white + ", DRAW:" + draw);
  }
}
