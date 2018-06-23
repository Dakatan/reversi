package com.example.reversi.player.evaluation;

import com.example.reversi.Board;
import com.example.reversi.Stone;

public interface ReversiStrategy {

  int eval(Board board, Stone stone);

  default int[][] getScoreBoardBase() {
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
    return scoreBoard;
  }
}
