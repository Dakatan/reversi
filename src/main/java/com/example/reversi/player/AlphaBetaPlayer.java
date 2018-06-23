package com.example.reversi.player;

import com.example.reversi.Board;
import com.example.reversi.Stone;
import com.example.reversi.player.evaluation.ReversiStrategy;
import com.example.reversi.player.evaluation.StaticReversiStrategy;

import java.util.*;

public class AlphaBetaPlayer implements Player {

  private Random random = new Random();

  private int searchDepth = 3;

  private ReversiStrategy strategy = new StaticReversiStrategy();

  public AlphaBetaPlayer() {}

  public AlphaBetaPlayer(int searchDepth) {
    this.searchDepth = searchDepth;
  }

  public AlphaBetaPlayer(int searchDepth, ReversiStrategy strategy) {
    this.searchDepth = searchDepth;
    this.strategy = strategy;
  }

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
    if(depth <= 0) return strategy.eval(board, me);
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
      return alpha;
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
      return beta;
    }
  }
}
