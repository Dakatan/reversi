package com.example.reversi.player.evaluation;

import com.example.reversi.Board;
import com.example.reversi.Stone;

public interface ReversiStrategy {

  default int eval(Board board, Stone stone) {
    int score = 0;
    int[][] scoreBoard = getScoreBoard(board, stone);
    for(int i = 0; i < 8; i++) {
      for(int j = 0; j < 8; j++) {
        if(board.get(i + 1, j + 1) == stone) {
          score += scoreBoard[j][i];
        } else if(board.get(i + 1, j + 1) == stone.reverse()) {
          score -= scoreBoard[j][i];
        }
        int count = board.count(Stone.BLACK) + board.count(Stone.WHITE);
        if(board.get(i + 1, j + 1) == stone) {
          score += count < 20 ? 10 : -10;
        }
      }
    }
    return score;
  }

  int[][] getScoreBoard(Board board, Stone stone);
}
