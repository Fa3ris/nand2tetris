// push constant 0
@0
D=A	// D = 0
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 0
// pop local 0
@LCL
D=M	// D = LCL
@0
D=D+A	// D = LCL + 0
@R13
M=D	// R13 = LCL + 0
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@R13
A=M	// A = LCL + 0
M=D	// *(LCL + 0) = *SP
// label LOOP_START
(LOOP_START)
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
// push local 0
@LCL
D=M	// D = LCL
@0
A=D+A	// A = LCL + 0
D=M	// D = *(LCL + 0)
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = *(LCL + 0)
// add
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
M=D+M	// *(SP - 1) = D + [*(SP - 1)]
// pop local 0
@LCL
D=M	// D = LCL
@0
D=D+A	// D = LCL + 0
@R13
M=D	// R13 = LCL + 0
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@R13
A=M	// A = LCL + 0
M=D	// *(LCL + 0) = *SP
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
// if-goto LOOP_START
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@LOOP_START
D;JNE	// if D then jump to LOOP_START
// push local 0
@LCL
D=M	// D = LCL
@0
A=D+A	// A = LCL + 0
D=M	// D = *(LCL + 0)
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = *(LCL + 0)
