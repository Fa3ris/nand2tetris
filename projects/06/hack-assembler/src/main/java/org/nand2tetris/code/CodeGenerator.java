package org.nand2tetris.code;

import java.util.ListIterator;
import org.nand2tetris.BinaryFormatter;
import org.nand2tetris.lexer.Token;
import org.nand2tetris.lexer.TokenType;
import org.nand2tetris.parser.AInstruction;
import org.nand2tetris.parser.AST;
import org.nand2tetris.parser.BinaryOperation;
import org.nand2tetris.parser.BitAnd;
import org.nand2tetris.parser.BitOr;
import org.nand2tetris.parser.CInstruction;
import org.nand2tetris.parser.Factor;
import org.nand2tetris.parser.IdentityOperation;
import org.nand2tetris.parser.Minus;
import org.nand2tetris.parser.Negate;
import org.nand2tetris.parser.Node;
import org.nand2tetris.parser.Not;
import org.nand2tetris.parser.Plus;
import org.nand2tetris.parser.SymbolTable;
import org.nand2tetris.parser.UnaryOperation;
import org.nand2tetris.parser.UnexpectedTokenException;

public class CodeGenerator {

  private SymbolTable symbolTable;
  private ListIterator<Node> lit;
  private Node currentInstruction;

  private static int compCodeSize = 7;
  private static int destCodeSize = 3;
  private static int jmpCodeSize = 3;

  public CodeGenerator(AST ast, SymbolTable symbolTable) {
    lit = ast.instructions().listIterator();
    this.symbolTable = symbolTable;
    advanceToNextInstruction();
  }

  public boolean hasNextCode() {
    return lit.hasNext();
  }

  public String nextCode() {
    String result;
    if (currentInstruction instanceof AInstruction) {
      result = codeAInstruction();
    } else {
      result = codeCInstruction();
    }
    advanceToNextInstruction();
    return result;
  }

  private String codeCInstruction() {
    CInstruction cInstruction = (CInstruction) currentInstruction;
    String destCode = codeDest(cInstruction.getDest());
    String compCode = codeComp(cInstruction.getComp());
    String jumpCode = codeJump(cInstruction.getJmp());
    return "111" + compCode + destCode + jumpCode;
  }

  private String codeJump(Factor jmp) {
    int code;
    switch (jmp.getToken().getType()) {
      case JEQ:
        code = CodeTable.JEQ();
        break;
      case JGE:
        code = CodeTable.JGE();
        break;
      case JGT:
        code = CodeTable.JGT();
        break;
      case JLE:
        code = CodeTable.JLE();
        break;
      case JLT:
        code = CodeTable.JLT();
        break;
      case JMP:
        code = CodeTable.JMP();
        break;
      case JNE:
        code = CodeTable.JNE();
        break;
      case NULL:
        code = CodeTable.nullJmp();
        break;
      default:
        throw new IllegalArgumentException(jmp + " is not a valid jmp");
    }
    return BinaryFormatter.toBinaryString(code, jmpCodeSize);
  }

  private String codeComp(Node comp) {

    int codeInt;
    if (comp instanceof BinaryOperation) {
      codeInt = codeBinaryOp(comp);
    } else if (comp instanceof UnaryOperation) {
      codeInt = codeUnaryOp(comp);
    } else if (comp instanceof IdentityOperation) {
      codeInt = codeIdentityOp(comp);
    } else {
      throw new IllegalArgumentException(comp.getClass() + " is not a valid comp");
    }
    return BinaryFormatter.toBinaryString(codeInt, compCodeSize);
  }

  private int codeIdentityOp(Node comp) {
    IdentityOperation id = (IdentityOperation) comp;
    Token token = id.getFactor().getToken();
    int code;
    switch (token.getType()) {
      case A:
        code = CodeTable.A();
        break;
      case M:
        code = CodeTable.M();
        break;
      case D:
        code = CodeTable.D();
        break;
      case INTEGER: {
        switch (token.getValue()) {
          case "0":
            code = CodeTable.zero();
            break;
          case "1":
            code = CodeTable.one();
            break;
          default:
            throw new IllegalArgumentException(IdentityOperation.class + " unknown constant " + token);
        }
        break;
      }
      default:
        throw new IllegalArgumentException(IdentityOperation.class + " unknown constant " + token);
    }
    return code;
  }

  private int codeUnaryOp(Node comp) {
    int code;
    if (comp instanceof Not) {
      Token token = ((Not) comp).getFactor().getToken();
      switch (token.getType()) {
        case A:
          code = CodeTable.notA();
          break;
        case M:
          code = CodeTable.notM();
          break;
        case D:
          code = CodeTable.notD();
          break;
        default:
          throw new IllegalArgumentException(Not.class + " unknown unary factor " + token);
      }
    } else if (comp instanceof Negate) {
      Token token = ((Negate) comp).getFactor().getToken();
      switch (token.getType()) {
        case A:
          code = CodeTable.minusA();
          break;
        case M:
          code = CodeTable.minusM();
          break;
        case D:
          code = CodeTable.minusD();
          break;
        case INTEGER:
          if ("1".equals(token.getValue())) {
            code = CodeTable.minusOne();
          } else {
            throw new IllegalArgumentException(Negate.class + " unknown unary factor " + token);
          }
          break;
        default:
          throw new IllegalArgumentException(Negate.class + "unknown unary factor " + token);
      }
    } else {
      throw new IllegalArgumentException("unknown unary operation " + comp.getClass());
    }
    return code;
  }

  private int codeBinaryOp(Node comp) {
    int code;
    if (comp instanceof Plus) {
      code = codePlus(comp);
    } else if (comp instanceof Minus) {
      code = codeMinus(comp);
    } else if (comp instanceof BitAnd) {
      code = codeBitAnd(comp);
    } else if (comp instanceof BitOr) {
      code = codeBitOr(comp);
    } else {
      throw new IllegalArgumentException("unknown binary operation " + comp.getClass());
    }
    return code;
  }

  private int codeBitOr(Node comp) {
    BitOr bitOr = (BitOr) comp;
    Token t1 = bitOr.getFactor1().getToken();
    Token t2 = bitOr.getFactor2().getToken();
    int code;
    if (TokenType.D == t1.getType()) {
      switch (t2.getType()) {
        case A:
          code = CodeTable.DOrA();
          break;
        case M:
          code = CodeTable.DOrM();
          break;
        default:
          throw new IllegalArgumentException(BitOr.class + " invalid 2nd factor for D " + t2);
      }
    } else {
      throw new IllegalArgumentException(BitOr.class + " invalid 1st factor " + t1);
    }
    return code;
  }

  private int codeBitAnd(Node comp) {
    BitAnd bitAnd = (BitAnd) comp;
    Token t1 = bitAnd.getFactor1().getToken();
    Token t2 = bitAnd.getFactor2().getToken();
    int code;
    if (TokenType.D == t1.getType()) {
      switch (t2.getType()) {
        case A:
          code = CodeTable.DAndA();
          break;
        case M:
          code = CodeTable.DAndM();
          break;
        default:
          throw new IllegalArgumentException(BitAnd.class + " invalid 2nd factor for D " + t2);
      }
    } else {
      throw new IllegalArgumentException(BitAnd.class + " invalid 1st factor " + t1);
    }
    return code;
  }

  private int codeMinus(Node comp) {
    Minus minus = (Minus) comp;
    Token t1 = minus.getFactor1().getToken();
    Token t2 = minus.getFactor2().getToken();
    int code;
    switch (t1.getType()) {
      case D: {
        switch (t2.getType()) {
          case A:
            code = CodeTable.DMinusA();
            break;
          case M:
            code = CodeTable.DMinusM();
            break;
          case INTEGER:
            if ("1".equals(t2.getValue())) {
              code = CodeTable.DMinusOne();
              break;
            } else {
              throw new IllegalArgumentException(Minus.class + " invalid 2nd factor for D " + t2);
            }
          default:
            throw new IllegalArgumentException(Minus.class + " invalid 2nd factor for D " + t2);
        }
        break;
      }
      case A: {
        switch (t2.getType()) {
          case D :
            code = CodeTable.AMinusD();
            break;
          case INTEGER:
            if ("1".equals(t2.getValue())) {
              code = CodeTable.AMinusOne();
              break;
            } else {
              throw new IllegalArgumentException(Minus.class + " invalid 2nd factor for A " + t2);
            }
          default:
            throw new IllegalArgumentException(Minus.class + " invalid 2nd factor for A " + t2);
        }
        break;
      }
      case M: {
        switch (t2.getType()) {
          case D:
            code = CodeTable.MMinusD();
            break;
          case INTEGER:
            if ("1".equals(t2.getValue())) {
              code = CodeTable.MMinusOne();
              break;
            } else {
              throw new IllegalArgumentException(Minus.class + " invalid 2nd factor for M " + t2);
            }
          default:
            throw new IllegalArgumentException(Minus.class + " invalid 2nd factor for M " + t2);
        }
        break;
      }
      default:
        throw new IllegalArgumentException(Minus.class + " invalid 1st factor " + t1);
    }

    return code;
  }

  private int codePlus(Node comp) {
    Plus plus = (Plus) comp;
    Token t1 = plus.getFactor1().getToken();
    Token t2 = plus.getFactor2().getToken();
    int code;
    switch (t1.getType()) {
      case D: {
        switch (t2.getType()) {
          case A:
            code = CodeTable.DPlusA();
            break;
          case M:
            code = CodeTable.DPlusM();
            break;
          case INTEGER:
            if ("1".equals(t2.getValue())) {
              code = CodeTable.DPlusOne();
              break;
            } else {
              throw new IllegalArgumentException(Plus.class + " invalid 2nd factor for D " + t2);
            }
          default:
            throw new IllegalArgumentException(Plus.class + " invalid 2nd factor for D " + t2);
        }
        break;
      }
      case A: {
        if ("1".equals(t2.getValue())) {
          code = CodeTable.APlusOne();
          break;
        } else {
          throw new IllegalArgumentException(Plus.class + " invalid 2nd factor for A " + t2);
        }
      }
      case M: {
        if ("1".equals(t2.getValue())) {
          code = CodeTable.MPlusOne();
          break;
        } else {
          throw new IllegalArgumentException(Plus.class + " invalid 2nd factor for M " + t2);
        }
      }
      default:
        throw new IllegalArgumentException(Plus.class + " invalid 1st factor " + t1);
    }
    return code;
  }

  private String codeDest(Factor dest) {
    int code;
    switch (dest.getToken().getType()) {
      case A:
        code = CodeTable.destA();
        break;
      case D:
        code = CodeTable.destD();
        break;
      case M:
        code = CodeTable.destM();
        break;
      case AD:
        code = CodeTable.destAD();
        break;
      case AM:
        code = CodeTable.destAM();
        break;
      case MD:
        code = CodeTable.destMD();
        break;
      case AMD:
        code = CodeTable.destAMD();
        break;
      case NULL:
        code = CodeTable.destNull();
        break;
      default:
        throw new IllegalArgumentException(dest + " is not a valid dest");
    }
    return BinaryFormatter.toBinaryString(code, destCodeSize);
  }

  private String codeAInstruction() {
    AInstruction aInstruction = (AInstruction) currentInstruction;
    Token address = aInstruction.getAddress();
    int addressInt;
    switch (address.getType()) {
      case INTEGER: {
        addressInt = Integer.parseInt(address.getValue());
        break;
      }
      case IDENTIFIER: {
        addressInt = symbolTable.lookup(address.getValue());
        break;
      }
      default:
        throw new UnexpectedTokenException(address);
    }
    String code = BinaryFormatter.toBinaryString(addressInt, 15);
    return "0" + code;
  }

  private void advanceToNextInstruction() {
    while (lit.hasNext()) {
      Node node = lit.next();
      if (node instanceof AInstruction || node instanceof CInstruction) {
        currentInstruction = node;
        break;
      }
    }
  }


}
