package org.nand2tetris.code;

import java.util.HashMap;
import java.util.Map;

public class CodeTable {

  private final static Map<String, Integer> compCodes= new HashMap<>();
  private final static Map<String, Integer> destCodes= new HashMap<>();
  private final static Map<String, Integer> jumpCodes= new HashMap<>();

  private static final String zero = "0";
  private static final String one = "1";
  private static final String minusOne = "-1";
  private static final String D = "D";
  private static final String A = "A";
  private static final String notD = "!D";
  private static final String notA = "!A";
  private static final String minusD = "-D";
  private static final String minusA = "-A";
  private static final String DPlusOne = "D+1";
  private static final String APlusOne = "A+1";
  private static final String DMinusOne = "D-1";
  private static final String AMinusOne = "A-1";
  private static final String DPlusA = "D+A";
  private static final String DMinusA = "D-A";
  private static final String AMinusD = "A-D";
  private static final String DAndA = "D&A";
  private static final String DOrA = "D|A";

  private static final String M = "M";
  private static final String notM = "!M";
  private static final String minusM = "-M";
  private static final String MPlusOne = "M+1";
  private static final String MMinusOne = "M-1";
  private static final String DPlusM = "D+M";
  private static final String DMinusM = "D-M";
  private static final String MMinusD = "M-D";
  private static final String DAndM = "D&M";
  private static final String DOrM = "D|M";

  private static final String NULL = "null";

  private static final String MD = "MD";
  private static final String AM = "AM";
  private static final String AD = "AD";
  private static final String AMD = "AMD";



  private static final String JGT = "JGT";
  private static final String JEQ = "JEQ";
  private static final String JGE = "JGE";
  private static final String JLT = "JLT";
  private static final String JNE = "JNE";
  private static final String JLE = "JLE";
  private static final String JMP = "JMP";


  static {
    compCodes.put(zero, 0b010_1010);
    compCodes.put(one, 0b011_1111);
    compCodes.put(minusOne, 0b011_1010);
    compCodes.put(D, 0b000_1100);
    compCodes.put(A, 0b011_0000);
    compCodes.put(notD, 0b000_1101);
    compCodes.put(notA, 0b011_0001);
    compCodes.put(minusD, 0b000_1111);
    compCodes.put(minusA, 0b011_0011);
    compCodes.put(DPlusOne, 0b001_1111);
    compCodes.put(APlusOne, 0b011_0111);
    compCodes.put(DMinusOne, 0b000_1110);
    compCodes.put(AMinusOne, 0b011_0010);
    compCodes.put(DPlusA, 0b000_0010);
    compCodes.put(DMinusA, 0b001_0011);
    compCodes.put(AMinusD, 0b000_0111);
    compCodes.put(DAndA, 0b000_0000);
    compCodes.put(DOrA, 0b001_0101);

    compCodes.put(M, 0b111_0000);
    compCodes.put(notM, 0b111_0001);
    compCodes.put(minusM, 0b111_0011);
    compCodes.put(MPlusOne, 0b111_0111);
    compCodes.put(MMinusOne, 0b111_0010);
    compCodes.put(DPlusM, 0b100_0010);
    compCodes.put(DMinusM, 0b101_0011);
    compCodes.put(MMinusD, 0b100_0111);
    compCodes.put(DAndM, 0b100_0000);
    compCodes.put(DOrM, 0b101_0101);
  }

  static {
    destCodes.put(NULL, 0b000);
    destCodes.put(M, 0b001);
    destCodes.put(D, 0b010);
    destCodes.put(MD, 0b011);
    destCodes.put(A, 0b100);
    destCodes.put(AM, 0b101);
    destCodes.put(AD, 0b110);
    destCodes.put(AMD, 0b111);
  }

  static {
    jumpCodes.put(NULL, 0b000);
    jumpCodes.put(JGT, 0b001);
    jumpCodes.put(JEQ, 0b010);
    jumpCodes.put(JGE, 0b011);
    jumpCodes.put(JLT, 0b100);
    jumpCodes.put(JNE, 0b101);
    jumpCodes.put(JLE, 0b110);
    jumpCodes.put(JMP, 0b111);
  }

  public static int zero() {
    return compCodes.get(zero);
  }
  public static int one() {
    return compCodes.get(one);
  }
  public static int minusOne() {
    return compCodes.get(minusOne);
  }
  public static int D() {
    return compCodes.get(D);
  }
  public static int A() {
    return compCodes.get(A);
  }
  public static int notD() {
    return compCodes.get(notD);
  }
  public static int notA() {
    return compCodes.get(notA);
  }
  public static int minusD() {
    return compCodes.get(minusD);
  }

  public static int minusA() {
    return compCodes.get(minusA);
  }

  public static int DPlusOne() {
    return compCodes.get(DPlusOne);
  }

  public static int APlusOne() {
    return compCodes.get(APlusOne);
  }

  public static int DMinusOne() {
    return compCodes.get(DMinusOne);
  }

  public static int AMinusOne() {
    return compCodes.get(AMinusOne);
  }

  public static int DPlusA() {
    return compCodes.get(DPlusA);
  }

  public static int DMinusA() {
    return compCodes.get(DMinusA);
  }

  public static int AMinusD() {
    return compCodes.get(AMinusD);
  }
  public static int DAndA() {
    return compCodes.get(DAndA);
  }

  public static int DOrA() {
    return compCodes.get(DOrA);
  }

  public static int M() {
    return compCodes.get(M);
  }

  public static int notM() {
    return compCodes.get(notM);
  }

  public static int minusM() {
    return compCodes.get(minusM);
  }

  public static int MPlusOne() {
    return compCodes.get(MPlusOne);
  }

  public static int MMinusOne() {
    return compCodes.get(MMinusOne);
  }

  public static int DPlusM() {
    return compCodes.get(DPlusM);
  }

  public static int DMinusM() {
    return compCodes.get(DMinusM);
  }

  public static int MMinusD() {
    return compCodes.get(MMinusD);
  }

  public static int DAndM() {
    return compCodes.get(DAndM);
  }

  public static int DOrM() {
    return compCodes.get(DOrM);
  }

  public static int destNull() {
    return destCodes.get(NULL);
  }

  public static int destA() {
    return destCodes.get(A);
  }

  public static int destD() {
    return destCodes.get(D);
  }

  public static int destM() {
    return destCodes.get(M);
  }

  public static int destMD() {
    return destCodes.get(MD);
  }

  public static int destAM() {
    return destCodes.get(AM);
  }

  public static int destAD() {
    return destCodes.get(AD);
  }

  public static int destAMD() {
    return destCodes.get(AMD);
  }

  public static int nullJmp() {
    return jumpCodes.get(NULL);
  }

  public static int JGT() {
    return jumpCodes.get(JGT);
  }

  public static int JEQ() {
    return jumpCodes.get(JEQ);
  }

  public static int JGE() {
    return jumpCodes.get(JGE);
  }
  public static int JLT() {
    return jumpCodes.get(JLT);
  }
  public static int JNE() {
    return jumpCodes.get(JNE);
  }

  public static int JLE() {
    return jumpCodes.get(JLE);
  }

  public static int JMP() {
    return jumpCodes.get(JMP);
  }
}
