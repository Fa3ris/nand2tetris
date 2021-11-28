package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.closeBracketTag;
import static org.nand2tetris.parser.utils.XMLUtils.closeParenTag;
import static org.nand2tetris.parser.utils.XMLUtils.dotTag;
import static org.nand2tetris.parser.utils.XMLUtils.formatTag;
import static org.nand2tetris.parser.utils.XMLUtils.openBracketTag;
import static org.nand2tetris.parser.utils.XMLUtils.openParenTag;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.nand2tetris.parser.utils.TagNames;
import org.nand2tetris.tokenizer.Token;

/**
 * integerConstant
 * | stringConstant
 * | keywordConstant = 'true' | 'false' | 'null' | 'this'
 * | varName
 * | varName '[' expression ']'
 * | subroutineCall
 * | '(' expression ')'
 * | unaryOp term
 */
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
    if (varName != null) {
      return varNameBasedTags();
    }

    if (keywordConstant != null) {
      return Collections.singletonList(formatTag(keywordConstant));
    }
    if (stringConstant != null) {
      return Collections.singletonList(formatTag(stringConstant));
    }

    if (integerConstant != null) {
      return Collections.singletonList(formatTag(integerConstant));
    }

    if (parenExpression != null) {
      List<String> tags = new LinkedList<>();
      tags.add(openParenTag());
      tags.add(parenExpression.toXMLString());
      tags.add(closeParenTag());
      return tags;
    }

    if (unaryOp != null && unaryTerm != null) {
      List<String> tags = new LinkedList<>();
      tags.add(formatTag(unaryOp));
      tags.add(unaryTerm.toXMLString());
      return tags;
    }

    return Collections.emptyList();
  }

  private List<String> varNameBasedTags() {
    // varName '.' subroutineName '(' expressionList ')'
    if (subroutineName != null && expressionListNode != null) {
      List<String> tags = new LinkedList<>();
      tags.add(formatTag(varName));
      tags.add(dotTag());
      tags.add(formatTag(subroutineName));
      tags.add(openParenTag());
      tags.add(expressionListNode.toXMLString());
      tags.add(closeParenTag());
      return tags;
    }

    // varName '(' expressionList ')'
    if (expressionListNode != null) {
      List<String> tags = new LinkedList<>();
      tags.add(formatTag(varName));
      tags.add(openParenTag());
      tags.add(expressionListNode.toXMLString());
      tags.add(closeParenTag());
      return tags;
    }

    // varName '[' expression ']'
    if (indexExpression != null) {
      List<String> tags = new LinkedList<>();
      tags.add(formatTag(varName));
      tags.add(openBracketTag());
      tags.add(indexExpression.toXMLString());
      tags.add(closeBracketTag());
      return tags;
    }

    // varName
    return Collections.singletonList(formatTag(varName));
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
