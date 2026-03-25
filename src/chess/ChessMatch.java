package chess;

import chess.pieces.*;

import boardgame.Position;
import boardgame.Board;
import boardgame.Piece;

import chess.exceptions.ChessException;

public class ChessMatch {

  private Board board;

  public ChessMatch() {
    board = new Board(8, 8);
    initialSetup();
  }
  public ChessPiece[][] getPieces(){
    ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];

    for (int i = 0; i < board.getRows(); i++) {
      for (int j = 0; j < board.getColumns(); j++) {
        mat[i][j] = (ChessPiece) board.piece(i, j);
      }
    }
    return mat;
  }
  public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
    Position source = sourcePosition.toPosition();
    Position target = targetPosition.toPosition();

    validateSourcePosition(source);
  
    validateTargetPosition(source, target);
    
    Piece capturedPiece = makeMove(source, target);
    
    return (ChessPiece) capturedPiece;
  }

  private void validateTargetPosition(Position source, Position target) {
    if(!board.piece(source).possibleMove(target)) {
      throw new ChessException("The chosen piece can't move to target position");
    }
  }
  private Piece makeMove(Position source, Position target) {
    Piece piece = board.removePiece(source);
    Piece capturedPiece = board.removePiece(target);
    board.placePiece(piece, target);
    
    return capturedPiece;
  }

  private void validateSourcePosition(Position position) {
    if (!board.thereIsAPiece(position)) {
      throw new ChessException("There is no piece on source position");
    }
    if(!board.piece(position).isThereAnyPossibleMove()) {
      throw new ChessException("There is no possible moves for the chosen piece");
    }
  }

  private void placeNewPiece(char column, int row, ChessPiece piece) {
    board.placePiece(piece, new ChessPosition(column, row).toPosition());
  }

  private void initialSetup() {

    char var = 'a';

    for(int i = 0; i < 8; i++) {
      placeNewPiece(var, 7, new Pawn(board, Color.BLACK));
      placeNewPiece(var, 2, new Pawn(board, Color.WHITE));
      var++;
    }
    
    placeNewPiece('a',8, new Rook(board, Color.BLACK));
    placeNewPiece('b',8, new Knight(board, Color.BLACK));
    placeNewPiece('c',8, new Bishop(board, Color.BLACK));
    placeNewPiece('e',8, new King(board, Color.BLACK));
    placeNewPiece('d',8, new Queen(board, Color.BLACK));
    placeNewPiece('f',8, new Bishop(board, Color.BLACK));
    placeNewPiece('g',8, new Knight(board, Color.BLACK));
    placeNewPiece('h',8, new Rook(board, Color.BLACK));

    placeNewPiece('a',1, new Rook(board, Color.WHITE));
    placeNewPiece('b',1, new Knight(board, Color.WHITE));
    placeNewPiece('c',1, new Bishop(board, Color.WHITE));
    placeNewPiece('e',1, new King(board, Color.WHITE));
    placeNewPiece('d',1, new Queen(board, Color.WHITE));
    placeNewPiece('f',1, new Bishop(board, Color.WHITE));
    placeNewPiece('g',1, new Knight(board, Color.WHITE));
    placeNewPiece('h',1, new Rook(board, Color.WHITE));
  } 
}