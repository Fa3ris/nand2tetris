package org.nand2tetris.parser.utils;

import org.nand2tetris.tokenizer.Keyword;
import org.nand2tetris.tokenizer.Symbol;

public abstract class TagNames {

  public static final String varDec = "varDec";
  public static final String subroutineDec = "subroutineDec";
  public static final String parameterList = "parameterList";
  public static final String subroutineBody = "subroutineBody";

  public static final String classVarDec = "classVarDec";
  public static final String keyword = "keyword";
  public static final String identifier = "identifier";
  public static final String symbol = "symbol";

  public static final String letStatement = "letStatement";
  public static final String termTag = "term";
  public static final String expressionTag = "expression";

  public static final String classTag = Keyword.CLASS;
  public static final String varTag = Keyword.VAR;
  public static final String letTag = Keyword.LET;
  public static final String equalTag = Symbol.EQ;
  public static final String returnTag = Keyword.RETURN;
  public static final String doTag = Keyword.DO;
  public static final String dot = Symbol.DOT;
  public static final String ifTag = Keyword.IF;
  public static final String elseTag = Keyword.ELSE;

  public static final String statements = "statements";
  public static final String returnStatement = "returnStatement";
  public static final String doStatement = "doStatement";
  public static final String expressionListTag = "expressionList";
  public static final String ifStatement = "ifStatement";
  public static final String whileStatement = "whileStatement";
  public static final String whileTag = Keyword.WHILE;
  public static final String stringConstant = "stringConstant";
  public static final String integerConstant = "integerConstant";
}
