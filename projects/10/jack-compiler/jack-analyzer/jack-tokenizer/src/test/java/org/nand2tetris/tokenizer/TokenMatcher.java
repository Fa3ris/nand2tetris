package org.nand2tetris.tokenizer;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;

public class TokenMatcher {


  public static void matchAll(List<Token> expectedTokens, Tokenizer tokenizer) {
    Assert.assertEquals(expectedTokens, generateTokens(tokenizer));
  }

  private static List<Token> generateTokens(Tokenizer tokenizer) {
    List<Token> tokens = new ArrayList<>();
    while (true) {
      tokenizer.advance();
      if (!tokenizer.hasToken()) {
        return tokens;
      }
      tokens.add(tokenizer.peekToken());
    }
  }

}
