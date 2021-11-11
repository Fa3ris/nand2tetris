package org.nand2tetris.parser;

import static org.nand2tetris.tokenizer.Keyword.BOOLEAN;
import static org.nand2tetris.tokenizer.Keyword.CHAR;
import static org.nand2tetris.tokenizer.Keyword.CLASS;
import static org.nand2tetris.tokenizer.Keyword.FIELD;
import static org.nand2tetris.tokenizer.Keyword.FUNCTION;
import static org.nand2tetris.tokenizer.Keyword.INT;
import static org.nand2tetris.tokenizer.Keyword.STATIC;
import static org.nand2tetris.tokenizer.Keyword.VAR;
import static org.nand2tetris.tokenizer.Keyword.VOID;
import static org.nand2tetris.tokenizer.Symbol.CLOSE_BRACE;
import static org.nand2tetris.tokenizer.Symbol.CLOSE_PAREN;
import static org.nand2tetris.tokenizer.Symbol.COMMA;
import static org.nand2tetris.tokenizer.Symbol.OPEN_BRACE;
import static org.nand2tetris.tokenizer.Symbol.OPEN_PAREN;
import static org.nand2tetris.tokenizer.Symbol.SEMICOLON;

import java.util.function.Predicate;
import org.nand2tetris.parser.ast.AST;
import org.nand2tetris.parser.ast.ClassNode;
import org.nand2tetris.parser.ast.ClassVarDecNode;
import org.nand2tetris.parser.ast.JackAST;
import org.nand2tetris.parser.ast.Node;
import org.nand2tetris.parser.ast.ParameterListNode;
import org.nand2tetris.parser.ast.SubroutineBodyNode;
import org.nand2tetris.parser.ast.SubroutineDecNode;
import org.nand2tetris.parser.ast.VarDecNode;
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

      if (isVarToken().test(token)) {
        Node node = parseVarDec();
        AST ast = new JackAST();
        ast.addNode(node);
        return ast;
      }
    }
    return null;
  }

  private Node parseVarDec() {
    VarDecNode node = new VarDecNode();

    tokenizer.advance();
    token = tokenizer.peekToken();
    ensureValidToken(token, isTypeToken());

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
    ensureValidToken(token, isTypeToken());
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

  private Predicate<Token> isTypeToken() {
    return isIntToken().or(isCharToken()).or(isBooleanToken()).or(
        isIdentifierToken());
  }
  private Predicate<Token> isClassToken() {
    return isKeyword(CLASS);
  }

  private Predicate<Token> isFieldToken() {
    return isKeyword(FIELD);
  }

  private Predicate<Token> isFunctionToken() {
    return isKeyword(FUNCTION);
  }

  private Predicate<Token> isStaticToken() {
    return isKeyword(STATIC);
  }

  private Predicate<Token> isIntToken() {
    return isKeyword(INT);
  }

  private Predicate<Token> isCharToken() {
    return isKeyword(CHAR);
  }

  private Predicate<Token> isVoidToken() {
    return isKeyword(VOID);
  }

  private Predicate<Token> isVarToken() {
    return isKeyword(VAR);
  }


  private Predicate<Token> isBooleanToken() {
    return isKeyword(BOOLEAN);
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
    return isSymbol(OPEN_PAREN
    );
  }

  private Predicate<Token> isCloseParen() {
    return isSymbol(CLOSE_PAREN);
  }

  private Predicate<Token> isOpenBrace() {
    return isSymbol(OPEN_BRACE);
  }

  private Predicate<Token> isCloseBrace() {
    return isSymbol(CLOSE_BRACE);
  }

  private Predicate<Token> isComma() {
    return isSymbol(COMMA);
  }

  private Predicate<Token> isSemicolon() {
    return isSymbol(SEMICOLON);
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
