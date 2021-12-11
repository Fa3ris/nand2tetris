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

  @Override
  public void accept(NodeVisitor visitor) {
    new Template<Void>().template(new AcceptNodeTemplater(visitor));
  }

  @Override
  public String toString() {
    return new Template<String>().template(new ToStringTemplater());
  }

  private class Template<T> {

    T template(Templater<T> templater) {

      if (varName != null) {

        // varName '.' subroutineName '(' expressionList ')'
        if (subroutineName != null && expressionListNode != null) {
          return templater.doForMethodOrFunctionCall();
        }

        // varName '(' expressionList ')'
        if (expressionListNode != null) {
          return templater.doForThisMethodCall();
        }

        // varName '[' expression ']'
        if (indexExpression != null) {
          return templater.doForIndexExpression();
        }

        // varName
        return templater.doForVarName();
      }

      if (keywordConstant != null) {
        return templater.doForKeyword();
      }
      if (stringConstant != null) {
        return templater.doForStringConstant();
      }

      if (integerConstant != null) {
        return templater.doForIntegerConstant();
      }

      if (parenExpression != null) {
        return templater.doForParenExpression();
      }

      if (unaryOp != null && unaryTerm != null) {
        return templater.doForUnaryExpression();
      }

      throw new IllegalStateException("no state to template!");
    }

  }

  private interface Templater<T> {

    T doForVarName();
    T doForKeyword();
    T doForStringConstant();
    T doForIntegerConstant();
    T doForParenExpression();
    T doForUnaryExpression();
    T doForMethodOrFunctionCall();
    T doForThisMethodCall();
    T doForIndexExpression();
  }


  private class ToStringTemplater implements Templater<String> {

    @Override
    public String doForVarName() {
      return varName.getLexeme();
    }

    @Override
    public String doForKeyword() {
      return keywordConstant.getLexeme();
    }

    @Override
    public String doForStringConstant() {
      return stringConstant.getLexeme();
    }

    @Override
    public String doForIntegerConstant() {
      return integerConstant.getLexeme();
    }

    @Override
    public String doForParenExpression() {
      return String.format("( %s )", parenExpression);
    }

    @Override
    public String doForUnaryExpression() {
      return String.format("%s %s", unaryOp.getLexeme(), unaryTerm);
    }

    @Override
    public String doForMethodOrFunctionCall() {
      return String.format("%s.%s(%s)", varName.getLexeme(), subroutineName.getLexeme(), expressionListNode);
    }

    @Override
    public String doForThisMethodCall() {
      return String.format("%s(%s)", varName.getLexeme(), expressionListNode);
    }

    @Override
    public String doForIndexExpression() {
      return String.format("%s[%s]", varName.getLexeme(), indexExpression);
    }
  }

  private class AcceptNodeTemplater implements Templater<Void> {

    private final NodeVisitor visitor;

    public AcceptNodeTemplater(NodeVisitor visitor) {
      this.visitor = visitor;
    }

    @Override
    public Void doForVarName() {
      System.out.println("doForVarName " + varName.getLexeme());
      visitor.visitVarName(varName);
      return null;
    }

    @Override
    public Void doForKeyword() {
      System.out.println("doForKeyword " + keywordConstant.getLexeme());
      visitor.visitKeyword(keywordConstant);
      return null;
    }

    @Override
    public Void doForStringConstant() {
      System.out.println("doForStringConstant " + stringConstant.getLexeme());
      throw new UnsupportedOperationException(stringConstant.toString());
    }

    @Override
    public Void doForIntegerConstant() {
      System.out.println("doForIntegerConstant " + integerConstant.getLexeme());
      visitor.visitInteger(integerConstant);
      return null;
    }

    @Override
    public Void doForParenExpression() {
      System.out.println("doForParenExpression " + parenExpression);
      parenExpression.accept(visitor);
      return null;
    }

    @Override
    public Void doForUnaryExpression() {
      System.out.println("doForUnaryExpression " + unaryOp.getLexeme() + " " + unaryTerm);
      visitor.visitUnaryExpression(unaryOp, unaryTerm);
      return null;
    }

    @Override
    public Void doForMethodOrFunctionCall() {
      System.out.println("doForMethodOrFunctionCall");
      visitor.visitMethodOrFunctionCall(varName, subroutineName, (ExpressionListNode) expressionListNode);
      return null;
    }

    @Override
    public Void doForThisMethodCall() {
      System.out.println("doForFunctionCall");
      visitor.visitThisMethodCall(subroutineName, (ExpressionListNode) expressionListNode);
      return null;
    }

    @Override
    public Void doForIndexExpression() {
      System.out.println("doForIndexExpression");
      throw new UnsupportedOperationException(varName.toString() + " " + indexExpression.toString());
    }
  }
}
