package application;

import java.util.Scanner;
import java.util.stream.Collectors;

import boardgame.Board;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

import chess.ChessPosition;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class UI {

  public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

  public static void clearScreen() {
    try {
			if (System.getProperty("os.name").contains("Windows"))
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			else
				Runtime.getRuntime().exec("clear");
    } 
		catch (Exception e) {
			e.printStackTrace();
    }
  }
  public static ChessPosition readChessPosition(Scanner sc) {

    try {
      String s = sc.nextLine();
      char column = s.charAt(0);
      int row = Integer.parseInt(s.substring(1));
      return new ChessPosition(column, row);
    } catch (RuntimeException e) {
      throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8.");
    }
  }
	public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
		printBoard(chessMatch.getPieces());
		
		if(chessMatch.getCheck()){
			System.out.println(ANSI_YELLOW +"CHECK!"+ANSI_RESET);
		}
		
		System.out.println();
		System.out.println("Turn: " + chessMatch.getTurn());
		System.out.println("Current player: " + chessMatch.getCurrentPlayer());

		System.out.println();
		printCapturedPiece(captured);
	}
  public static void printBoard(ChessPiece[][] pieces) {
		int color = 0;
    for(int i =0; i < pieces.length; i++) {
      System.out.print(ANSI_RED +(8 - i) + " "+ANSI_RESET);
      for(int j = 0; j < pieces.length; j++) {
				initialColorBorder(color++);
        printPiece(pieces[i][j], false);
      }
      System.out.println();
			color--;
    }
    System.out.println(ANSI_RED + "  a b c d e f g h" + ANSI_RESET);
  }
	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
    for(int i =0; i < pieces.length; i++) {
      System.out.print((8 - i) + " ");
      for(int j = 0; j < pieces.length; j++) {
        printPiece(pieces[i][j], possibleMoves[i][j]);
      }
      System.out.println();
    }
    System.out.println("  a b c d e f g h");
  }
	private static void initialColorBorder(int i){
		if(i%2 == 0){
			System.out.print(ANSI_BLACK_BACKGROUND);
		}
	}

  private static void printPiece(ChessPiece piece, boolean background) {
		if (background){
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		
		if (piece == null) {
			System.out.print("-" + ANSI_RESET);
		} else {
			if (piece.getColor() == Color.WHITE) {
				System.out.print(ANSI_WHITE + piece + ANSI_RESET);
			} else {
				System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
			}
		}
		System.out.print(" ");
	}
	private static void printCapturedPiece(List<ChessPiece> captured) {
		List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());

		List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());

		System.out.println("Captured pieces:");
		System.out.print("White: ");
		System.out.println(ANSI_WHITE + Arrays.toString(white.toArray())+ANSI_RESET);
		System.out.print("Black: ");
		System.out.println(ANSI_YELLOW + Arrays.toString(black.toArray())+ANSI_RESET);
	}
}
