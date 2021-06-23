package org.nand2tetris.parser.resolver;

import org.nand2tetris.lexer.Token;
import org.nand2tetris.lexer.TokenType;
import org.nand2tetris.parser.AInstruction;
import org.nand2tetris.parser.AST;
import org.nand2tetris.parser.LabelDefinition;
import org.nand2tetris.parser.Node;
import org.nand2tetris.parser.SymbolTable;

public class Resolver {

  public SymbolTable resolveSymbols(AST ast) {
    SymbolTable table = new SymbolTable();
    resolveLabels(ast, table);
    resolveVariables(ast, table);
    return table;
  }

  private void resolveLabels(AST ast, SymbolTable table) {
    int instructionCount = 0;
    for (Node instruction : ast.instructions()) {
      instructionCount++;
      if (instruction instanceof LabelDefinition) {
        LabelDefinition definition = (LabelDefinition) instruction;
        table.addLabel(definition.getLabel(), instructionCount + 1);
      }
    }
  }

  private void resolveVariables(AST ast, SymbolTable table) {
    for (Node instruction : ast.instructions()) {
      if (instruction instanceof AInstruction) {
        AInstruction aInstruction = (AInstruction) instruction;
        Token address = aInstruction.getAddress();
        if (TokenType.IDENTIFIER == address.getType()) {
          if (null == table.lookup(address.getValue())) {
            table.addVariable(address.getValue());
          }
        }
      }
    }
  }

}
