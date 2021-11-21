package org.nand2tetris.parser;

import static org.nand2tetris.tokenizer.Keyword.BOOLEAN;
import static org.nand2tetris.tokenizer.Keyword.CHAR;
import static org.nand2tetris.tokenizer.Keyword.CLASS;
import static org.nand2tetris.tokenizer.Keyword.CONSTRUCTOR;
import static org.nand2tetris.tokenizer.Keyword.DO;
import static org.nand2tetris.tokenizer.Keyword.ELSE;
import static org.nand2tetris.tokenizer.Keyword.FIELD;
import static org.nand2tetris.tokenizer.Keyword.FUNCTION;
import static org.nand2tetris.tokenizer.Keyword.INT;
import static org.nand2tetris.tokenizer.Keyword.LET;
import static org.nand2tetris.tokenizer.Keyword.METHOD;
import static org.nand2tetris.tokenizer.Keyword.RETURN;
import static org.nand2tetris.tokenizer.Keyword.STATIC;
import static org.nand2tetris.tokenizer.Keyword.VAR;
import static org.nand2tetris.tokenizer.Keyword.VOID;
import static org.nand2tetris.tokenizer.Keyword.WHILE;
import static org.nand2tetris.tokenizer.Symbol.CLOSE_BRACE;
import static org.nand2tetris.tokenizer.Symbol.CLOSE_PAREN;
import static org.nand2tetris.tokenizer.Symbol.COMMA;
import static org.nand2tetris.tokenizer.Symbol.DOT;
import static org.nand2tetris.tokenizer.Symbol.EQ;
import static org.nand2tetris.tokenizer.Symbol.OPEN_BRACE;
import static org.nand2tetris.tokenizer.Symbol.OPEN_PAREN;
import static org.nand2tetris.tokenizer.Symbol.SEMICOLON;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.function.Predicate;
import org.nand2tetris.parser.ast.AST;
import org.nand2tetris.parser.ast.ClassNode;
import org.nand2tetris.parser.ast.ClassVarDecNode;
import org.nand2tetris.parser.ast.DoNode;
import org.nand2tetris.parser.ast.ExpressionListNode;
import org.nand2tetris.parser.ast.ExpressionNode;
import org.nand2tetris.parser.ast.IfNode;
import org.nand2tetris.parser.ast.JackAST;
import org.nand2tetris.parser.ast.LetNode;
import org.nand2tetris.parser.ast.Node;
import org.nand2tetris.parser.ast.ParameterArgNode;
import org.nand2tetris.parser.ast.ParameterListNode;
import org.nand2tetris.parser.ast.ReturnNode;
import org.nand2tetris.parser.ast.SubroutineBodyNode;
import org.nand2tetris.parser.ast.SubroutineDecNode;
import org.nand2tetris.parser.ast.TermNode;
import org.nand2tetris.parser.ast.VarDecNode;
import org.nand2tetris.parser.ast.WhileNode;
import org.nand2tetris.tokenizer.Keyword;
import org.nand2tetris.tokenizer.Token;
import org.nand2tetris.tokenizer.TokenType;
import org.nand2tetris.tokenizer.Tokenizer;

public class ParserEngine implements Parser {

  private final Tokenizer tokenizer;

  private Token token;

  public ParserEngine(Tokenizer tokenizer) {
    this.tokenizer = tokenizer;
  }

  public AST parse() {
    tokenizer.advance();
    if (!tokenizer.hasToken()) {
      return null;
    }
    token = tokenizer.peekToken();
    AST ast = new JackAST();
    ast.addNode(getRootNode());
    return ast;
  }

  private Node getRootNode() {
    if (isClassToken().test(token)) {
      pushBackToken();
      return parseClass();
    }

    if (isClassVarDecFirstToken().test(token)) {
      return parseClassVarDec();
    }

    if (isSubroutineDecFirstToken().test(token)) {
      return parseSubroutineDec();
    }

    if (isVarToken().test(token)) {
      return parseVarDec();
    }

    if (isOpenBrace().test(token)) {
      return parseSubroutineBody();
    }
    return null;
  }

  private Predicate<Token> isClassVarDecFirstToken() {
    return isFieldToken().or(isStaticToken());
  }

  private Predicate<Token> isSubroutineDecFirstToken() {
    return isFunctionToken().or(isConstructorToken()).or(isMethodToken());
  }

  /**
   * 'class' className '{' classVarDec* subroutineDec* '}'
   */
  private Node parseClass() {
    captureTokenOfType(isClassToken());
    ClassNode node = new ClassNode();
    node.setClassName(captureTokenOfType(isIdentifierToken()));
    captureTokenOfType(isOpenBrace());
    while (true) {
      captureToken();
      if (isClassVarDecFirstToken().test(token)) {
        node.addClassVarDec(parseClassVarDec());
        continue;
      }

      if (isSubroutineDecFirstToken().test(token)) {
        node.addSubroutineDec(parseSubroutineDec());
        continue;
      }
      break;
    }
    ensureValidToken(token, isCloseBrace());
    return node;
  }

  /**
   * ('static' | 'field') type varName (',' varName)* ';'
   */
  private Node parseClassVarDec() {
    ClassVarDecNode node = new ClassVarDecNode();
    node.setScope(token);
    captureTokenOfType(isTypeToken());
    node.setType(token);
    node.addVarNames(parseIdentifiers());
    ensureValidToken(token, isSemicolon());
    return node;
  }

  /**
   * identifier (',' identifier)*
   */
  private List<Token> parseIdentifiers() {
    List<Token> identifiers = new ArrayList<>();
    captureTokenOfType(isIdentifierToken());
    identifiers.add(token);
    while (true) {
      captureToken();
      if (isComma().test(token)) {
        captureTokenOfType(isIdentifierToken());
        identifiers.add(token);
        continue;
      }
      break;
    }
    return identifiers;
  }

  /**
   * ('constructor' | 'function' | 'method') ('void' | type) subroutineName
   * '(' parameterList ')' subroutineBody
   */
  private Node parseSubroutineDec() {
    ensureValidToken(token, isFunctionToken().or(isConstructorToken()).or(isMethodToken()));
    SubroutineDecNode node = new SubroutineDecNode();
    node.setRoutineType(token);
    captureTokenOfType(isVoidToken().or(isTypeToken()));
    node.setReturnType(token);
    captureTokenOfType(isSubroutineName());
    node.setRoutineName(token);
    captureTokenOfType(isOpenParen());
    Node parameterList = parseParameterList();
    node.setParameterListNode(parameterList);
    ensureValidToken(token, isCloseParen());
    captureToken();
    Node subroutineBody = parseSubroutineBody();
    node.setSubroutineBodyNode(subroutineBody);
    return node;
  }

  /**
   * (type varName (',' type varName)* )?
   */
  private Node parseParameterList() {
    ParameterListNode node = new ParameterListNode();
    while (true) {
      captureToken();
      if (isComma().test(token)) {
        captureToken();
        node.addArg(parseParameterListArgNode());
        continue;
      }
      if (isTypeToken().test(token)) {
        node.addArg(parseParameterListArgNode());
        continue;
      }
      break;
    }
    return node;
  }

  /**
   * type varName
   */
  private Node parseParameterListArgNode() {
    ensureValidToken(token, isTypeToken());
    ParameterArgNode arg = new ParameterArgNode();
    arg.setType(token);
    captureTokenOfType(isIdentifierToken());
    arg.setName(token);
    return arg;
  }

  /**
   * '{' varDec* statements '}'
   */
  private Node parseSubroutineBody() {
    ensureValidToken(token, isOpenBrace());
    SubroutineBodyNode node = new SubroutineBodyNode();

    for (Node varDec : parseVarDecs()) {
      node.addVarDec(varDec);
    }

    for (Node statement : parseStatements()) {
      node.addStatement(statement);
    }

    ensureValidToken(token, isCloseBrace());
    return node;
  }

  /**
   * varDec*
   */
  private List<Node> parseVarDecs() {
    List<Node> varDecs = new ArrayList<>();
    while (true) {
      captureToken();
      if (isVarToken().test(token)) {
        varDecs.add(parseVarDec());
        continue;
      }
      break;
    }
    return varDecs;
  }

  /**
   * 'var' type varName (',' varName)* ';'
   */
  private Node parseVarDec() {
    ensureValidToken(token, isVarToken());
    VarDecNode node = new VarDecNode();
    captureTokenOfType(isTypeToken());
    node.setType(token);
    node.addVarNames(parseIdentifiers());
    ensureValidToken(token, isSemicolon());
    return node;
  }

  /**
   * statement*
   */
  private List<Node> parseStatements() {
    List<Node> statements = new ArrayList<>();
    while (true) {
      Node statement = parseStatement();
      if (statement == null) {
        break;
      }
      statements.add(statement);
      captureToken();
    }
    return statements;
  }

  /**
   * letStatement | returnStatement | doStatement | ifStatement | whileStatement
   */
  private Node parseStatement() {
    if (isLetToken().test(token)) {
      return parseLetStatement();
    }

    if (isReturnToken().test(token)) {
      return parseReturnStatement();
    }

    if (isDoToken().test(token)) {
      return parseDoStatement();
    }

    if (isIfToken().test(token)) {
      return parseIfStatement();
    }

    if (isWhileToken().test(token)) {
      return parseWhileStatement();
    }

    return null;
  }

  /**
   * 'while' '(' expression ')' '{' statements '}'
   */
  private Node parseWhileStatement() {
    WhileNode node = new WhileNode();
    captureTokenOfType(isOpenParen());
    node.setExpression(parseExpression());
    captureTokenOfType(isCloseParen());
    captureTokenOfType(isOpenBrace());
    captureToken();
    node.addStatements(parseStatements());
    ensureValidToken(token, isCloseBrace());
    return node;
  }

  /**
   * 'if' '(' expression ')' '{' statements '}' ( else '{' statements '}' )?
   */
  private Node parseIfStatement() {
    ensureValidToken(token, isIfToken());
    IfNode node = new IfNode();
    captureTokenOfType(isOpenParen());
    Node expression = parseExpression();
    node.addExpression(expression);
    captureTokenOfType(isCloseParen());
    captureTokenOfType(isOpenBrace());

    captureToken();
    for (Node statement : parseStatements()) {
      node.addIfStatement(statement);
    }
    ensureValidToken(token, isCloseBrace());

    captureToken();
    if (isElseToken().test(token)) {
      node.setElseBlockPresent();
      captureTokenOfType(isOpenBrace());

      captureToken();
      List<Node> elseStatements = parseStatements();
      for (Node statement : elseStatements) {
        node.addElseStatement(statement);
      }
      ensureValidToken(token, isCloseBrace());
    } else {
      pushBackToken();
    }
    return node;
  }

  /**
   * 'do' subroutineCall ';'
   *
   * subroutineName '(' expressionList ')' |
   * (className | varName) '.' subroutineName '(' expressionList ')'
   */
  private Node parseDoStatement() {
    ensureValidToken(token, isDoToken());
    DoNode node = new DoNode();
    captureTokenOfType(isIdentifierToken());
    Token identifier = token;
    captureToken();
    if (isDotToken().test(token)) {
      node.addIdentifier(identifier);
      captureTokenOfType(isIdentifierToken());
      node.addSubroutineName(token);
      captureTokenOfType(isOpenParen());
    } else if (isOpenParen().test(token)) {
      node.addSubroutineName(identifier);
    }
    node.addExpressionList(parseExpressionList());
    ensureValidToken(token, isCloseParen());
    captureTokenOfType(isSemicolon());
    return node;
  }

  /**
   * 'let' varName ( '[' expression ']' )? '=' expression ';'
   */
  private Node parseLetStatement() {
    ensureValidToken(token, isLetToken());
    LetNode node = new LetNode();
    captureTokenOfType(isIdentifierToken());
    node.setVarName(token);
    captureTokenOfType(isEqualToken());
    Node rightExpression = parseExpression();
    node.setRightExpression(rightExpression);
    captureTokenOfType(isSemicolon());
    return node;
  }

  /**
   * 'return' expression? ';'
   */
  private Node parseReturnStatement() {
    ensureValidToken(token, isReturnToken());
    ReturnNode node = new ReturnNode();

    captureToken();
    if (isSemicolon().test(token)) {
      return node;
    }

    pushBackToken();


    node.setExpression(parseExpression());
    captureTokenOfType(isSemicolon());
    return node;
  }

  private final Deque<Token> stack = new ArrayDeque<>();


  /**
   * (expression (',' expression)* )?
   */
  private Node parseExpressionList() {
    ExpressionListNode node = new ExpressionListNode();

    captureToken();
    if (isCloseParen().test(token)) {
      return node;
    }
    pushBackToken();
    node.addExpression(parseExpression());

    captureToken();
    if (isCloseParen().test(token)) {
      return node;
    }

    throw new IllegalStateException();
  }

  /**
   * term (op term)*
   */
  private Node parseExpression() {
    ExpressionNode node = new ExpressionNode();
    node.addTerm(parseTerm());
    return node;
  }

  /**
   * integerConstant
   * | stringConstant
   * | keywordConstant
   * | varName
   * | varName '[' expression ']'
   * | subroutineCall
   * | '(' expression ')'
   * | unaryOp term
   */
  private Node parseTerm() {
    TermNode node = new TermNode();
    captureTokenOfType(isIdentifierToken());
    node.addVarName(token);
    return node;
  }

  private Token captureTokenOfType(Predicate<Token> predicate) {
    captureToken();
    ensureValidToken(token, predicate);
    return token;
  }

  private Token captureToken() {
    if (!stack.isEmpty()) {
      token = stack.pop();
      return token;
    }
    tokenizer.advance();
    token = tokenizer.peekToken();
    return token;
  }

  private void pushBackToken() {
    stack.push(token);
  }

  private void ensureValidToken(Token token, Predicate<Token> predicate) {
    if (!predicate.test(token)) {
      throw new RuntimeException("invalid token " + token);
    }
  }

  private Predicate<Token> isSubroutineName() {
    return isIdentifierToken();
  }

  private Predicate<Token> isDotToken() {
    return isSymbol(DOT);
  }

  private Predicate<Token> isDoToken() {
    return isKeyword(DO);
  }

  private Predicate<Token> isEqualToken() {
    return isSymbol(EQ);
  }

  private Predicate<Token> isWhileToken() {
    return isKeyword(WHILE);
  }

  private Predicate<Token> isElseToken() {
    return isKeyword(ELSE);
  }

  private Predicate<Token> isIfToken() {
    return isKeyword(Keyword.IF);
  }

  private Predicate<Token> isReturnToken() {
    return isKeyword(RETURN);
  }

  private Predicate<Token> isConstructorToken() {
    return isKeyword(CONSTRUCTOR);
  }

  /**
   * 'int' | 'char' | 'boolean' | className
   */
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

  private Predicate<Token> isMethodToken() {
    return isKeyword(METHOD);
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

  private Predicate<Token> isLetToken() {
    return isKeyword(LET);
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
    return isSymbol(OPEN_PAREN);
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

}
