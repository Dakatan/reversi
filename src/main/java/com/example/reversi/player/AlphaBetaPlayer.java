package com.example.reversi.player;

import com.example.reversi.Board;
import com.example.reversi.BoardImpl;
import com.example.reversi.Stone;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class AlphaBetaPlayer extends AbstractBasePlayer {

  private static final int HEIGHT = 8;
  private static final int WIDTH = 8;
  private final int nodeDepth;

//  private final int[][] scoreBoard = {
//          { 30, -12,  0, -1, -1,  0, -12,  30},
//          {-12, -15, -3, -3, -3, -3, -15, -12},
//          {  0,  -3,  0, -1, -1,  0,  -3,   0},
//          { -1,  -3, -1, -1, -1, -1,  -3,  -1},
//          { -1,  -3, -1, -1, -1, -1,  -3,  -1},
//          {  0,  -3,  0, -1, -1,  0,  -3,   0},
//          {-12, -15, -3, -3, -3, -3, -15, -12},
//          { 30, -12,  0, -1, -1,  0, -12,  30}
//  };

  private final int[][] scoreBoard = {
          { 9999, -20,  20,  5,  5,  20, -20,  9999},
          { -20, -40,  -5, -5, -5,  -5, -40,  -20},
          {  20,  -5,  15,  3,  3,  15,  -5,   20},
          {   5,  -5,   3,  3,  3,   3,  -5,    5},
          {   5,  -5,   3,  3,  3,   3,  -5,    5},
          {  20,  -5,  15,  3,  3,  15,  -5,   20},
          { -20, -40,  -5, -5, -5,  -5, -40,  -20},
          { 9999, -20,  20,  5,  5,  20, -20,  9999},
  };

  public AlphaBetaPlayer(Stone stone, Board board) {
    this(stone, board, 5);
  }

  public AlphaBetaPlayer(Stone stone, Board board, int depth) {
    super(stone, board);
    this.nodeDepth = depth;
  }

  @Override
  protected int[] getArea() {
    if(nodeDepth <= 0) {
      Set<int[]> set = board.getCanPutArea(stone);
      int[][] array = set.toArray(new int[set.size()][2]);
      Random random = new Random();
      return array[random.nextInt(array.length)];
    }
    BoardNode node = new BoardNode(nodeDepth, stone, board);
    int calculated = alphaBeta(node, Integer.MIN_VALUE, Integer.MAX_VALUE);
    for(BoardNode nextNode : node.getNextNodes()) {
      if(nextNode.getEval() == calculated) return new int[]{nextNode.getX(), nextNode.getY()};
    }
    return new int[]{-1, -1};
  }

  private int alphaBeta(BoardNode node, int alpha, int beta) {
    if(node.getNextNodes().size() == 0) return node.calculateScore(stone);
    if(node.getTurn() == stone) {
      for(BoardNode nextNode : node.getNextNodes()) {
        alpha = Math.max(alpha, alphaBeta(nextNode, alpha, beta));
        if(alpha >= beta) break;
      }
      for(BoardNode nextNode : node.getNextNodes()) {
        nextNode.setEval(alpha);
      }
      return alpha;
    } else {
      for(BoardNode nextNode : node.getNextNodes()) {
        beta = Math.min(beta, alphaBeta(nextNode, alpha, beta));
        if(alpha >= beta) break;
      }
      for(BoardNode nextNode : node.getNextNodes()) {
        nextNode.setEval(beta);
      }
      return beta;
    }
  }

  private class BoardNode {
    private List<BoardNode> nodes = new ArrayList<>();
    private Board board;
    private Stone turn;
    private int x;
    private int y;
    private int eval;

    public BoardNode(int depth, Stone stone, Board board) {
      this(depth, -1, -1, stone, board, true);
    }

    private BoardNode(int depth, int x, int y, Stone turn, Board board, boolean isFirst) {
      this.board = board;
      this.x = x;
      this.y = y;
      this.turn = turn;
      Stone nextTurn = isFirst ? turn : nextTurn(turn);

      if(depth <= 0) return;
      Set<int[]> areas = this.board.getCanPutArea(nextTurn);

      for(int[] area : areas) {
        Board nextBoard = new BoardImpl(board);
        nextBoard.put(area[0], area[1], nextTurn);
        BoardNode nextNode = new BoardNode(depth - 1, area[0], area[1], nextTurn, nextBoard, false);
        nodes.add(nextNode);
      }
    }

    public List<BoardNode> getNextNodes() {
      return this.nodes;
    }

    public int calculateScore(Stone stone) {
      int score = 0;
      for(int i = 1; i <= HEIGHT; i++) {
        for(int j = 1; j <= WIDTH; j++) {
          if(this.board.get(j, i) != stone) continue;
          score += scoreBoard[i - 1][j - 1];
        }
      }
      return score;
    }

    public int getX() {
      return this.x;
    }

    public int getY() {
      return this.y;
    }

    public Stone getTurn() {
      return this.turn;
    }

    public Board getBoard() {
      return this.board;
    }

    public int getEval() {
      return eval;
    }

    public void setEval(int eval) {
      this.eval = eval;
    }

    private Stone nextTurn(Stone stone) {
      Stone nextStone;
      if(stone == Stone.WHITE) {
        nextStone = Stone.BLACK;
      } else {
        nextStone = Stone.WHITE;
      }
      if(!this.board.canPut(nextStone)) {
        if(stone == Stone.WHITE) {
          nextStone = Stone.BLACK;
        } else {
          nextStone = Stone.WHITE;
        }
      }
      return nextStone;
    }
  }
}
