package com.example.reversi.player.evaluation;

import com.example.reversi.Board;
import com.example.reversi.Stone;

public class StaticReversiStrategy implements ReversiStrategy {

  @Override
  public int eval(Board board, Stone stone) {
    int[][] scoreBoard = getScoreBoardBase();
    int score = 0;
    for(int i = 0; i < 8; i++) {
      for(int j = 0; j < 8; j++) {
        if(board.get(i + 1, j + 1) == stone) score += scoreBoard[j][i];
        else if(board.get(i + 1, j + 1) == stone.reverse()) score -= scoreBoard[j][i];
      }
    }
    return score;
  }
}
