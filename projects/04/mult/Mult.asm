// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)
//
// This program only needs to handle arguments that satisfy
// R0 >= 0, R1 >= 0, and R0*R1 < 32768.


    // R2 = 0
    @R2     // A = R2
    M=0     // RAM[A] = 0

    // D = R0
    @R0     // A = R0
    D=M     // D = RAM[A]

    // if R0 == 0 goto END (result is 0)
    @END    // A = END
    D;JEQ   // if D == 0 goto END - D holds value of R0

    // D = R1 check R1 == 0
    @R1     // A = R1
    D=M     // D = RAM[A]

    // if R1 == 0 goto END (result is 0)
    @END    // A = END
    D;JEQ   // if D == 0 goto END - D holds value of R1

    
    // loop while (R0 > 0)
    // compute (R2 = R2 + R1), R0 times
(LOOP)
    // D = R0
    @R0
    D=M

    // if R0 == 0 goto END
    @END
    D;JEQ   // if D == 0 goto END - finished looping

    // R2 = R2 + R1
    @R2     
    D=M     // D = R2 - load value at R2 in Data register
    @R1
    D=D+M   // D = D + R1 - add R1 to Data register
    @R2
    M=D     // R2 = D - put new value back in R2

    // R0--
    @R0
    M=M-1   // R0 = R0 -1

    // goto LOOP unconditionnaly
    @LOOP
    0;JMP

(END)
    @END
    0;JMP // Infinite loop