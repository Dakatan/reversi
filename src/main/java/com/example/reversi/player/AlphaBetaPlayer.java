package com.example.reversi.player;

import com.example.reversi.Board;
import com.example.reversi.Stone;

import java.util.*;

public class AlphaBetaPlayer implements Player {

  private Random random = new Random();

  private int searchDepth = 3;

  public AlphaBetaPlayer() {}

  public AlphaBetaPlayer(int searchDepth) {
    this.searchDepth = searchDepth;
  }

  private final int[][] scoreBoard = {
          { 320, -20,  20,  5,  5,  20, -20,  320},
          { -20, -40,  -5, -5, -5,  -5, -40,  -20},
          {  20,  -5,  15,  3,  3,  15,  -5,   20},
          {   5,  -5,   3,  3,  3,   3,  -5,    5},
          {   5,  -5,   3,  3,  3,   3,  -5,    5},
          {  20,  -5,  15,  3,  3,  15,  -5,   20},
          { -20, -40,  -5, -5, -5,  -5, -40,  -20},
          { 320, -20,  20,  5,  5,  20, -20,  320},
  };

  @Override
  public Position play(Board board, Stone stone) {
    Board clone = board.clone();
    int max = Integer.MIN_VALUE;
    Map<Integer, List<Position>> map = new HashMap<>();
    for(int i = 1; i <= 8; i++) {
      for(int j = 1; j <= 8; j++) {
        if(clone.put(i, j, stone) > 0) {
          int tmp = alphaBeta(clone, stone, stone.reverse(), Integer.MIN_VALUE, Integer.MAX_VALUE, searchDepth);
          if(!map.containsKey(tmp)) map.put(tmp, new ArrayList<>());
          max = Math.max(tmp, max);
          map.get(tmp).add(Position.at(i, j));
          clone = board.clone();
        }
      }
    }
    List<Position> positions = map.get(max);
    if(positions.size() == 0) throw new IllegalArgumentException("this board cannot put.");
    return positions.get(random.nextInt(positions.size()));
  }

  private int alphaBeta(Board board, Stone me, Stone turn, int alpha, int beta, int depth) {
    if(depth <= 0) return eval(board, me);
    if(!board.canPut(turn)) turn = turn.reverse();
    Board clone = board.clone();
    if(turn == me) {
      for(int i = 1; i <= 8; i++) {
        for(int j = 1; j <= 8; j++) {
          if(clone.put(i, j, turn) > 0) {
            alpha = Math.max(alpha, alphaBeta(clone, me, turn.reverse(), alpha, beta, depth - 1));
            if(alpha >= beta) return alpha;
            clone = board.clone();
          }
        }
      }
    } else {
      for(int i = 1; i <= 8; i++) {
        for(int j = 1; j <= 8; j++) {
          if(clone.put(i, j, turn) > 0) {
            beta = Math.min(beta, alphaBeta(clone, me, turn.reverse(), alpha, beta, depth - 1));
            if(alpha >= beta) return beta;
            clone = board.clone();
          }
        }
      }
    }
    return eval(board, me);
  }

  private int eval(Board board, Stone stone) {
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
