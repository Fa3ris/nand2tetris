// push argument 1
@ARG
D=M	// D = ARG
@1
A=D+A	// A = ARG + 1
D=M	// D = *(ARG + 1)
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = *(ARG + 1)
// pop pointer 1
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@THAT
M=D	// THAT = *SP
// push constant 0
@0
D=A	// D = 0
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 0
// pop that 0
@THAT
D=M	// D = THAT
@0
D=D+A	// D = THAT + 0
@R13
M=D	// R13 = THAT + 0
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@R13
A=M	// A = THAT + 0
M=D	// *(THAT + 0) = *SP
// push constant 1
@1
D=A	// D = 1
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 1
// pop that 1
@THAT
D=M	// D = THAT
@1
D=D+A	// D = THAT + 1
@R13
M=D	// R13 = THAT + 1
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@R13
A=M	// A = THAT + 1
M=D	// *(THAT + 1) = *SP
// push argument 0
@ARG
D=M	// D = ARG
@0
A=D+A	// A = ARG + 0
D=M	// D = *(ARG + 0)
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = *(ARG + 0)
// push constant 2
@2
D=A	// D = 2
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 2
// sub
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
M=M-D	// *(SP - 1) = *(SP - 1) - D
// pop argument 0
@ARG
D=M	// D = ARG
@0
D=D+A	// D = ARG + 0
@R13
M=D	// R13 = ARG + 0
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@R13
A=M	// A = ARG + 0
M=D	// *(ARG + 0) = *SP
// label MAIN_LOOP_START
(MAIN_LOOP_START)
// push argument 0
@ARG
D=M	// D = ARG
@0
A=D+A	// A = ARG + 0
D=M	// D = *(ARG + 0)
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = *(ARG + 0)
// if-goto COMPUTE_ELEMENT
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@COMPUTE_ELEMENT
D;JNE	// if D then jump to COMPUTE_ELEMENT
// goto END_PROGRAM
@END_PROGRAM
0;JMP	// jump to END_PROGRAM
// label COMPUTE_ELEMENT
(COMPUTE_ELEMENT)
// push that 0
@THAT
D=M	// D = THAT
@0
A=D+A	// A = THAT + 0
D=M	// D = *(THAT + 0)
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = *(THAT + 0)
// push that 1
@THAT
D=M	// D = THAT
@1
A=D+A	// A = THAT + 1
D=M	// D = *(THAT + 1)
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = *(THAT + 1)
// add
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
M=D+M	// *(SP - 1) = D + [*(SP - 1)]
// pop that 2
@THAT
D=M	// D = THAT
@2
D=D+A	// D = THAT + 2
@R13
M=D	// R13 = THAT + 2
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@R13
A=M	// A = THAT + 2
M=D	// *(THAT + 2) = *SP
// push pointer 1
@THAT
D=M	// D = THAT
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = THAT
// push constant 1
@1
D=A	// D = 1
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 1
// add
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
M=D+M	// *(SP - 1) = D + [*(SP - 1)]
// pop pointer 1
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@THAT
M=D	// THAT = *SP
// push argument 0
@ARG
D=M	// D = ARG
@0
A=D+A	// A = ARG + 0
D=M	// D = *(ARG + 0)
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = *(ARG + 0)
// push constant 1
@1
D=A	// D = 1
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 1
// sub
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
M=M-D	// *(SP - 1) = *(SP - 1) - D
// pop argument 0
@ARG
D=M	// D = ARG
@0
D=D+A	// D = ARG + 0
@R13
M=D	// R13 = ARG + 0
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@R13
A=M	// A = ARG + 0
M=D	// *(ARG + 0) = *SP
// goto MAIN_LOOP_START
@MAIN_LOOP_START
0;JMP	// jump to MAIN_LOOP_START
// label END_PROGRAM
(END_PROGRAM)
