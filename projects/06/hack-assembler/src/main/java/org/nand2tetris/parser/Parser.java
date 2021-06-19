package org.nand2tetris.parser;

import java.util.LinkedList;
import java.util.List;
import org.nand2tetris.lexer.Token;
import org.nand2tetris.lexer.TokenStream;
import org.nand2tetris.lexer.TokenType;

public class Parser {

  private TokenStream tokenStream;

  public AST parse(TokenStream stream) {
    tokenStream = stream;
    Node program = parseProgram();
    return new AST(program);
  }

  private Node parseProgram() {
    List<Node> statements = new LinkedList<>();
    while (true) {
      Token scanned = tokenStream.peekToken();
      if (TokenType.EOF == scanned.getType()) {
        break;
      }
      Node statement = parseStatement();
      statements.add(statement);
    }
    return new Program(statements);
  }

  private Node parseStatement() {
    Token scanned = tokenStream.peekToken();
    System.out.println(scanned);
    TokenType type = scanned.getType();
    if (TokenType.AT == type) {
      tokenStream.nextToken();
      return parseAInstruction();
    }

    if (TokenType.OPEN_PAREN == type) {
      tokenStream.nextToken();
      return parseLabelDefinition();
    }

    return parseCInstruction();
  }

  private Node parseAInstruction() {
    Token scanned = tokenStream.peekToken();
    System.out.println(scanned);
    TokenType type = scanned.getType();
    if (TokenType.INTEGER == type || TokenType.IDENTIFIER == type) {
      AInstruction aInstruction = new AInstruction(tokenStream.nextToken());
      return aInstruction;
    }
    throw new UnexpectedTokenException(scanned);
  }

  private Node parseLabelDefinition() {

    // read label
    Token scanned = tokenStream.peekToken();
    System.out.println(scanned);
    TokenType type = scanned.getType();
    LabelDefinition labelDefinition;
    if (TokenType.IDENTIFIER == type) {
      labelDefinition = new LabelDefinition(tokenStream.nextToken());
      System.out.println(labelDefinition);
    } else {
      throw new UnexpectedTokenException(new Token(TokenType.IDENTIFIER, ")"), scanned);
    }

    // read close parenthesis
    scanned = tokenStream.peekToken();
    type = scanned.getType();
    System.out.println(scanned);
    if (TokenType.CLOSE_PAREN == type) {
      tokenStream.nextToken();
    } else {
      throw new UnexpectedTokenException(new Token(TokenType.CLOSE_PAREN, ")"), scanned);
    }

    return labelDefinition;
  }

  /*
    dest = parseDest() // scan first token only and check if valid destination
    if (dest == null) // invalid destination
      dest = NULL_DEST_NODE
      cmp = parseCmp() // parse as cmp expression
    else
        if (scanToken() == '=') // assignment present
          readToken()
          cmp = parseCmp() // cmp follows
        else // no assignment - first expression is cmp
          cmp = dest
    if (scanToken() == ';') // jmp condition present
      jmp = parseJmp()
    else
      jmp = NULL_JMP_NODE

    return CInstruction(dest, comp, jmp)
   */
  private Node parseCInstruction() {
    Factor dest = parseDest();

    Node comp;
    Factor jmp;
    if (dest == null) {
      dest = new NullFactor();
      comp = parseComp();
    } else {
      Token scanned = tokenStream.peekToken();
      switch (scanned.getType()) {
        case ASSIGN: {
          tokenStream.nextToken();
          comp = parseComp();
          break;
        }
        case PLUS:
        case MINUS:
        case BIT_AND:
        case BIT_OR: {
          Token operator = tokenStream.nextToken();
          Factor secondFactor = parseFactor();
          comp = ExpressionNodeFactory.binaryExpression(operator, dest, secondFactor);
          dest = new NullFactor();
          break;
        }
        default: {
          comp = new IdentityOperation(dest);
          dest = new NullFactor();
        }
      }
    }

    Token scanned = tokenStream.peekToken();
    if (TokenType.SEMI_COLON == scanned.getType()) {
      tokenStream.nextToken();
      jmp = parseJmp();
    } else {
      jmp = new NullFactor();
    }

    return new CInstruction(dest, comp, jmp);
  }

  private Factor parseDest() {
    Token scanned = tokenStream.peekToken();
    switch (scanned.getType()) {
      // valid dest
      case A:
      case D:
      case M:
      case AD:
      case AM:
      case MD:
      case AMD: {
        tokenStream.nextToken();
        return new Factor(scanned);
      }
      // unary op
      case NOT:
      case MINUS:
        return null;
      // constant factors
      case INTEGER: {
        switch (scanned.getValue()) {
          case "0":
          case "1":
            return null;
        }
      }
      default: {
        throw new UnexpectedTokenException(scanned);
      }
    }
  }

  // same as Comp
  /*
  example
  E -> T { +|- T}
  T -> F { *|/ F}
  F -> ID | INT | (E) | -F

  for Hack language
  EBF
  [ ] == optional
  { } == zero or more times

  E -> F { +|-|&|'|' F} | !F
  F -> 0 | 1 | A | D | M

  if (scanToken == '!')
    readToken
    factor = parseFactor()
    return Not(factor)
  if (scanToken == '-')
    readToken
    factor = parseFactor()
    return Negate(factor)
  else
    factor = parseFactor()
    if (scanToken == binary operator)
      operator = readToken()
      factor2 = parseFactor()
      return BinaryOp(operator, factor, factor2;
    else
      return factor

   */
  private Node parseComp() {
    Token scanned = tokenStream.peekToken();
    switch (scanned.getType()) {
      case NOT:
      case MINUS: {
        Token operator = tokenStream.nextToken();
        Factor factor = parseFactor();
        return ExpressionNodeFactory.unaryExpression(operator, factor);
      }
    }
    Factor factor = parseFactor();
    scanned = tokenStream.peekToken();
    switch (scanned.getType()) {
      case PLUS:
      case MINUS:
      case BIT_AND:
      case BIT_OR: {
        Token operator = tokenStream.nextToken();
        Factor secondFactor = parseFactor();
        return ExpressionNodeFactory.binaryExpression(operator, factor, secondFactor);
      }
    }
    // A D M 0 1
    return new IdentityOperation(factor);
  }

  private Factor parseJmp() {
    Token scanned = tokenStream.peekToken();
    switch (scanned.getType()) {
      case JEQ:
      case JGE:
      case JGT:
      case JLE:
      case JLT:
      case JMP:
      case JNE: {
        tokenStream.nextToken();
        return new Factor(scanned);
      }
      default: {
        throw new UnexpectedTokenException(scanned);
      }
    }
  }


  private Factor parseFactor() {
    Token scanned = tokenStream.peekToken();
    switch (scanned.getType()) {
      case A:
      case D:
      case M: {
        tokenStream.nextToken();
        return new Factor(scanned);
      }
      case INTEGER: {
        switch (scanned.getValue()) {
          case "0":
          case "1": {
            tokenStream.nextToken();
            return new Factor(scanned);
          }
        }
      }
    }
    throw new UnexpectedTokenException(scanned);
  }
}
