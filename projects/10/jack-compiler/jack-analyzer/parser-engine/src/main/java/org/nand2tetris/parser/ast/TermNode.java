package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.formatTag;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.nand2tetris.parser.utils.TagNames;
import org.nand2tetris.tokenizer.Token;

public class TermNode extends AbstractNode {

  private Token varName;
  private Token keywordConstant;
  private Token subroutineName;
  private Token stringConstant;
  private Token integerConstant;
  private Node expressionListNode;
  private Node indexExpression;
  private Node parenExpression;
  private Node unaryTerm;
  private Token unaryOp;

  public void addVarName(Token token) {
    varName = token;
  }

  @Override
  protected String parentTag() {
    return TagNames.termTag;
  }

  @Override
  protected List<String> childrenTags() {
    String tag = null;
    if (varName != null) {
      tag = formatTag(varName);
    } else if (keywordConstant != null) {
      tag = formatTag(keywordConstant);
    }
    return Collections.singletonList(tag);
  }

  public void addKeywordConstant(Token token) {
    keywordConstant = token;
  }

  public void addSubroutineName(Token token) {
    subroutineName = token;
  }

  public void addExpressionList(Node node) {
    this.expressionListNode = node;
  }

  public void addStringConstant(Token token) {
    stringConstant = token;
  }

  public void addIndexExpression(Node node) {
    indexExpression = node;
  }

  public void addIntegerConstant(Token token) {
    integerConstant = token;
  }

  public void addParenExpression(Node expression) {
    parenExpression = expression;
  }

  public void addUnaryOp(Token token) {
    unaryOp = token;
  }

  public void addUnaryTerm(Node term) {
    unaryTerm = term;
  }
}
