package org.nand2tetris.parser;

import java.util.function.Predicate;
import org.nand2tetris.parser.ast.AST;
import org.nand2tetris.parser.ast.ClassNode;
import org.nand2tetris.parser.ast.ClassVarDecNode;
import org.nand2tetris.parser.ast.JackAST;
import org.nand2tetris.parser.ast.Node;
import org.nand2tetris.parser.ast.ParameterListNode;
import org.nand2tetris.parser.ast.SubroutineBodyNode;
import org.nand2tetris.parser.ast.SubroutineDecNode;
import org.nand2tetris.tokenizer.Token;
import org.nand2tetris.tokenizer.TokenType;
import org.nand2tetris.tokenizer.Tokenizer;

public class ParserEngine implements Parser {

  private Tokenizer tokenizer;

  private Token token;

  public ParserEngine(Tokenizer tokenizer) {
    this.tokenizer = tokenizer;
  }

  public AST parse() {
    tokenizer.advance();
    if (tokenizer.hasToken()) {
      token = tokenizer.peekToken();

      if (isClassToken().test(token)) {
        Node node = parseClass();
        AST ast = new JackAST();
        ast.addNode(node);
        return ast;
      }

      if (isFieldToken().or(isStaticToken()).test(token)) {
        Node node = parseClassVarDec();
        AST ast = new JackAST();
        ast.addNode(node);
        return ast;
      }

      if (isFunctionToken().test(token)) {
        Node node = parseSubroutineDec();
        AST ast = new JackAST();
        ast.addNode(node);
        return ast;

      }
    }
    return null;
  }

  private Node parseSubroutineDec() {
    SubroutineDecNode node = new SubroutineDecNode();
    node.setRoutineType(token);
    tokenizer.advance();
    token = tokenizer.peekToken();
    ensureValidToken(token, isVoidToken());

    node.setReturnType(token);

    tokenizer.advance();
    token = tokenizer.peekToken();
    ensureValidToken(token, isIdentifierToken());

    node.setRoutineName(token);

    tokenizer.advance();
    token = tokenizer.peekToken();
    ensureValidToken(token, isOpenParen());

    Node parameterList = parseParameterList();
    node.setParameterListNode(parameterList);
    tokenizer.advance();
    token = tokenizer.peekToken();
    ensureValidToken(token, isCloseParen());

    tokenizer.advance();
    token = tokenizer.peekToken();
    ensureValidToken(token, isOpenBrace());

    Node subroutineBody = parseSubroutineBody();
    node.setSubroutineBodyNode(subroutineBody);
    tokenizer.advance();
    token = tokenizer.peekToken();
    ensureValidToken(token, isCloseBrace());

    return node;
  }

  private Node parseParameterList() {
    ParameterListNode node = new ParameterListNode();
    return node;
  }

  private Node parseSubroutineBody() {
    SubroutineBodyNode node = new SubroutineBodyNode();


    return node;
  }

  private Node parseClassVarDec() {
    ClassVarDecNode node = new ClassVarDecNode();
    node.setScope(token);
    tokenizer.advance();
    token = tokenizer.peekToken();
    ensureValidToken(token, isIntToken().or(isCharToken()).or(isBooleanToken()).or(
        isIdentifierToken()));
    node.setType(token);

    tokenizer.advance();
    token = tokenizer.peekToken();
    ensureValidToken(token, isTokenType(TokenType.IDENTIFIER));
    node.addVarName(token);

    tokenizer.advance();
    token = tokenizer.peekToken();

    while (true) {
      if (isComma().test(token)) {
        tokenizer.advance();
        token = tokenizer.peekToken();
        ensureValidToken(token, isTokenType(TokenType.IDENTIFIER));
        node.addVarName(token);
        tokenizer.advance();
        token = tokenizer.peekToken();
      } else {
        break;
      }
    }
    ensureValidToken(token, isSemicolon());

    return node;
  }

  private Node parseClass() {

    tokenizer.advance();
    token = tokenizer.peekToken();
    ensureValidToken(token, isTokenType(TokenType.IDENTIFIER));
    ClassNode node = new ClassNode();
    node.setClassName(token);

    tokenizer.advance();
    token = tokenizer.peekToken();
    ensureValidToken(token, isOpenBrace());

    tokenizer.advance();
    token = tokenizer.peekToken();
    ensureValidToken(token, isCloseBrace());

    return node;
  }

  private Predicate<Token> isClassToken() {
    return isKeyword("class");
  }

  private Predicate<Token> isFieldToken() {
    return isKeyword("field");
  }

  private Predicate<Token> isFunctionToken() {
    return isKeyword("function");
  }

  private Predicate<Token> isStaticToken() {
    return isKeyword("static");
  }

  private Predicate<Token> isIntToken() {
    return isKeyword("int");
  }

  private Predicate<Token> isCharToken() {
    return isKeyword("char");
  }

  private Predicate<Token> isVoidToken() {
    return isKeyword("void");
  }

  private Predicate<Token> isBooleanToken() {
    return isKeyword("boolean");
  }

  private Predicate<Token> isKeyword(String lexeme) {
    return isKeywordToken().and(isLexeme(lexeme));
  }

  private Predicate<Token> isIdentifierToken() {
    return isTokenType(TokenType.IDENTIFIER);
  }

  private Predicate<Token> isKeywordToken() {
    return isTokenType(TokenType.KEYWORD);
  }

  private Predicate<Token> isTokenType(TokenType type) {
    return token -> token.getType() == type;
  }

  private Predicate<Token> isLexeme(String lexeme) {
    return token -> token.getLexeme().equals(lexeme);
  }

  private Predicate<Token> isOpenParen() {
    return isSymbol("(");
  }

  private Predicate<Token> isCloseParen() {
    return isSymbol(")");
  }

  private Predicate<Token> isOpenBrace() {
    return isSymbol("{");
  }

  private Predicate<Token> isCloseBrace() {
    return isSymbol("}");
  }

  private Predicate<Token> isComma() {
    return isSymbol(",");
  }

  private Predicate<Token> isSemicolon() {
    return isSymbol(";");
  }
  private Predicate<Token> isSymbol(String lexeme) {
    return isTokenType(TokenType.SYMBOL).and(isLexeme(lexeme));
  }

  private void ensureValidToken(Token token, Predicate<Token> predicate) {
      if (!predicate.test(token)) {
        throw new RuntimeException("invalid token " + token);
      }
  }
}
