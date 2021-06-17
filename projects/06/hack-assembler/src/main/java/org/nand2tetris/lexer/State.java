package org.nand2tetris.lexer;

public interface State {

  /*
      peek next char

      if not part of token \n \r space
        end of token lexer.setEnd();

      else
        write char to string buffer
          if transition
            lexer.setState

   */
  void handle(StateMachine stateMachine);
  TokenType getTokenType();

}
