package com.example.reversi.helper;

import com.example.reversi.Board;
import com.example.reversi.BoardImpl;
import com.example.reversi.Stone;

import java.util.*;

/**
 * オセロの棋譜を読み込み実行するテスト用ヘルパークラス.
 */
public class GameRecordExecutor {

  /** 棋譜のX軸を数値にマッピングする{@linkplain java.util.Map}オブジェクト. */
  private static Map<Character, Integer> mapping;

  static {
    mapping = new HashMap<>();
    char[] chars = "ABCDEFGH".toCharArray();
    for(int j = 0; j < chars.length; j++) {
      mapping.put(Character.toUpperCase(chars[j]), j + 1);
      mapping.put(Character.toLowerCase(chars[j]), j + 1);
    }
  }

  /**
   * 先攻を黒として棋譜を実行する.
   * @param record 棋譜
   * @return 棋譜実行後のボード
   */
  public static Board executeRecord(String record) {
    return executeRecord(record, Stone.BLACK);
  }

  /**
   * 黒または白のどちらかを先攻として棋譜を実行する.
   * @param record 棋譜
   * @param first 先攻
   * @return 棋譜実行後のボード
   */
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

  /**
   * 文字列型の棋譜をコレクションに変換する.
   * @param record 棋譜
   * @return Listにパースされた棋譜
   */
  private static List<int[]> parse(String record) {
    List<int[]> list = new LinkedList<>();
    for(int i = 0; i < record.length(); i += 2) {
      int x = mapping.get(record.charAt(i));
      int y = Integer.parseInt(record.substring(i + 1, i + 2));
      list.add(new int[]{x, y});
    }
    return list;
  }

  /**
   * 次のターンの{@linkplain Stone}を返す.<br>
   * {@linkplain Stone#BLACK}であれば{@linkplain Stone#WHITE}を返し、
   * {@linkplain Stone#WHITE}であれば{@linkplain Stone#BLACK}を返す.<br>
   * {@code null}や{@linkplain Stone#NONE}であれば{@linkplain Stone#BLACK}を返す.
   * @param turn ターン
   * @return 次のターン
   */
  private static Stone changeTurn(Stone turn) {
    if(turn == Stone.BLACK) {
      return Stone.WHITE;
    }
    return Stone.BLACK;
  }
}
