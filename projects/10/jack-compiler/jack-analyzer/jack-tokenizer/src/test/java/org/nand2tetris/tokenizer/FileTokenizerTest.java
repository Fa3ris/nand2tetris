package org.nand2tetris.tokenizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.nand2tetris.tokenizer.stubs.CharReaderStub;

public class FileTokenizerTest {

  @Test
  public void hasNoTokenIfNoContent() throws Exception {
    String[] whiteSpaces = {
        "",
        "     ",
        "\t",
        "\n",
        "\r",
        "\r\n"
    };
    for (String whiteSpace : whiteSpaces) {
      Tokenizer tokenizer = buildTokenizer(whiteSpace);
      tokenizer.advance();
      assertFalse(tokenizer.hasToken());
    }
  }

  @Test
  public void hasTokenIfContent() throws Exception {
    String[] charStreams = {
        "Hello",
        "{",
        "+"
    };
    for (String charStream : charStreams) {
      Tokenizer tokenizer = buildTokenizer(charStream);
      tokenizer.advance();
      assertTrue(tokenizer.hasToken());
    }
  }

  @Test
  public void returnsIdentifierToken() throws Exception {
    String[] lexemes = {
        "Hello",
        "Bye",
        "_underscore",
        "var123",
        "var_123",
        "var_123_foo"
    };
    for (String lexeme : lexemes) {
      Tokenizer tokenizer = buildTokenizer(lexeme);
      List<Token> expectedTokens = Collections.singletonList(
          Token.build(TokenType.IDENTIFIER, lexeme)
      );
      TokenMatcher.matchAll(expectedTokens, tokenizer);
    }
  }

  @Test
  public void returnsKeyword_class() throws Exception {
    String[] keywords = {
        "class",
        "constructor",
        "function",
        "method",
        "field",
        "static",
        "var",
        "int",
        "char",
        "boolean",
        "void",
        "true",
        "false",
        "null",
        "this",
        "let",
        "do",
        "if",
        "else",
        "while",
        "return"
    };
    for (String lexeme : keywords) {
      Tokenizer tokenizer = buildTokenizer(lexeme);
      List<Token> expectedTokens = Collections.singletonList(
          Token.build(TokenType.KEYWORD, lexeme)
      );
      TokenMatcher.matchAll(expectedTokens, tokenizer);
    }
  }

  @Test
  public void returnsKeywordAndIdentifier() throws Exception {
    String classLexeme = "class";
    String identifier = "MyClass";
    String stream =  String.join(" ", Arrays.asList(classLexeme, identifier));
    Tokenizer tokenizer = buildTokenizer(stream);
    List<Token> expectedTokens = Arrays.asList(
        Token.build(TokenType.KEYWORD, classLexeme),
        Token.build(TokenType.IDENTIFIER, identifier)
    );
    TokenMatcher.matchAll(expectedTokens, tokenizer);
  }


  @Test
  public void returnsSymbol() throws Exception {
    String[] symbols = {
      "+",
      "{",
      "}",
      "(",
      ")",
      "[",
      "]",
      ".",
      ",",
      ";",
      "+",
      "-",
      "*",
      "/",
      "&",
      "|",
      "<",
      ">",
      "=",
      "~"
    };
    for (String symbolLexeme : symbols) {

    Tokenizer tokenizer = buildTokenizer(symbolLexeme);
    List<Token> expectedTokens = Collections.singletonList(
        Token.build(TokenType.SYMBOL, symbolLexeme)
    );
    TokenMatcher.matchAll(expectedTokens, tokenizer);
    }
  }


  @Test
  public void returnsInteger() throws Exception {
    String[] integers = {
        "1342",
        "9999",
        "0",
        "32767"
    };
    for (String integerLexeme : integers) {
      Tokenizer tokenizer = buildTokenizer(integerLexeme);
      List<Token> expectedTokens = Collections.singletonList(
          Token.build(TokenType.INTEGER, integerLexeme)
      );
      TokenMatcher.matchAll(expectedTokens, tokenizer);
    }
  }

  @Test
  public void returnsTokensSeparatedBySpace_aPlusb() throws Exception {
    String a = "a";
    String plus = "+";
    String b = "b";
    String stream = String.join(" ", Arrays.asList(a, plus, b)) ;
    Tokenizer tokenizer = buildTokenizer(stream);
    List<Token> expectedTokens = Arrays.asList(
        Token.build(TokenType.IDENTIFIER, a),
        Token.build(TokenType.SYMBOL, plus),
        Token.build(TokenType.IDENTIFIER, b)
    );
    TokenMatcher.matchAll(expectedTokens, tokenizer);
  }

  @Test
  public void returnsTokensSeparatedBySpace_bPlusa() throws Exception {
    String a = "a";
    String plus = "+";
    String b = "b";
    String stream = String.join(" ", Arrays.asList(b, plus, a)) ;
    Tokenizer tokenizer = buildTokenizer(stream);
    List<Token> expectedTokens = Arrays.asList(
        Token.build(TokenType.IDENTIFIER, b),
        Token.build(TokenType.SYMBOL, plus),
        Token.build(TokenType.IDENTIFIER, a)
    );
    TokenMatcher.matchAll(expectedTokens, tokenizer);
  }

  @Test
  public void returnIdentifierAndSymbol() throws Exception {
    String[] delimiters = { "", " "};
    for (String delimiter : delimiters) {
      String a = "a";
      String plus = "+";
      String stream = String.join(delimiter, Arrays.asList(a, plus)) ;
      Tokenizer tokenizer = buildTokenizer(stream);
      List<Token> expectedTokens = Arrays.asList(
          Token.build(TokenType.IDENTIFIER, a),
          Token.build(TokenType.SYMBOL, plus)
      );
      TokenMatcher.matchAll(expectedTokens, tokenizer);
    }
  }

  @Test
  public void returnsTokensNotSeparatedBySpace_aPlusb() throws Exception {
    String a = "a";
    String plus = "+";
    String b = "b";
    String stream = String.join("", Arrays.asList(a, plus, b)) ;
    Tokenizer tokenizer = buildTokenizer(stream);
    List<Token> expectedTokens = Arrays.asList(
        Token.build(TokenType.IDENTIFIER, a),
        Token.build(TokenType.SYMBOL, plus),
        Token.build(TokenType.IDENTIFIER, b)
    );
    TokenMatcher.matchAll(expectedTokens, tokenizer);
  }

  @Test
  public void returnStringLiteral() throws Exception {
    String[] contents = {
        "my string literal",
    };
    for (String content : contents) {
      String literal = "\"" + content + "\"";
      Tokenizer tokenizer = buildTokenizer(literal);
      List<Token> expectedTokens = Collections.singletonList(
          Token.build(TokenType.STRING, content)
      );
      TokenMatcher.matchAll(expectedTokens, tokenizer);
    }
  }

  @Test
  public void escapeStringLiteral() throws Exception {
    String prefix = "literal";
    String suffix = "quotes";
    String content = prefix + "\\\"" + suffix;
    String expectedContent = prefix + "\"" + suffix;
    String literal = "\"" + content + "\"";
    Tokenizer tokenizer = buildTokenizer(literal);
    List<Token> expectedTokens = Collections.singletonList(
        Token.build(TokenType.STRING, expectedContent)
    );
    TokenMatcher.matchAll(expectedTokens, tokenizer);

  }
  @Test
  public void ignoresComment() throws Exception {
    String[] comments = {
        "// this is a comment",
        "/* block comment one one line */",
        "/** block documentation comment */",
        "/** block documentation \n comment */",
    };

    for (String comment : comments) {
      Tokenizer tokenizer = buildTokenizer(comment);
      tokenizer.advance();
      assertFalse(tokenizer.hasToken());
    }

  }

  @Test
  public void findTokenAfterComment() throws Exception {
    String lineComment = "/* this is a comment */let";
    Tokenizer tokenizer = buildTokenizer(lineComment);
    tokenizer.advance();
    assertEquals(Token.build(TokenType.KEYWORD, "let"), tokenizer.peekToken());
    tokenizer.advance();
    assertFalse(tokenizer.hasToken());
  }

  @Test
  public void findTokenBeforeCommentNoSpace() throws Exception {
    String lineComment = "}/** this is a comment */";
    Tokenizer tokenizer = buildTokenizer(lineComment);
    tokenizer.advance();
    assertEquals(Token.build(TokenType.SYMBOL, "}"), tokenizer.peekToken());
    tokenizer.advance();
    assertFalse(tokenizer.hasToken());
  }

  @Test
  public void findTokenBeforeComment() throws Exception {
    String lineComment = "function // this is a comment";
    Tokenizer tokenizer = buildTokenizer(lineComment);
    tokenizer.advance();
    assertEquals(Token.build(TokenType.KEYWORD, "function"), tokenizer.peekToken());
    tokenizer.advance();
    assertFalse(tokenizer.hasToken());
  }

  @Test
  public void blockCommentMultiLine() throws Exception {
    String lineComment = "/** this is a comment \n over multiple lines */";
    Tokenizer tokenizer = buildTokenizer(lineComment);
    tokenizer.advance();
    assertFalse(tokenizer.hasToken());
  }

  @Test
  public void divideAtEndOfLine() throws Exception {
    String lineComment = "/\n";
    Tokenizer tokenizer = buildTokenizer(lineComment);
    tokenizer.advance();
    assertEquals(Token.build(TokenType.SYMBOL, "/"), tokenizer.peekToken());
  }

  @Test
  public void parseSnippet() throws Exception {
    String snippet = "if (x < 0) {\n"
        + "\t// prints the sign\n"
        + "\tlet sign = \"negative\";\n"
        + "}";

    Tokenizer tokenizer = buildTokenizer(snippet);
    List<Token> expectedTokens = Arrays.asList(
        Token.build(TokenType.KEYWORD, "if"),
        Token.build(TokenType.SYMBOL, "("),
        Token.build(TokenType.IDENTIFIER, "x"),
        Token.build(TokenType.SYMBOL, "<"),
        Token.build(TokenType.INTEGER, "0"),
        Token.build(TokenType.SYMBOL, ")"),
        Token.build(TokenType.SYMBOL, "{"),
        Token.build(TokenType.KEYWORD, "let"),
        Token.build(TokenType.IDENTIFIER, "sign"),
        Token.build(TokenType.SYMBOL, "="),
        Token.build(TokenType.STRING, "negative"),
        Token.build(TokenType.SYMBOL, ";"),
        Token.build(TokenType.SYMBOL, "}")
    );
    TokenMatcher.matchAll(expectedTokens, tokenizer);
  }

  private Tokenizer buildTokenizer(String charStream) {
    CharReader reader = new CharReaderStub(charStream);
    return new FileTokenizer(reader);
  }

}