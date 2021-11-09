package org.nand2tetris.parser;

import java.util.function.Predicate;
import org.nand2tetris.parser.ast.AST;
import org.nand2tetris.parser.ast.ClassNode;
import org.nand2tetris.parser.ast.ClassVarDecNode;
import org.nand2tetris.parser.ast.JackAST;
import org.nand2tetris.parser.ast.Node;
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
    }
    return null;
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
      if (isSymbol(",").test(token)) {
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
    ensureValidToken(token, isSymbol(";"));

    return node;
  }

  private Node parseClass() {

    tokenizer.advance();
    token = tokenizer.peekToken();
    ensureValidToken(token, isTokenType(TokenType.IDENTIFIER));
    ClassNode node = new ClassNode();
    node.setClassName(token.getLexeme());

    tokenizer.advance();
    token = tokenizer.peekToken();
    ensureValidToken(token, isSymbol("{"));

    tokenizer.advance();
    token = tokenizer.peekToken();
    ensureValidToken(token, isSymbol("}"));

    return node;
  }

  private Predicate<Token> isClassToken() {
    return isKeyword("class");
  }

  private Predicate<Token> isFieldToken() {
    return isKeyword("field");
  }

  private Predicate<Token> isStaticToken() {
    return isKeyword("static");
  }

  private Predicate<Token> isIntToken() {
    return isKeyword("int");
  }

  private Predicate<? super Token> isCharToken() {
    return isKeyword("char");
  }

  private Predicate<? super Token> isBooleanToken() {
    return isKeyword("boolean");
  }

  private Predicate<Token> isKeyword(String lexeme) {
    return isKeywordToken().and(isLexeme(lexeme));
  }

  private Predicate<? super Token> isIdentifierToken() {
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

  private Predicate<Token> isSymbol(String lexeme) {
    return isTokenType(TokenType.SYMBOL).and(isLexeme(lexeme));
  }

  private void ensureValidToken(Token token, Predicate<Token> predicate) {
      if (!predicate.test(token)) {
        throw new RuntimeException("invalid token " + token);
      }
  }
}
