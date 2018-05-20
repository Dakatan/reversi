package com.example.reversi;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import com.example.reversi.helper.GameRecordExecutor;
import org.junit.Test;

public class BoardImplTest {

  @Test
  public void test01() {
    String record = "F5D6C4D3C5F6E6B6B5A6B4G6G5F4E3F3F2D2C2B3C6C7D7E7G7F7E8D8C8B8B7A7A8G8F8H7H6H4H5H8H3E2C3G4G3G2H2G1H1B2A5A4A3A2F1E1D1C1B1A1";
    Board board = GameRecordExecutor.executeRecord(record);
    assertTest(board, 26, 38, 0);
  }

  @Test
  public void test02() {
    String record = "D3E3F3C3C4C5D6G3F5G5E6E7F4G4";
    Board board = GameRecordExecutor.executeRecord(record);
    assertTest(board, 18, 0, 46);
  }

  @Test
  public void test03() {
    String record = "E6F6D3C5C6D6G7F4G4D2C3B4C4B7E7F3A4H8F2B3D1E3A3G3A8E2G5D7C8B2A1F5C7B8H4D8E8A7A6B5A5B6C2A2B1C1E1F7F8H3G6G2H1G1F1H6G8H7H5H2";
    Board board = GameRecordExecutor.executeRecord(record);
    assertTest(board, 26, 38, 0);
  }

  @Test
  public void test04() {
    String record = "F5D6C6F6D3C3E6B6C4F4B2E3F3C5D7E2C7G4A5C2G3B5A4A6G5B4D2G6D1A3B3A1B7H5H6H4A2F2A7B1C1F1H3C8B8A8E1G2G1H1H2E7D8E8F8F7G8H8G7H7";
    Board board = GameRecordExecutor.executeRecord(record);
    assertTest(board, 44, 20, 0);
  }

  @Test
  public void test05() {
    String record = "C4E3F6E6F5C5D3G6D7G5D6E7C6C8D8E8F4C7F7G4F8G8H5F3H6C2H3H4G3F2D2E2C1D1E1F1G1B3B4B6B7C3B5G2H1H2G7A7A6A8B8A3B1B2A1A5H8H7A4A2";
    Board board = GameRecordExecutor.executeRecord(record);
    assertTest(board, 32, 32, 0);
  }

  @Test
  public void test06() {
    String record = "F5F6E6F4E3C5G5F3G4G6G3D6F7H5E7D7C3H3H4E2C4H6F2C6D3F8E8D8B5F1G2B6C8B8C7A5B4G8A4A3A6A7B3D2H2A2H7H1G7H8G1B7A8A1B2B1C1C2E1D1";
    Board board = GameRecordExecutor.executeRecord(record);
    assertTest(board, 34, 30, 0);
  }

  @Test
  public void test07() {
    String record = "F5D6C3D3C4F4F6F3E6E7C6G5D2D7F8F7E8C5E3C8C7B4B5E2G4C1H6B3A3G6F2H3H4D8B8H5H2G3F1D1H7A6A5E1B1A4A7B6C2B2A2G7G1G2H1B7A8A1G8H8";
    Board board = GameRecordExecutor.executeRecord(record);
    assertTest(board, 16, 48, 0);
  }

  @Test
  public void test08() {
    String record = "C4C3D3E3D2B4F4F3D6E6F2C6C5B5A4A5E2D1A6B6B3C2F5A2F7F1E1E7A3A7F6G5C7F8E8G3G4G6C1H4G1B2G8C8H3H2H6H5H1G2B7D8B8D7A8H7A1G7B1H8";
    Board board = GameRecordExecutor.executeRecord(record);
    assertTest(board, 15, 49, 0);
  }

  @Test
  public void test09() {
    String record = "F5D6C3D3C4F4F6B4C2F3E3E2C6E6C5D2F1B3B5E1G4G3D1F2F7H5G5E7C7G6A4E8D7B6A5A3F8D8C8A6B2G7G8A1A2B1C1G1H4B8A8H6A7H3B7H8H7H2G2H1";
    Board board = GameRecordExecutor.executeRecord(record);
    assertTest(board, 19, 45, 0);
  }

  @Test
  public void test10() {
    String record = "F5D6C3D3C4F4C5B3C2E6C6B4B5D2E3A6C1D7A5F3C7F6A4A3F2B6G4E2G6E1G3H6D1B1G5D8B7A8A7H5C8B8G2H1H4H3B2F1E8E7G1F8F7A1A2G7H8G8H7H2";
    Board board = GameRecordExecutor.executeRecord(record);
    assertTest(board, 21, 43, 0);
  }

  @Test
  public void test11() {
    String record = "D3C5F6F5E6E3C3F3C4B4B5D2A3D6C6B3C2E7F7D7F4G4D1G3G6G5E2A5F2A4A6C7B6F1F8E8H6C1H4E1H3G2A2G8H1B2A1B1G1H2C8D8H8H7G7A7A8H5B7B8";
    Board board = GameRecordExecutor.executeRecord(record);
    assertTest(board,  34, 30, 0);
  }

  private void assertTest(Board target, int white, int black, int none) {
    assertThat(target.getCount(Stone.WHITE), is(equalTo(white)));
    assertThat(target.getCount(Stone.BLACK), is(equalTo(black)));
    assertThat(target.getCount(Stone.NONE), is(equalTo(none)));
  }
}