package com.example.reversi.helper;

import com.example.reversi.Board;
import com.example.reversi.BoardImpl;
import com.example.reversi.Stone;

import java.util.*;

public class GameRecordExecutor {
  private static Map<Character, Integer> mapping;

  static {
    mapping = new HashMap<>();
    char[] chars = "ABCDEFGH".toCharArray();
    for(int j = 0; j < chars.length; j++) {
      mapping.put(Character.toUpperCase(chars[j]), j + 1);
      mapping.put(Character.toLowerCase(chars[j]), j + 1);
    }
  }

  public static Board executeRecord(String record) {
    return executeRecord(record, Stone.BLACK);
  }

  public static Board executeRecord(String record, Stone first) {
    BoardImpl board = new BoardImpl();
    List<int[]> list = parse(record);

    Stone turn = first;
    if(turn == null || turn == Stone.NONE) turn = Stone.BLACK;

    for(int[] ints : list) {
      if(board.isFinish()) break;
      if(!board.canPut(turn)) turn = changeTurn(turn);

      board.put(ints[0], ints[1], turn);

      turn = changeTurn(turn);
    }
    return board;
  }

  private static List<int[]> parse(String record) {
    List<int[]> list = new LinkedList<>();
    for(int i = 0; i < record.length(); i += 2) {
      int x = mapping.get(record.charAt(i));
      int y = Integer.parseInt(record.substring(i + 1, i + 2));
      list.add(new int[]{x - 1, y - 1});
    }
    return list;
  }

  private static Stone changeTurn(Stone turn) {
    if(turn == Stone.BLACK) {
      return Stone.WHITE;
    }
    return Stone.BLACK;
  }
}
