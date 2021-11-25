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
import java.util.Objects;
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

  private final Deque<Token> stack = new ArrayDeque<>();

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
      pushBackToken();
      return parseClassVarDec();
    }

    if (isSubroutineDecFirstToken().test(token)) {
      pushBackToken();
      return parseSubroutineDec();
    }

    if (isVarToken().test(token)) {
      pushBackToken();
      return parseVarDec();
    }

    if (isOpenBrace().test(token)) {
      pushBackToken();
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
      Node classVarDec = parseClassVarDec();
      if (classVarDec != null) {
        node.addClassVarDec(classVarDec);
      } else {
        break;
      }
    }
    while (true) {
      Node subroutineDec = parseSubroutineDec();
      if (subroutineDec != null) {
        node.addSubroutineDec(subroutineDec);
      } else {
        break;
      }
    }
    captureTokenOfType(isCloseBrace());
    return node;
  }

  /**
   * ('static' | 'field') type varName (',' varName)* ';'
   */
  private Node parseClassVarDec() {
    captureToken();
    if (!isClassVarDecFirstToken().test(token)) {
      pushBackToken();
      return null;
    }
    ClassVarDecNode node = new ClassVarDecNode();
    node.setScope(token);
    node.setType(captureTokenOfType(isTypeToken()));
    node.addVarNames(parseIdentifiers());
    captureTokenOfType(isSemicolon());
    return node;
  }

  /**
   * identifier (',' identifier)*
   */
  private List<Token> parseIdentifiers() {
    List<Token> identifiers = new ArrayList<>();
    identifiers.add(captureTokenOfType(isIdentifierToken()));
    while (true) {
      captureToken();
      if (isComma().test(token)) {
        identifiers.add(captureTokenOfType(isIdentifierToken()));
      } else {
        pushBackToken();
        return identifiers;
      }
    }
  }

  /**
   * ('constructor' | 'function' | 'method') ('void' | type) subroutineName
   * '(' parameterList ')' subroutineBody
   */
  private Node parseSubroutineDec() {
    captureToken();
    if (!isSubroutineDecFirstToken().test(token)) {
      pushBackToken();
      return null;
    }
    SubroutineDecNode node = new SubroutineDecNode();
    node.setRoutineType(token);
    node.setReturnType(captureTokenOfType(isVoidToken().or(isTypeToken())));
    node.setRoutineName(captureTokenOfType(isSubroutineName()));
    captureTokenOfType(isOpenParen());
    node.setParameterListNode(parseParameterList());
    captureTokenOfType(isCloseParen());
    node.setSubroutineBodyNode(parseSubroutineBody());
    return node;
  }

  /**
   * (type varName (',' type varName)* )?
   */
  private Node parseParameterList() {
    ParameterListNode node = new ParameterListNode();
    Node argNode = parseParameterListArgNode();
    if (argNode == null) {
      return node;
    }
    node.addArg(argNode);
    while (true) {
      captureToken();
      if (isComma().test(token)) {
        node.addArg(Objects.requireNonNull(parseParameterListArgNode()));
      } else {
        pushBackToken();
        return node;
      }
    }
  }

  /**
   * type varName
   */
  private Node parseParameterListArgNode() {
    captureToken();
    if (!isTypeToken().test(token)) {
      pushBackToken();
      return null;
    }
    ParameterArgNode arg = new ParameterArgNode();
    arg.setType(token);
    arg.setName(captureTokenOfType(isIdentifierToken()));
    return arg;
  }

  /**
   * '{' varDec* statements '}'
   */
  private Node parseSubroutineBody() {
    captureTokenOfType(isOpenBrace());
    SubroutineBodyNode node = new SubroutineBodyNode();
    for (Node varDec : parseVarDecs()) {
      node.addVarDec(varDec);
    }
    for (Node statement : parseStatements()) {
      node.addStatement(statement);
    }
    captureTokenOfType(isCloseBrace());
    return node;
  }

  /**
   * varDec*
   */
  private List<Node> parseVarDecs() {
    List<Node> varDecs = new ArrayList<>();
    while (true) {
      Node varDec = parseVarDec();
      if (varDec != null) {
        varDecs.add(varDec);
      } else {
        break;
      }
    }
    return varDecs;
  }

  /**
   * 'var' type varName (',' varName)* ';'
   */
  private Node parseVarDec() {
    captureToken();
    if (!isVarToken().test(token)) {
      pushBackToken();
      return null;
    }
    VarDecNode node = new VarDecNode();
    node.setType(captureTokenOfType(isTypeToken()));
    node.addVarNames(parseIdentifiers());
    captureTokenOfType(isSemicolon());
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
        return statements;
      }
      statements.add(statement);
    }
  }

  /**
   * letStatement | returnStatement | doStatement | ifStatement | whileStatement
   */
  private Node parseStatement() {
    captureToken();
    if (isLetToken().test(token)) {
      pushBackToken();
      return parseLetStatement();
    }

    if (isReturnToken().test(token)) {
      pushBackToken();
      return parseReturnStatement();
    }

    if (isDoToken().test(token)) {
      pushBackToken();
      return parseDoStatement();
    }

    if (isIfToken().test(token)) {
      pushBackToken();
      return parseIfStatement();
    }

    if (isWhileToken().test(token)) {
      pushBackToken();
      return parseWhileStatement();
    }

    pushBackToken();
    return null;
  }

  /**
   * 'while' '(' expression ')' '{' statements '}'
   */
  private Node parseWhileStatement() {
    captureTokenOfType(isWhileToken());
    WhileNode node = new WhileNode();
    captureTokenOfType(isOpenParen());
    node.setExpression(parseExpression());
    captureTokenOfType(isCloseParen());
    captureTokenOfType(isOpenBrace());
    node.addStatements(parseStatements());
    captureTokenOfType(isCloseBrace());
    return node;
  }

  /**
   * 'if' '(' expression ')' '{' statements '}' ( else '{' statements '}' )?
   */
  private Node parseIfStatement() {
    captureTokenOfType(isIfToken());
    IfNode node = new IfNode();
    captureTokenOfType(isOpenParen());
    node.addExpression(parseExpression());
    captureTokenOfType(isCloseParen());
    captureTokenOfType(isOpenBrace());

    for (Node ifStatement : parseStatements()) {
      node.addIfStatement(ifStatement);
    }
    captureTokenOfType(isCloseBrace());

    captureToken();
    if (isElseToken().test(token)) {
      node.setElseBlockPresent();
      captureTokenOfType(isOpenBrace());

      for (Node elseStatement : parseStatements()) {
        node.addElseStatement(elseStatement);
      }

      captureTokenOfType(isCloseBrace());
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
    captureTokenOfType(isDoToken());
    DoNode node = new DoNode();
    Token identifier = captureTokenOfType(isIdentifierToken());
    captureToken();
    if (isDotToken().test(token)) {
      node.addIdentifier(identifier);
      node.addSubroutineName(captureTokenOfType(isIdentifierToken()));
      captureTokenOfType(isOpenParen());
    } else if (isOpenParen().test(token)) {
      node.addSubroutineName(identifier);
    } else {
      throw new IllegalStateException();
    }
    node.addExpressionList(parseExpressionList());
    captureTokenOfType(isCloseParen());
    captureTokenOfType(isSemicolon());
    return node;
  }

  /**
   * 'let' varName ( '[' expression ']' )? '=' expression ';'
   */
  private Node parseLetStatement() {
    captureTokenOfType(isLetToken());
    LetNode node = new LetNode();
    node.setVarName(captureTokenOfType(isIdentifierToken()));
    captureTokenOfType(isEqualToken());
    node.setRightExpression(parseExpression());
    captureTokenOfType(isSemicolon());
    return node;
  }

  /**
   * 'return' expression? ';'
   */
  private Node parseReturnStatement() {
    captureTokenOfType(isReturnToken());
    ReturnNode node = new ReturnNode();

    Node expression = parseExpression();
    if (expression != null) {
      node.setExpression(expression);
    }
    captureTokenOfType(isSemicolon());
    return node;
  }

  /**
   * (expression (',' expression)* )?
   */
  private Node parseExpressionList() {
    ExpressionListNode node = new ExpressionListNode();
    Node expression = parseExpression();
    if (expression != null) {
      node.addExpression(expression);
    }
    return node;
  }

  /**
   * term (op term)*
   */
  private Node parseExpression() {
    Node term = parseTerm();
    if (term == null) {
      return null;
    }
    ExpressionNode node = new ExpressionNode();
    node.addTerm(term);
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
    captureToken();
    if (isIdentifierToken().test(token)) {
      TermNode node = new TermNode();
      node.addVarName(token);
      return node;
    }
    pushBackToken();
    return null;
  }

  private Token captureTokenOfType(Predicate<Token> predicate) {
    ensureValidToken(captureToken(), predicate);
    return token;
  }

  private Token captureToken() {
    if (!stack.isEmpty()) {
      return token = stack.pop();
    }
    tokenizer.advance();
    return token = tokenizer.peekToken();
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
