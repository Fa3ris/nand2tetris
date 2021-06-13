package org.nand2tetris;

import java.util.Arrays;

public class BinaryFormatter {

  private static final int LSB_MASK = 0b1;

  private static final char[] DIGITS = {'0', '1'};

  /**
   * number of bits in int
   */
  private static final int INT_SIZE = 32;

  /**
   * converts number to String corresponding to binary representation
   * zero-padding is added on the left to match the expected minStrLength
   * if the binary representation if longer than minStrLength, the binary representation is
   * returned as-is
   *
   * e.g. toBinaryString(0b00101, 8) => "00000101"
   */
  public static String toBinaryString(int number, int minStrLength) {
    char[] chars = toBinaryChars(number);
    boolean noPaddingNeeded = chars.length >= minStrLength;
    if (noPaddingNeeded) {
      return String.valueOf(chars);
    }
    return padLeftZero(chars, minStrLength);
  }

  public static char[] toBinaryChars(int number) {
    if (number == 0) { // special case
      return new char[]{DIGITS[0]};
    }

    int nbCharsToRepresentInBinary = INT_SIZE - numberOfLeadingZeros(number);

    char[] chars = new char[nbCharsToRepresentInBinary];
    int charsIndex = chars.length - 1;
    int numberLSB;
    boolean significantBitsRemaining;
    do {
      numberLSB = number & LSB_MASK;
      char val = DIGITS[numberLSB]; // get char corresponding to lsb
      chars[charsIndex] = val; // write from right to left
      charsIndex--;
      number >>= 1; // shift to get next bit
      significantBitsRemaining = number != 0;
    } while (significantBitsRemaining);
    return chars;
  }

  private static String padLeftZero(char[] chars, int length) {
    int zerosLength = length - chars.length;
    char[] zeros = new char[zerosLength];
    Arrays.fill(zeros, DIGITS[0]);
    return String.valueOf(zeros) + String.valueOf(chars);
  }

  /**
   * number of zeros before the most significant 1-bit in a 32-bit representation
   */
  public static int numberOfLeadingZeros(int number) {
    if (number == 0) { // special case
      return INT_SIZE;
    }

    int count = 1; // to cancel or not at the end

    boolean msbInLowerGroup = (number >>> 16) == 0; // compare range [32-17] and [16-1]
    if (msbInLowerGroup) {
      count += 16;
      number <<= 16; // shift lower group to range [32-17]
    }

    msbInLowerGroup = (number >>> 24) == 0; // compare range [32-25] and [24-17]
    if (msbInLowerGroup) {
      count += 8;
      number <<= 8; // shift lower group to range [32-25]
    }

    msbInLowerGroup = (number >>> 28) == 0; // compare range [32-29] to [28-25]
    if (msbInLowerGroup) {
      count += 4;
      number <<= 4; // shift lower group to range [32-29]
    }

    msbInLowerGroup = (number >>> 30) == 0; // compare range [32-31] to [30-29]
    if (msbInLowerGroup) {
      count += 2;
      number <<= 2; // shift lower group to range [32-31]
    }
    /*
      most significant 1-bit is either 32nd or 31st bit
        if 32nd bit => no more leading zero
          number >>> 31 == 1
          cancel initial count = 1
        if 31st bit => additional leading zero: the 32nd bit
          number >>> 31 == 0
          do not cancel initial count = 1
    */
    int msb = number >>> 31;
    count -= msb;
    return count;
  }
}
