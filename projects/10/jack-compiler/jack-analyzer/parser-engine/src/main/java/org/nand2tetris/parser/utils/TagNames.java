package org.nand2tetris.parser.utils;

import java.security.Key;
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
}
