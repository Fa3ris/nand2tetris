package org.nand2tetris.parser;

import java.util.function.Predicate;
import org.nand2tetris.parser.ast.AST;
import org.nand2tetris.parser.ast.ClassNode;
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
      Token token = tokenizer.peekToken();

      if (isClassToken().test(token)) {
        Node node = parseClass();
        AST ast = new JackAST();
        ast.addNode(node);
        return ast;
      }
    }
    return null;
  }


  private Node parseClass() {

    tokenizer.advance();
    token = tokenizer.peekToken();
    ensureTokenOfType(token, isOfType(TokenType.IDENTIFIER));
    ClassNode node = new ClassNode();
    node.setClassName(token.getLexeme());

    tokenizer.advance();
    token = tokenizer.peekToken();
    ensureTokenOfType(token, isSymbol("{"));

    tokenizer.advance();
    token = tokenizer.peekToken();
    ensureTokenOfType(token, isSymbol("}"));

    return node;
  }

  private Predicate<Token> isClassToken() {
    return isKeyword().and(isLexeme("class"));
  }

  private Predicate<Token> isKeyword() {
    return isOfType(TokenType.KEYWORD);
  }

  private Predicate<Token> isOfType(TokenType type) {
    return token -> token.getType() == type;
  }

  private Predicate<Token> isLexeme(String lexeme) {
    return token -> token.getLexeme().equals(lexeme);
  }

  private Predicate<Token> isSymbol(String lexeme) {
    return isOfType(TokenType.SYMBOL).and(isLexeme(lexeme));
  }

  private void ensureTokenOfType(Token token, Predicate<Token> predicate) {
      if (!predicate.test(token)) {
        throw new RuntimeException("invalid token " + token);
      }
  }
}
